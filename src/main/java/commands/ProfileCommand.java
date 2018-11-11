package commands;

import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ProfileCommand {

    public static void profileCommand(Message message, PlayerDatabase playerDatabase, User user, MessageChannel channel, MessageHandler messageHandler){
        String[] msgArr = message.getContentDisplay().split(" ");

        if(msgArr.length == 1){
            Player player = playerDatabase.grabPlayer(user.getId());

            if(player != null){
                channel.sendMessage(messageHandler.createEmbedProfile(user, player)).queue();
            }
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "profile");

            if(mentionedPlayer != null){
                User mentionedUser = message.getMentionedMembers().get(0).getUser();
                channel.sendMessage(messageHandler.createEmbedProfile(mentionedUser, mentionedPlayer)).queue();
            }
        }
    }
}
