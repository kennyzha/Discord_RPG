package listeners;

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

        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.

        System.out.println(author.getId());

        String msg = event.getMessage().getContentDisplay();

        String[] msgArr = msg.split(" ");

        channel.sendMessage(msg).queue();
        handleCommand(msgArr);

        PlayerDatabase db = new PlayerDatabase();
        Player player = new Player(author.getId());


        db.insertPlayer(player);
        Player retrievedPlayer = db.selectPlayer(player.getId());
        System.out.println(retrievedPlayer.getLevel());

    }

    public void handleCommand(String[] msgArr){
        for(String s: msgArr){
            System.out.println(s);
        }
    }
}
