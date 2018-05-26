package listeners;

import config.ApplicationConstants;
import database.PlayerDatabase;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }

        if(event.getAuthor().isBot()){
            return;
        }

        handleCommand(event);
    }

    public void handleCommand(MessageReceivedEvent event){
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.

        String msg = message.getContentDisplay().toLowerCase();
        String[] msgArr = msg.split(" ");

        if(msgArr.length == 0 || !msgArr[0].startsWith("!"))
            return;


        PlayerDatabase playerDatabase = new PlayerDatabase();
        Player player = playerDatabase.selectPlayer(author.getId());
        if(player == null){
            Player newPlayer = new Player(author.getId());
            playerDatabase.insertPlayer(newPlayer);
            player = newPlayer;

            Stamina stamina = new Stamina(author.getId());
            playerDatabase.insertPlayerStamina(stamina);
        }

        switch(msgArr[0]){
            case "!profile":
                if(player.getIntelligence() < 100){
                    channel.sendMessage(player.toString() + "\n\n lol nice int dumas").queue();
                } else{
                    channel.sendMessage(player.toString()).queue();
                }
                break;
            case "!help":
                channel.sendMessage(ApplicationConstants.ALL_COMMANDS).queue();
                break;
            case "!train":
                if(msgArr.length < 3){
                    channel.sendMessage(ApplicationConstants.ALL_COMMANDS).queue();
                }else{
                    String arg2 = msgArr[1].toLowerCase();
                    System.out.println("Training " + arg2);

                    try{
                        int arg3 = Integer.parseInt(msgArr[2]);

                        if((arg3 % 5) != 0){
                            channel.sendMessage("You can only train in intervals of 5 stamina.").queue();
                        } else if(arg3 < 5 || arg3 > 100){
                            channel.sendMessage("Please type in a number between 5 and 100.").queue();
                        } else{
                            Stamina curStamina = playerDatabase.retreivePlayerStamina(author.getId());
                            curStamina.updateStamina();
                            int numStamina = curStamina.getStamina();
                            int numTimesToTrain = arg3 / 5;

                            System.out.println("num stamina is " + numStamina);
                            if(numStamina < arg3){
                                channel.sendMessage("You are too tired with only " + numStamina + " stamina. You gain 5 stamina every 5 minutes.").queue();
                            }else if(arg2.equals("attack")){
                                System.out.println("attack");

                                player.incAttack(numTimesToTrain);
                                playerDatabase.insertPlayer(player);
                                curStamina.setStamina(numStamina - arg3);
                                playerDatabase.insertPlayerStamina(curStamina);

                                channel.sendMessage("Successfully trained " + arg2 + ". You have " + curStamina.getStamina() + " stamina left.").queue();
                            }else if(arg2.equals("strength")){
                                System.out.println("strength");

                                player.incStrength(numTimesToTrain);
                                playerDatabase.insertPlayer(player);
                                curStamina.setStamina(numStamina - arg3);
                                playerDatabase.insertPlayerStamina(curStamina);

                                channel.sendMessage("Successfully trained " + arg2 + ". You have " + curStamina.getStamina() + " stamina left.").queue();

                            }else if(arg2.equals("defense")){
                                System.out.println("defense");

                                player.incDefense(numTimesToTrain);
                                playerDatabase.insertPlayer(player);
                                curStamina.setStamina(numStamina - arg3);
                                playerDatabase.insertPlayerStamina(curStamina);

                                channel.sendMessage("Successfully trained " + arg2 + ". You have " + curStamina.getStamina() + " stamina left.").queue();

                            } else{
                                channel.sendMessage("Failed to train: " + arg2 + "\n" + ApplicationConstants.ALL_COMMANDS).queue();
                            }
                        }
                    } catch(Exception e){
                        channel.sendMessage("Please type in a number in intervals of 5.").queue();
                    }
                }
                break;
            case "!fight":

                break;
            default:
                channel.sendMessage("Invalid input: " + message.getContentDisplay() + "\n" + ApplicationConstants.ALL_COMMANDS).queue();
        }
    }

}
