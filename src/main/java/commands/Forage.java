package commands;

import config.ApplicationConstants;
import config.ItemConstants;
import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Item;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Forage {
    public static void forageCommand(String[] msgArr, MessageChannel channel, PlayerDatabase playerDatabase, User user, MessageHandler messageHandler, DecimalFormat format){
        Player player = playerDatabase.grabPlayer(user.getId());
        String msg = "";

        if(msgArr.length == 1){
            msg = String.format("You can forage 20 times a day. You have already foraged %s times today.", player.getForageAmount());
            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
            return;
        }
        try{
            int amount = Integer.parseInt(msgArr[1]);

            if(amount > player.getStamina()){
                amount = player.getStamina();
            }

            if(amount > 20 || amount <= 0 || player.getForageAmount() + amount > 20){
                msg = String.format("You can only forage 20 times a day and each time forage consumes 1 stamina. You have already foraged %s times today.", player.getForageAmount());
                MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                return;
            }

            StringBuilder sb = new StringBuilder();
            int accessoryCratesFound = 0;
            int speedPotionsFound = 0;
            int powerPotionsFound = 0;
            int strengthPotionsFound = 0;

            for(int i = 0; i < amount; i++){
                int roll = (int) (Math.random() * 1000) + 1;
                Item itemRolled;
                if(roll > 200){
                    int potionRoll = (int) (Math.random() * 3);

                    if(potionRoll == 0){
                        speedPotionsFound++;
                    } else if(potionRoll == 1){
                        powerPotionsFound++;
                    } else{
                        strengthPotionsFound++;
                    }
                } else if(roll > 100){
                    itemRolled = ItemConstants.STAMINA_POTION;
                    Integer staminaPotionsOwned = player.getInventory().get(itemRolled.toString());

                    if(staminaPotionsOwned == null || staminaPotionsOwned <= ApplicationConstants.INVENTORY_LIMIT){
                        player.addItem(itemRolled.toString(), 1);
                        sb.append(String.format("You searched around and found a %s!\n", itemRolled.toString()));
                    } else {
                        sb.append(String.format("Your can't hold any more stamina potions so you decided to drink the stamina" +
                                " potion and gain 2 stamina.\n", itemRolled.toString()));
                        player.setStamina(player.getStamina() + 2);
                    }
                } else if(roll > 50){
                    player.increExp(player.calcExpToNextLevel());
                    player.updateLevelAndExp();
                    sb.append("You wandered around aimlessly and magically gained a level!\n");
                } else{
                    if(player.getLevel() < 50){
                        player.increExp(player.calcExpToNextLevel());
                        player.updateLevelAndExp();
                        player.increExp(player.calcExpToNextLevel());
                        player.updateLevelAndExp();
                        sb.append("You wandered around aimlessly and magically gained two levels!\n");
                    } else{
                        sb.append("You found an accessory crate lying on the floor!\n");
                        accessoryCratesFound++;
                    }
                }
            }

            int speedPotionExcess = 0;
            int powerPotionExcess = 0;
            int strengthPotionExcess = 0;

            if(speedPotionsFound > 0){
                speedPotionExcess = getExcessAmount(player, speedPotionsFound, ItemConstants.SPEED_POTION, sb);
            }

            if(powerPotionsFound > 0){
                powerPotionExcess = getExcessAmount(player, powerPotionsFound, ItemConstants.POWER_POTION, sb);
            }

            if(strengthPotionsFound > 0){
                strengthPotionExcess = getExcessAmount(player, powerPotionsFound, ItemConstants.STRENGTH_POTION, sb);
            }

            String footer = "Forage: " + (20 - player.getForageAmount()) + " / " + 20 + " (-" + amount + ")";
            MessageHandler.sendDefaultEmbedMessageWithFooter(user, sb.toString(), messageHandler, channel, footer);

            player.setForageAmount(player.getForageAmount() + amount);
            player.setForageDate(LocalDate.now().toString());
            player.setStamina(player.getStamina() - amount);
            playerDatabase.insertPlayer(player);

            consumeExcessPotions(user, playerDatabase, channel, messageHandler, format, speedPotionExcess, powerPotionExcess, strengthPotionExcess);


            if(accessoryCratesFound > 0){
                Crate.crateCommand(new String[]{"r!crate", "accessory", Integer.toString(accessoryCratesFound)}, channel, playerDatabase, user, messageHandler, true);
            }
        } catch(NumberFormatException e){
            msg = String.format("Please enter a a valid number. You can only forage 20 times a day. You have already foraged %s times today.", player.getForageAmount());
            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
        }
    }

    private static void consumeExcessPotions(User user, PlayerDatabase playerDatabase, MessageChannel channel, MessageHandler messageHandler,
                                             DecimalFormat format, int speedPotionExcess, int powerPotionExcess, int strengthPotionExcess) {
        String potionStr = "";
        if(speedPotionExcess > 0){
            potionStr = "r!consume speed potion";
            Consume.consumeCommand(potionStr.split(" "), user, playerDatabase, channel, messageHandler, format);
        }

        if(powerPotionExcess > 0){
            potionStr = "r!consume power potion";
            Consume.consumeCommand(potionStr.split(" "), user, playerDatabase, channel, messageHandler, format);
        }

        if(strengthPotionExcess > 0){
            potionStr = "r!consume strength potion";
            Consume.consumeCommand(potionStr.split(" "), user, playerDatabase, channel, messageHandler, format);
        }
    }

    public static int getExcessAmount(Player player, int potionAmount, Item item, StringBuilder sb){
        String itemName = item.toString().toLowerCase();
        int potionExcess = 0;

        if(potionAmount == 1){
            sb.append(String.format("You searched around and found %s %s!\n", potionAmount, itemName));
        } else {
            sb.append(String.format("You searched around and found %s %ss!\n", potionAmount, itemName));

        }
        player.addItem(itemName, potionAmount);
        Integer speedPotionsOwned = player.getInventory().get(itemName);
        if(speedPotionsOwned != null && speedPotionsOwned > 100){
            potionExcess = speedPotionsOwned - ApplicationConstants.INVENTORY_LIMIT;

            if (potionExcess == 1) {
                sb.append(String.format("You decided to drink a %s because you can only hold a max of 100 of each item.\n", itemName));
            } else {
                sb.append(String.format("You decided to drink %s %ss because you can only hold a max of 100 of each item.\n", potionExcess, itemName));
            }
        }

        return potionExcess;
    }
}
