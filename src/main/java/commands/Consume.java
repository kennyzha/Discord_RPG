package commands;

import config.ItemConstants;
import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Item;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.text.DecimalFormat;

public class Consume {
    private static final String ERROR_MESSAGE = "Invalid format. Please type the item full name as it appears in your inventory with spaces. If you would like to use 5 stamina potions you would type: r!use stamina potion 5";

    public static void consumeCommand(String[] msgArr, User user, PlayerDatabase playerDatabase, MessageChannel channel, MessageHandler messageHandler, DecimalFormat format){
        if(msgArr.length < 4){
            MessageHandler.sendDefaultEmbedMessage(user, ERROR_MESSAGE, messageHandler, channel);
            return;
        }

        StringBuilder message = new StringBuilder();
        String item = msgArr[1];

        try{
            int amount = Integer.parseInt(msgArr[3]);

            if(amount <= 0){
                message.append("You can't consume non existent quantities!");
                MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);
                return;
            }

            Player player = playerDatabase.grabPlayer(user.getId());
            consumeItem(player, item, amount, message, format);

            MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);
            playerDatabase.insertPlayer(player);
        } catch(NumberFormatException e){
            MessageHandler.sendDefaultEmbedMessage(user, ERROR_MESSAGE, messageHandler, channel);
        }
    }


    public static void consumeItem(Player player, String item, int amount, StringBuilder message, DecimalFormat format){
        double playerTotalStat = player.getTotalStats();

        if(item.equals("speed") && player.consumeItems(ItemConstants.SPEED_POTION.toString(), amount)){
            Item.usePotionItem(ItemConstants.SPEED_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s speed.", amount, ItemConstants.SPEED_POTION.toString(), format.format(statGained)));
        } else if(item.equals("power") && player.consumeItems(ItemConstants.POWER_POTION.toString(), amount)){
            Item.usePotionItem(ItemConstants.POWER_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s power.", amount, ItemConstants.POWER_POTION.toString(),  format.format(statGained)));
        } else if(item.equals("strength") && player.consumeItems(ItemConstants.STRENGTH_POTION.toString(), amount)){
            Item.usePotionItem(ItemConstants.STRENGTH_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s strength.", amount, ItemConstants.STRENGTH_POTION.toString(),  format.format(statGained)));
        } else if(item.equals("stamina") && player.consumeItems(ItemConstants.STAMINA_POTION.toString(), amount)){
            Item.usePotionItem(ItemConstants.STAMINA_POTION, player, amount);
            message.append(String.format("You consumed %s %s and gained %s stamina", amount, ItemConstants.STAMINA_POTION.toString(),  format.format(amount * 2)));
        } else if(item.equals("reset") && player.consumeItems(ItemConstants.RESET_SCROLL.toString(), 1)){
            // r!use reset scroll 33 33 34

        }
        else{
            message.append(String.format("You are trying to consume more than you have or you entered the wrong item name! Check your inventory with r!inventory."));
        }
    }
}
