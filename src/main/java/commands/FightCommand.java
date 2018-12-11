package commands;

import database.PlayerDatabase;
import handlers.CombatHandler;
import handlers.MessageHandler;
import models.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import utils.CombatResult;

public class FightCommand {
    public static void fightCommand(Message message, User user, PlayerDatabase playerDatabase, MessageChannel channel, MessageHandler messageHandler){
        String[] msgArr = message.getContentDisplay().split(" ");

        CombatHandler combatHandler = new CombatHandler();
        if(msgArr.length < 2){
            MessageHandler.sendDefaultEmbedMessage(user, "Please mention the name of the user you wish to fight with !fight @name.", messageHandler, channel);
        } else{
            Player mentionedPlayer = playerDatabase.grabMentionedPlayer(message, channel, "fight");

            if(mentionedPlayer != null){
                String enemyName = message.getMentionedUsers().get(0).getName();

                Player player = playerDatabase.grabPlayer(user.getId());
                CombatResult pvpResults = combatHandler.playerFight(player, mentionedPlayer);

                channel.sendMessage(messageHandler.createEmbedFightMessage(user, enemyName, pvpResults)).queue();
            }
        }
    }
}
