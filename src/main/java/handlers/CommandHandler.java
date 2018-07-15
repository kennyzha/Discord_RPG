package handlers;

import config.ApplicationConstants;
import database.PlayerDatabase;
import listeners.MessageListener;
import models.CombatResult;
import models.Monster;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;

public class CommandHandler {

    private PlayerDatabase playerDatabase;
    private MessageHandler messageHandler;
    private HighscoreHandler highscoreHandler;

    private final String COMMAND_PREFIX = "r!";
    public CommandHandler(PlayerDatabase playerDatabase, MessageHandler messageHandler, HighscoreHandler highscoreHandler){
        this.playerDatabase = playerDatabase;
        this.messageHandler = messageHandler;
        this.highscoreHandler = highscoreHandler;
    }

    public void handleCommand(MessageReceivedEvent event){
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.

        String msg = message.getContentDisplay().toLowerCase();
        String[] msgArr = msg.split(" ");

        if(msgArr.length == 0 || !msgArr[0].startsWith(COMMAND_PREFIX))
            return;

        switch(msgArr[0]){
            case "r!profile":
                profile(channel, author, message);
                break;
            case "r!help":
                help(channel, author);
                break;
            case "r!commands":
                commands(channel, author);
                break;
            case "r!train":
               train(channel, msgArr, author);
                break;
            case "r!stamina":
                stamina(channel, author);
                break;
            case "r!fight":
               fight(channel, message, author);
                break;
            case "r!hunt":
                hunt(channel, msgArr, author);
                break;
            case "r!monsters":
                channel.sendMessage(messageHandler.createEmbedMonsterListMessage(author)).queue();
                break;
            case"r!highscore":
                highscore(channel, msgArr, author, event.getJDA());
                break;
            default:
                String str = "Command not recognized: " + message.getContentDisplay();
                sendDefaultEmbedMessage(author, str, messageHandler, channel);
        }
    }

