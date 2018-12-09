package handlers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import commands.*;

import config.ApplicationConstants;
import database.PlayerDatabase;
import models.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.CombatResult;
import utils.Donator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandHandler {

    private PlayerDatabase playerDatabase;
    private MessageHandler messageHandler;
    private HighscoreHandler highscoreHandler;
    private DecimalFormat format;

    private final String COMMAND_PREFIX = "r!";
    public CommandHandler(PlayerDatabase playerDatabase, MessageHandler messageHandler, HighscoreHandler highscoreHandler){
        this.playerDatabase = playerDatabase;
        this.messageHandler = messageHandler;
        this.highscoreHandler = highscoreHandler;
        format = new DecimalFormat("#,###.###");
    }

    public void handleCommand(MessageReceivedEvent event){
        User user = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay().toLowerCase();
        String[] msgArr = msg.split(" ");

        if(msgArr.length == 0 || !msgArr[0].startsWith(COMMAND_PREFIX))
            return;


        if(!handleStaticCommands(msgArr, channel, user) && !handleDynamicCommands(msgArr, channel, user, message, event)){
            String str = "Command not recognized: " + msgArr[0] + ". Type r!commands for list of commands.";
            sendDefaultEmbedMessage(user, str, messageHandler, channel);
        }
    }

    public boolean handleStaticCommands(String[] msgArr, MessageChannel channel, User user){
        switch(msgArr[0]){
            case "r!help":
                help(channel, user);
                break;
            case "r!monster":
            case "r!monsters":
                monsters(channel, user);
                break;
            case "r!server":
                String link = "Link to official RPG server.  Join for update announcements and to give feedback to help shape the development of the game.\n\nhttps://discord.gg/3Gq4kAr";
                sendDefaultEmbedMessage(user,link, messageHandler, channel);
                break;
            case "r!commands":
            case "r!command":
                commands(channel, user);
                break;
            case "r!credits":
                String credits = "The concept of power, speed, and strength is based on an old school RPG game called hobowars. " +
                        "The item icons used in this are available on https://game-icons.net";
                sendDefaultEmbedMessage(user, credits, messageHandler, channel);
                break;
            case "r!vote":
            case "r!votes":
            case "r!daily":
                vote(channel, user);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean handleDynamicCommands(String[] msgArr, MessageChannel channel, User user, Message message, Event event){
        switch(msgArr[0]){
            case "r!profile":
            case "r!prof":
            case "r!p":
                ProfileCommand.profileCommand(message, playerDatabase, user, channel, messageHandler);
                break;
            case "r!train":
            case "r!t":
                TrainCommand.trainCommand(msgArr, channel, playerDatabase, user, messageHandler);
                break;
            case "r!stamina":
                stamina(channel, user);
                break;
            case "r!fight":
                FightCommand.fightCommand(message, user, playerDatabase, channel, messageHandler);
                break;
            case "r!hunt":
            case "r!h":
                hunt(channel, msgArr, user);
                break;
            case "r!highscore":
            case "r!highscores":
            case "r!leaderboards":
            case "r!leaderboard":
                highscore(channel, msgArr, user, event.getJDA());
                break;
            case "r!crate":
            case"r!crates":
                CrateCommand.crateCommand(msgArr, channel, playerDatabase, user, messageHandler, false);
                break;
            case "r!gamble":
            case "r!bet":
                gamble(channel, msgArr, user);
                break;
            case "r!forage":
                ForageCommand.forageCommand(msgArr, channel, playerDatabase, user, messageHandler, format);
                break;
            case "r!consume":
            case "r!item":
            case "r!use":
                ConsumeCommand.consumeCommand(msgArr, user, playerDatabase, channel, messageHandler, format);
                break;
            case "r!inventory":
            case "r!inven":
            case "r!i":
            case "r!inv":
                inventory(channel, user);
                break;
/*            case "r!donator":
                Player player = playerDatabase.grabPlayer(user.getId());
                String str = "Player is a donator. " + Donator.isDonator(player);
                str += " - " + player.getDonatorEndTime();
                System.out.println(str);
//                Donator.applyDonatorPacks(player, 1);
                System.out.println("Donator.isDonator(player) = " + Donator.isDonator(player));
                System.out.println("Donator.getDonatorTimeDays(ok) = " + Donator.getDonatorTimeDays(player));
//                playerDatabase.insertPlayer(player);

                break;*/
/*            case "r!collect":
                CollectCommand.collect(user, playerDatabase, messageHandler, channel);
                break;*/
            default:
                return false;
        }

        return true;
    }

        private void inventory(MessageChannel channel, User user) {
        Player player = playerDatabase.grabPlayer(user.getId());
        channel.sendMessage(messageHandler.createEmbedInventory(user, player)).queue();
    }

    private void gamble(MessageChannel channel, String[] msgArr, User user) {

        if(msgArr.length == 1){
            sendDefaultEmbedMessage(user, "A random number will be generated between 0 and 100. You win twice your bet amount if the number is greater than 50. r!gamble 500", messageHandler, channel);
            return;
        }

        try{
            int betAmount = Integer.parseInt(msgArr[1]);

            if(betAmount < 100 || betAmount > 500000){
                sendDefaultEmbedMessage(user, "Minimum wager is 100 gold and maximum wager is 500,000 gold.", messageHandler, channel);
                return;
            }

            Player player = playerDatabase.grabPlayer(user.getId());
            int playerGold = player.getGold();

            if(playerGold < betAmount){
                sendDefaultEmbedMessage(user, String.format("Unable to wage %s due to insufficient gold. You only have %s gold.", betAmount, playerGold), messageHandler, channel);
            } else{
                int roll = (int) (Math.random() * 101);
                String result = "";
                if(roll > 50){
                    playerGold += betAmount;
                    result = String.format("You rolled a %s. You won %s gold! You now have %s gold.", roll, betAmount, playerGold);
                } else{
                    playerGold -= betAmount;
                    result = String.format("You rolled a %s. You lost %s gold! You now have %s gold.", roll, betAmount, playerGold);
                }

                player.setGold(playerGold);
                playerDatabase.insertPlayer(player);
                sendDefaultEmbedMessage(user, result, messageHandler, channel);
            }
        } catch(NumberFormatException e){
            sendDefaultEmbedMessage(user, "Please enter a number. r!gamble NUMBER", messageHandler, channel);

        }
    }

    public void vote(MessageChannel channel, User user) {
        String msg = "You may vote for the bot every 12 hours. As a reward, you will get additional stamina each time you vote." +
                " On Friday, Saturday, and Sunday, you will also get a crate worth of gold added to your account. Thanks for supporting Discord RPG!\n\n " +
                "https://discordbots.org/bot/449444515548495882";

        sendDefaultEmbedMessage(user, msg, messageHandler, channel);
    }

    public void help(MessageChannel channel, User user){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ApplicationConstants.HELP_STRING)).queue();;
    }

    public void commands(MessageChannel channel, User user){
        user.openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ApplicationConstants.VERBOSE_COMMANDS)).queue();;
        });

        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Messaged you the list of commands.")).queue();
    }


    public void stamina(MessageChannel channel, User user){
        int curStamina = playerDatabase.grabPlayer(user.getId()).getStamina();

        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "You currently have " + curStamina + " stamina.")).queue();

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

            try{
                int numTimesToHunt = Math.min(Integer.parseInt(msgArr[2]), player.getStamina());

                if(numTimesToHunt < 0){
                    sendDefaultEmbedMessage(user, "Please enter a valid number.", messageHandler, channel);
                    return;
                }
                if(numTimesToHunt == 0){
                    sendDefaultEmbedMessage(user, "You are too tired to hunt monsters. You recover 1 stamina every 5 minutes.", messageHandler, channel);
                    return;
                } else if(numTimesToHunt > 20 || numTimesToHunt < 0){
                    sendDefaultEmbedMessage(user, "You can only hunt a maximum of 20 monsters at a time. If you hunt too many at once they might go extinct!", messageHandler, channel);
                    return;
                }

                player.setStamina(player.getStamina() - numTimesToHunt);
                CombatResult pvmResults = handler.fightMonster(player, monster, numTimesToHunt);

                playerDatabase.insertPlayer(player);

                channel.sendMessage(messageHandler.createEmbedFightMessage(user, monster.getName(), pvmResults)).queue();

            } catch(Exception e){
                sendDefaultEmbedMessage(user, "Please type a valid number of times you wish to hunt that monster with. e.g. \"r!hunt slime 1\". \"r!monsters\" for list of monsters.", messageHandler, channel);
            }
        }
    }

    public void monsters(MessageChannel channel, User user){
        Player player = playerDatabase.grabPlayer(user.getId());

        user.openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(messageHandler.createEmbedMonsterListMessage(user, player.getLevel())).queue();
        });

        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Messaged you the list of monsters within 100 levels.")).queue();
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
        channel.sendMessage(messageHandler.createHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
    }

    public void sendDefaultEmbedMessage(User user, String description, MessageHandler messageHandler, MessageChannel channel){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, description)).queue();
    }

    public void sendDefaultEmbedMessageWithFooter(User user, String description, MessageHandler messageHandler, MessageChannel channel, String footer){
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, description, footer)).queue();
    }
}
