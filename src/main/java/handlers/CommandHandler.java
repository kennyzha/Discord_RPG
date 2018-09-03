package handlers;

import config.ApplicationConstants;
import database.PlayerDatabase;
import models.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.CombatResult;

import java.text.DecimalFormat;
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
        User user = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay().toLowerCase();
        String[] msgArr = msg.split(" ");

        if(msgArr.length == 0 || !msgArr[0].startsWith(COMMAND_PREFIX))
            return;

        switch(msgArr[0]){
            case "r!profile":
                profile(channel, user, message);
                break;
            case "r!help":
                help(channel, user);
                break;
            case "r!train":
            case "r!t":
               train(channel, msgArr, user);
                break;
            case "r!stamina":
                stamina(channel, user);
                break;
            case "r!fight":
               fight(channel, message, user);
                break;
            case "r!hunt":
            case "r!h":
                hunt(channel, msgArr, user);
                break;
            case "r!monster":
            case "r!monsters":
                monsters(channel, user);
                break;
            case"r!highscore":
            case"r!highscores":
                highscore(channel, msgArr, user, event.getJDA());
                break;
            case "r!crate":
            case"r!crates":
                crate(channel, msgArr, user);
                break;
             case "r!server":
                String link = "Link to official RPG server.  Join for update announcements and to give feedback to help shape the development of the game.\n\nhttps://discord.gg/3Gq4kAr";
                sendDefaultEmbedMessage(user,link, messageHandler, channel);
                break;
            case "r!commands":
                commands(channel, user);
                break;
            case "r!credits":
                String credits = "The concept of power, speed, and strength is based on an old school RPG game called hobowars. " +
                        "The game icons used in this are available on https://game-icons.net";
                sendDefaultEmbedMessage(user, credits, messageHandler, channel);
                break;
            case "r!vote":
            case "r!votes":
            case "r!daily":
                vote(channel, user);
                break;
            default:
                String str = "Command not recognized: " + message.getContentDisplay() + ". Type r!commands for list of commands.";
                sendDefaultEmbedMessage(user, str, messageHandler, channel);
        }
    }

    public void vote(MessageChannel channel, User user) {
        String msg = "You may vote for the bot every 12 hours. Your stamina will reset to 20 each time you vote. In addition to the " +
                "stamina reset, on Friday, Saturday, and Sunday's you will get a crate worth of gold added to your account. + \n\n " +
                " Vote Link: https://discordbots.org/bot/449444515548495882";
    }

    public void crate(MessageChannel channel, String[] msgArr, User user){
        Player player = playerDatabase.grabPlayer(user.getId());

        if(player != null){
            int playerLevel = player.getLevel();

            if(playerLevel < 50){
                sendDefaultEmbedMessage(user, "Crates provide you with weapon and armor stats. You will unlock them at level 50.", messageHandler, channel);
                return;
            }

            int crateCost = Crate.cost[Item.getLevelBracket(playerLevel)];
            int lowerBound = Item.getLowerBoundStat(playerLevel);
            int upperBound = Item.getUpperBoundStat(playerLevel);

            if(msgArr.length == 1){
                channel.sendMessage(messageHandler.createCrateEmbedMessage(user, player, crateCost, lowerBound, upperBound)).queue();
            } else if(msgArr.length == 3){
                Item.Type itemType = null;
                int oldPlayerItemStat = 0;

                if(msgArr[1].equals("weap") || msgArr[1].equals("weapon")){
                    itemType = Item.Type.WEAPON;
                    oldPlayerItemStat = player.getWeapon();
                } else if(msgArr[1].equals("arm") || msgArr[1].equals("armor")){
                    itemType = Item.Type.ARMOR;
                    oldPlayerItemStat = player.getArmor();
                }

                if(itemType == null){
                    String msg = "r!crate weapon 1 or r!crate armor 1";
                    sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                } else{
                    int playerGold = player.getGold();
                    int newPlayerItemStat = 0;

                    try{
                        int numBuys = Integer.parseInt(msgArr[2]);

                        if(numBuys > 10 && numBuys > 0){
                            String msg = "You can only buy a max of 10 crates at a time.";
                            sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                            return;
                        }

                        int totalCost = crateCost * numBuys;
                        DecimalFormat format = new DecimalFormat("#,###.##");
                        if(playerGold >= totalCost){
                            StringBuilder sb = new StringBuilder();
                            String suffix = (itemType == Item.Type.WEAPON) ? "attack" : "defense";

                            for(int i = 0; i < numBuys; i++){
                                Item.Rarity rarity = Item.rollItemRarity();
                                int itemRoll = Item.rollItemStat(playerLevel, rarity);
                                newPlayerItemStat = Integer.max(itemRoll, newPlayerItemStat);

                                sb.append(String.format("The crate contained a %s %s with %s %s.\n", rarity.toString(), itemType.toString().toLowerCase(), format.format(itemRoll), suffix));
                            }

                            if(itemType == Item.Type.WEAPON){
                                player.setWeapon(Math.max(oldPlayerItemStat, newPlayerItemStat));
                            } else{
                                player.setArmor(Math.max(oldPlayerItemStat, newPlayerItemStat));
                            }
                            playerGold -= totalCost;
                            player.setGold(playerGold);

                            if(Item.getItemRarity(playerLevel, newPlayerItemStat) != null && Item.getItemRarity(playerLevel, newPlayerItemStat) == Item.Rarity.LEGENDARY){
                                double oldStatTotal = player.getTotalStats();
                                player.applyLegendaryEffect();
                                double statsGained = player.getTotalStats() - oldStatTotal;

                                sb.append("\nLegendary effect is applied. Total stats will increase by 5% permanently. Your total stats increased by " + format.format(statsGained) + "\n");

                            }

                            playerDatabase.insertPlayer(player);

                            channel.sendMessage(messageHandler.createCrateOpeningEmbed(user, player, sb.toString(), oldPlayerItemStat, newPlayerItemStat, Item.getItemRarity(playerLevel, newPlayerItemStat), itemType)).queue();

                        } else{
                            String msg = String.format("Failed to buy %s crates. Each crate costs %s and you only have %s gold.", numBuys, format.format(crateCost), format.format(playerGold));
                            sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                        }
                    } catch (NumberFormatException e){
                        String msg = "Please provide a valid number.";
                        sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                    }
                }
            } else{
                String msg = "Invalid format. r!crate weapon 1 or r!crate armor 1";
                sendDefaultEmbedMessage(user, msg, messageHandler, channel);
            }
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
        user.openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ApplicationConstants.VERBOSE_COMMANDS)).queue();;
        });

        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Messaged you the list of commands.")).queue();    }

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
}
