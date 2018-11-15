package commands;

import config.ApplicationConstants;
import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Item;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CollectCommand {
    public static void collect(User user, PlayerDatabase playerDatabase, MessageHandler messageHandler, MessageChannel channel){
        Player curPlayer = playerDatabase.grabPlayer(user.getId());
        if(curPlayer.getKeyword() == null || !curPlayer.getKeyword().equals(ApplicationConstants.KEYWORD)){
            int crateCost = models.Crate.getCrateCost(Item.getLevelBracket(curPlayer.getLevel()));
            int goldIncrease = crateCost * 5;

            curPlayer.increGold(goldIncrease);
            curPlayer.setKeyword(ApplicationConstants.KEYWORD);
            playerDatabase.insertPlayer(curPlayer);

            String msg = String.format("You have collected %s gold.", goldIncrease);
            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
        }  else{
            String msg = "You have already collected your gold.";
            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
        }
    }
}
