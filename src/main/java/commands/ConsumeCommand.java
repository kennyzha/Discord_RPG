package commands;

import config.ItemConstants;
import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Item;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.text.DecimalFormat;

public class ConsumeCommand {
    private static final String ERROR_MESSAGE = "Invalid format. Please type the item full name as it appears in your inventory with spaces. If you would like to use 5 stamina potions you would type: r!use stamina potion 5";

    public static void consumeCommand(String[] msgArr, User user, PlayerDatabase playerDatabase, MessageChannel channel, MessageHandler messageHandler, DecimalFormat format){
        if(msgArr.length < 4){
            MessageHandler.sendDefaultEmbedMessage(user, ERROR_MESSAGE, messageHandler, channel);
            return;
        }

        StringBuilder message = new StringBuilder();
        String itemName = msgArr[1];
        String itemType = msgArr[2];
        Player player = playerDatabase.grabPlayer(user.getId());

        try{
            switch(itemType){
                case "potion":
                    int amount = Integer.parseInt(msgArr[3]);

                    if(amount <= 0){
                        message.append("You can't consume non existent quantities!");
                        MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);
                        return;
                    }

                    consumePotion(player, itemName, amount, message, format);

                    MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);
                    break;
                case "scroll":
                    if(msgArr.length < 6){
                        message.append("Insufficient arguments. If you are trying to use a reset scroll, you need to type in the % of each stat" +
                                " you would like and they must add up to 100. \n e.g. r!use reset scroll 30 25 45 would set your power to 30%, speed" +
                                " to 25% and strength to 45% of your total stats.");
                        MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);
                        return;
                    }

                    int spd = Integer.parseInt(msgArr[3]);
                    int pow = Integer.parseInt(msgArr[4]);
                    int str = Integer.parseInt(msgArr[5]);

                    consumeResetScroll(itemName, player, pow, spd, str, message);
                    MessageHandler.sendDefaultEmbedMessage(user, message.toString(), messageHandler, channel);

            }
            playerDatabase.insertPlayer(player);
        } catch(NumberFormatException e){
            MessageHandler.sendDefaultEmbedMessage(user, ERROR_MESSAGE, messageHandler, channel);
        }
    }


    public static void consumePotion(Player player, String item, int amount, StringBuilder message, DecimalFormat format){
        double playerTotalStat = player.getTotalStats();

        if(item.equals("speed") && player.consumeItems(ItemConstants.SPEED_POTION.toString(), amount)){
            Item.applyPotionItem(ItemConstants.SPEED_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s speed.", amount, ItemConstants.SPEED_POTION.toString(), format.format(statGained)));
        } else if(item.equals("power") && player.consumeItems(ItemConstants.POWER_POTION.toString(), amount)){
            Item.applyPotionItem(ItemConstants.POWER_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s power.", amount, ItemConstants.POWER_POTION.toString(),  format.format(statGained)));
        } else if(item.equals("strength") && player.consumeItems(ItemConstants.STRENGTH_POTION.toString(), amount)){
            Item.applyPotionItem(ItemConstants.STRENGTH_POTION, player, amount);
            double statGained = player.getTotalStats() - playerTotalStat;
            message.append(String.format("You consumed %s %s and gained %s strength.", amount, ItemConstants.STRENGTH_POTION.toString(),  format.format(statGained)));
        } else if(item.equals("stamina") && player.consumeItems(ItemConstants.STAMINA_POTION.toString(), amount)){
            Item.applyPotionItem(ItemConstants.STAMINA_POTION, player, amount);
            message.append(String.format("You consumed %s %s and gained %s stamina", amount, ItemConstants.STAMINA_POTION.toString(),  format.format(amount * 2)));
        } else{
            message.append(String.format("You are trying to consume more than you have or you entered the wrong item name! Check your inventory with r!inventory."));
        }
    }

    public static void consumeResetScroll(String itemName, Player player, int pow, int spd, int str, StringBuilder message){
        if(itemName.equals("reset") && player.consumeItems(ItemConstants.RESET_SCROLL.toString(), 1)){
            // r!use reset scroll 33 33 34
            Item.applyResetScroll(player, pow, spd, str);
            message.append(String.format("Successfully applied reset scroll. Your stat distribution is now %s%% power, %s%% speed, and %s%% strength.", pow, spd, str));
        } else{
            message.append(String.format("You are trying to consume more than you have or you entered the wrong item name! Check your inventory with r!inventory."));
        }
    }
}
