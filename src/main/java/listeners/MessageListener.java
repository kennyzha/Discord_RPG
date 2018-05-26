package listeners;

import config.ApplicationConstants;
import database.PlayerDatabase;
import models.Player;
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
            System.out.println("Adding new player to database with id " + author.getId());
            Player newPlayer = new Player(author.getId());
            playerDatabase.insertPlayer(newPlayer);
            player = newPlayer;
        }

        switch(msgArr[0]){
            case "!profile":
                channel.sendMessage(player.toString()).queue();
                break;
            case "!help":
                channel.sendMessage(ApplicationConstants.ALL_COMMANDS).queue();
                break;
            case "!train":
                if(msgArr.length < 2){
                    channel.sendMessage(ApplicationConstants.ALL_COMMANDS).queue();
                }else{
                    String arg2 = msgArr[1].toLowerCase();
                    System.out.println("Training " + arg2);

                    if(arg2.equals("attack")){
                        System.out.println("attack");
                    }else if(arg2.equals("strength")){
                        System.out.println("strength");

                    }else if(arg2.equals("defense")){
                        System.out.println("defense");
                    }
                }

                break;
            default:
                channel.sendMessage("Invalid input: " + message.getContentDisplay() + "\n" + ApplicationConstants.ALL_COMMANDS).queue();
        }
    }

}
