package handlers;

import config.ApplicationConstants;
import database.PlayerDatabase;
import models.CombatResult;
import models.Monster;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandHandler {

    private PlayerDatabase playerDatabase;
    private final String COMMAND_PREFIX = "r!";
    public CommandHandler(){
        this.playerDatabase = new PlayerDatabase();
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
                help(channel);
                break;
            case "r!commands":
                commands(channel);
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
                channel.sendMessage("Available monsters to !hunt: slime (lvl 1), spider (lvl 5), goblin (lvl 10), kobold (lvl 20), orc (lvl 30), and ogre (lvl 50)").queue();
                break;
            default:
                channel.sendMessage(author.getName() + ", Command not recognized: " + message.getContentDisplay() + "\n" + ApplicationConstants.ALL_COMMANDS).queue();
        }
    }

    public void profile(MessageChannel channel, User user, Message message){
        String[] msgArr = message.getContentDisplay().split(" ");
        MessageHandler messageHandler = new MessageHandler();
        System.out.println("hello!");
        if(msgArr.length == 1){
            Player player = playerDatabase.grabPlayer(user.getId());
            Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());
            String slime = "http://wiki.chronicles-of-blood.com/images/Creatures-Slime_monster.jpg";
            String orc = "https://wallscover.com/images/orc-9.jpg";
            if(curStamina != null){
                channel.sendMessage(messageHandler.createProfileEmbed(user, player, curStamina)).queue();
            }
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "profile");
            Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

            if(mentionedPlayer != null && curStamina != null){
                User mentionedUser = message.getMentionedMembers().get(0).getUser();
                channel.sendMessage(messageHandler.createProfileEmbed(mentionedUser, mentionedPlayer, curStamina)).queue();
            }
        }
    }

    public void help(MessageChannel channel){
        channel.sendMessage(ApplicationConstants.HELP_STRING).queue();
    }

    public void commands(MessageChannel channel){
        channel.sendMessage(ApplicationConstants.VERBOSE_COMMANDS).queue();
    }

    public void train( MessageChannel channel, String[] msgArr, User user){
        if(msgArr.length < 3){
            channel.sendMessage(user.getName() + ", Please include a number between 1 and 20 and the type of stat you would like to train. e.g. !train power 10").queue();
        }else{
            String statToTrain = msgArr[1];
            try{
                int numTimesToTrain = Integer.parseInt(msgArr[2]);

                if(numTimesToTrain < 1 || numTimesToTrain > 20){
                    channel.sendMessage(user.getName() + ", Please include a number between 1 and 20 and the type of stat you would like to train. e.g. !train power 10").queue();
                } else{
                    Player player = playerDatabase.grabPlayer(user.getId());
                    Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

                    TrainingHandler trainingHandler = new TrainingHandler(player, curStamina, channel, playerDatabase);

                    if(statToTrain.equals("speed")){
                        trainingHandler.trainSpeed(numTimesToTrain);
                    }else if(statToTrain.equals("power")){
                        trainingHandler.trainPower(numTimesToTrain);
                    }else if(statToTrain.equals("strength")){
                        trainingHandler.trainStrength(numTimesToTrain);
                    } else{
                        channel.sendMessage( user.getName() + ", Invalid argument. Failed to train: " + statToTrain + ". You can only train power, speed and strength.\n").queue();
                    }
                }
            } catch(Exception e){
                channel.sendMessage(user + ", please type in a number between 1 and 20.").queue();
            }
        }
    }

    public void stamina(MessageChannel channel, User user){
        Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());
        if(curStamina == null)
        {
            channel.sendMessage(user.getName() + ", Please register an account into the system with !profile.").queue();
        } else{
            channel.sendMessage(user.getName() + ", you currently have " + curStamina.getStamina() + " stamina.").queue();
        }
    }

    public void fight(MessageChannel channel, Message message, User user){
        String[] msgArr = message.getContentDisplay().split(" ");

        CombatHandler combatHandler = new CombatHandler();
        if(msgArr.length < 2){
            channel.sendMessage(user.getName() + ", Please mention the name of the user you wish to fight with !fight @name.").queue();
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "fight");

            if(mentionedPlayer != null){
                Player player = playerDatabase.grabPlayer(user.getId());
                CombatResult pvpResults = combatHandler.fightPlayer(player, mentionedPlayer);
                channel.sendMessage(pvpResults.getCombatResultString() + "\n" + pvpResults.getEntityOneStats()).queue();
            }
        }
    }

    public void hunt(MessageChannel channel, String[] msgArr, User user){
        CombatHandler handler = new CombatHandler();
        if(msgArr.length < 3){
            channel.sendMessage(user.getName() + ", Please type the name of the monster you wish to hunt and the # of times with \"!hunt name 1\". !monsters for list of monsters.").queue();
        }

        String inputtedName = msgArr[1];
        Monster monster = Monster.identifyMonster(inputtedName);

        if(monster == null){
            channel.sendMessage(user.getName() + ", " + inputtedName + " is not a valid monster name. Please type a valid name of the monster you wish to hunt with !!hunt monster-name. !monsters for list of monsters.").queue();
        } else{
            Player player = playerDatabase.grabPlayer(user.getId());
            Stamina curStamina = playerDatabase.retreivePlayerStamina(user.getId());

            try{
                int numTimesToHunt = Math.min(Integer.parseInt(msgArr[2]), curStamina.getStamina());

                if(numTimesToHunt == 0){
                    channel.sendMessage(user.getName() + ", You are too tired to hunt monsters. You recover 1 stamina every 5 minutes.").queue();
                    return;
                }

                curStamina.setStamina(curStamina.getStamina() - numTimesToHunt);
                CombatResult results = handler.fightMonster(player, monster, numTimesToHunt);

                playerDatabase.insertPlayer(player);
                playerDatabase.insertPlayerStamina(curStamina);

                channel.sendMessage(results.getCombatResultString().toString() + "\n " + results.getEntityOneStats()).queue();

            } catch(Exception e){
                System.out.println(e.getMessage());
                channel.sendMessage(user.getName() + ", Please type a valid number of times you wish to hunt that monster with \"!hunt name 1\". \"!monsters\" for list of monsters.").queue();
            }
        }
    }
}