    public void profile(MessageChannel channel, User user, Message message){
        String[] msgArr = message.getContentDisplay().split(" ");
        if(msgArr.length == 1){
            Player player = playerDatabase.grabPlayer(user.getId());
            Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

            if(curStamina != null){
                channel.sendMessage(messageHandler.createProfileEmbed(user, player, curStamina)).queue();
            }
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "profile");

            if(mentionedPlayer != null){
                User mentionedUser = message.getMentionedMembers().get(0).getUser();
                Stamina mentionedUserStamina = playerDatabase.retreivePlayerStamina(mentionedUser.getId());
                channel.sendMessage(messageHandler.createProfileEmbed(mentionedUser, mentionedPlayer, mentionedUserStamina)).queue();
            }
        }
    }

    public void help(MessageChannel channel, User user){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ApplicationConstants.HELP_STRING)).queue();;
    }

    public void commands(MessageChannel channel, User user){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ApplicationConstants.VERBOSE_COMMANDS)).queue();;
    }

    public void train( MessageChannel channel, String[] msgArr, User user){
        if(msgArr.length < 3){
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a number between 1 and 20 and the type of stat you would like to train. e.g. r!train power 10")).queue();
        }else{
            String statToTrain = msgArr[1];
            try{
                int numTimesToTrain = Integer.parseInt(msgArr[2]);

                if(numTimesToTrain < 1 || numTimesToTrain > 20){
                    channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a number between 1 and 20 and the type of stat you would like to train. e.g. r!train power 10")).queue();
                } else{
                    Player player = playerDatabase.grabPlayer(user.getId());
                    Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

                    TrainingHandler trainingHandler = new TrainingHandler(player, user, curStamina, channel, playerDatabase);

                    if(statToTrain.equals("speed")){
                        trainingHandler.trainSpeed(numTimesToTrain);
                    }else if(statToTrain.equals("power")){
                        trainingHandler.trainPower(numTimesToTrain);
                    }else if(statToTrain.equals("strength")){
                        trainingHandler.trainStrength(numTimesToTrain);
                    } else{
                        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Invalid argument. Failed to train:" + statToTrain + ". You can only train power, speed and strength.")).queue();
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
                channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a valid number between 1 and 20. e.g. r!train speed 10")).queue();
            }
        }
    }

    public void stamina(MessageChannel channel, User user){
        Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());
        if(curStamina == null)
        {
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please register an account into the system with !profile.")).queue();
        } else{
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "You currently have " + curStamina.getStamina() + " stamina.")).queue();
        }
    }

    public void fight(MessageChannel channel, Message message, User user){
        String[] msgArr = message.getContentDisplay().split(" ");

        CombatHandler combatHandler = new CombatHandler();
        if(msgArr.length < 2){
            sendDefaultEmbedMessage(user, "Please mention the name of the user you wish to fight with !fight @name.", messageHandler, channel);
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "fight");

            if(mentionedPlayer != null){
                String enemyName = message.getMentionedUsers().get(0).getName();

                Player player = playerDatabase.grabPlayer(user.getId());
                CombatResult pvpResults = combatHandler.fightPlayer(player, mentionedPlayer);
                channel.sendMessage(messageHandler.createEmbedFightMessage(user, enemyName, pvpResults)).queue();
            }
        }
    }

    public void hunt(MessageChannel channel, String[] msgArr, User user){
        CombatHandler handler = new CombatHandler();
        if(msgArr.length < 3){
            sendDefaultEmbedMessage(user, "Please type the name of the monster you wish to hunt and the # of times. e.g. \"r!hunt slime 1\". r!monsters for list of monsters.", messageHandler, channel);
            return;
        }

        String inputtedName = msgArr[1];
        Monster monster = Monster.identifyMonster(inputtedName);

        if(monster == null){
            sendDefaultEmbedMessage(user, inputtedName + " is not a valid monster name. Please type a valid name of the monster you wish to hunt e.g. r!!hunt slime. r!monsters for list of monsters.", messageHandler, channel);
        } else{
            Player player = playerDatabase.grabPlayer(user.getId());
            Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

            try{
                int numTimesToHunt = Math.min(Integer.parseInt(msgArr[2]), curStamina.getStamina());

                if(numTimesToHunt == 0){
                    sendDefaultEmbedMessage(user, "You are too tired to hunt monsters. You recover 1 stamina every 5 minutes.", messageHandler, channel);
                    return;
                }

                curStamina.setStamina(curStamina.getStamina() - numTimesToHunt);
                CombatResult pvmResults = handler.fightMonster(player, monster, numTimesToHunt);

                playerDatabase.insertPlayer(player);
                playerDatabase.insertPlayerStamina(curStamina);

                channel.sendMessage(messageHandler.createEmbedFightMessage(user, monster.getName(), pvmResults)).queue();

            } catch(Exception e){
                sendDefaultEmbedMessage(user, "Please type a valid number of times you wish to hunt that monster with. e.g. \"r!hunt slime 1\". \"r!monsters\" for list of monsters.", messageHandler, channel);
            }
        }
    }

    public void monsters(){

    }

    public void highscore(MessageChannel channel, String[] msgArr, User user, JDA jda){
        String highscoreType = "";
        ArrayList<Player> players;
        if(msgArr.length < 2){
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Highscores update daily. Available highscores: Level, Power, Speed, Strength, Total, Gold. e.g r!highscore total")).queue();
            return;
        } else{
            switch(msgArr[1]){
                case "level":
                    highscoreType = "Level";
                    players = highscoreHandler.getLevelHighscore();
                    break;
                case "speed":
                    players = highscoreHandler.getSpeedHighscore();
                    highscoreType = "Speed";
                    break;
                case "power":
                    players = highscoreHandler.getPowerHighscore();
                    highscoreType = "Power";
                    break;
                case "strength":
                    players = highscoreHandler.getStrengthHighscore();
                    highscoreType = "Strength";
                    break;
                case "total":
                    players = highscoreHandler.getTotalHighscore();
                    highscoreType = "Total Stats";
                    channel.sendMessage(messageHandler.createTotalHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
                    return;
                case "gold":
                    players = highscoreHandler.getGoldHighscore();
                    highscoreType = "Gold";
                    channel.sendMessage(messageHandler.createGoldHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
                    return;
                default:
                    channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Available highscores: Level, Power, Speed, Strength, Total, Gold. e.g r!highscore total")).queue();
                    return;
            }
        }
        System.out.println(highscoreType);
        channel.sendMessage(messageHandler.createHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
    }

    public void sendDefaultEmbedMessage(User user, String description, MessageHandler messageHandler, MessageChannel channel){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, description)).queue();
    }
}
