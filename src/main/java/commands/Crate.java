package commands;

import database.PlayerDatabase;
import handlers.MessageHandler;
import models.Item;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.text.DecimalFormat;

public class Crate {
    public static final String LOW_LEVEL_ERROR_MSG = "Crates provide you with weapon and armor stats. You will unlock them at level 50.";

    public static void crateCommand(String[] msgArr, MessageChannel channel, PlayerDatabase playerDatabase, User user, MessageHandler messageHandler, boolean forage){
        Player player = playerDatabase.grabPlayer(user.getId());

        if(player != null){
            int playerLevel = player.getLevel();

            if(playerLevel < 50){
                MessageHandler.sendDefaultEmbedMessage(user, LOW_LEVEL_ERROR_MSG, messageHandler, channel);
                return;
            }

            int crateCost = models.Crate.getCrateCost(Item.getLevelBracket(playerLevel));
            int lowerBound = Item.getLowerBoundStat(playerLevel);
            int upperBound = Item.getUpperBoundStat(playerLevel);

            if(msgArr.length == 1){
                channel.sendMessage(messageHandler.createCrateEmbedMessage(user, player, crateCost, lowerBound, upperBound)).queue();
            } else if(msgArr.length == 3){
                Item.Type itemType = getItemType(msgArr[1], forage);
                int oldPlayerItemStat = getOldItemStat(player, msgArr[1], forage);

                if(itemType == null){
                    String msg = "r!crate weapon 1 or r!crate armor 1";
                    MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                } else{
                    int playerGold = player.getGold();
                    int newPlayerItemStat = 0;

                    try{
                        int numBuys = Integer.parseInt(msgArr[2]);

                        if(forage){
                            playerGold += (crateCost * numBuys);
                        } else if(numBuys > 10 || numBuys <= 0){
                            String msg = "You can only buy a max of 10 crates at a time.";
                            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                            return;
                        }

                        int totalCost = crateCost * numBuys;
                        DecimalFormat format = new DecimalFormat("#,###.##");
                        if(playerGold >= totalCost){
                            StringBuilder sb = new StringBuilder();
                            String suffix = getSuffix(itemType);

                            for(int i = 0; i < numBuys; i++){
                                Item.Rarity rarity = Item.rollItemRarity();
                                int itemRoll = Item.rollItemStat(playerLevel, rarity);
                                newPlayerItemStat = Integer.max(itemRoll, newPlayerItemStat);

                                sb.append(String.format("The crate contained a %s %s with %s %s.\n", rarity.toString(), itemType.toString().toLowerCase(), format.format(itemRoll), suffix));
                            }

                            if(itemType == Item.Type.WEAPON){
                                player.setWeapon(Math.max(oldPlayerItemStat, newPlayerItemStat));
                            } else if(itemType == Item.Type.ARMOR){
                                player.setArmor(Math.max(oldPlayerItemStat, newPlayerItemStat));
                            } else{
                                player.setAccessory(Math.max(oldPlayerItemStat, newPlayerItemStat));
                            }
                            playerGold -= totalCost;
                            player.setGold(playerGold);


                            if(Item.getItemRarity(playerLevel, newPlayerItemStat) != null && Item.getItemRarity(playerLevel, newPlayerItemStat) == Item.Rarity.LEGENDARY){
                                double oldStatTotal = player.getTotalStats();
                                player.applyLegendaryEffect();
                                double statsGained = player.getTotalStats() - oldStatTotal;

                                String legendaryEffect = "Legendary effect is applied. Total stats will increase by 100 + 5% permanently. \n Your total stats increased by " + format.format(statsGained) + ".";
                                sb.append("\n" +  legendaryEffect + "\n");
                                channel.getJDA().getGuildById("449610753566048277").getTextChannelById("486328955415298060").sendMessage(messageHandler.createDefaultEmbedMessage(user, legendaryEffect)).queue();
                            }

                            playerDatabase.insertPlayer(player);

                            channel.sendMessage(messageHandler.createCrateOpeningEmbed(user, player, sb.toString(), oldPlayerItemStat, newPlayerItemStat, Item.getItemRarity(playerLevel, newPlayerItemStat), itemType)).queue();

                        } else{
                            String msg = String.format("Failed to buy %s crates. Each crate costs %s and you only have %s gold.", numBuys, format.format(crateCost), format.format(playerGold));
                            MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                        }
                    } catch (NumberFormatException e){
                        String msg = "Please provide a valid number.";
                        MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
                    }
                }
            } else{
                String msg = "Invalid format. r!crate weapon 1 or r!crate armor 1";
                MessageHandler.sendDefaultEmbedMessage(user, msg, messageHandler, channel);
            }
        }
    }

    public static Item.Type getItemType(String str, boolean forage){
        Item.Type itemType = null;
        if(str.equals("weap") || str.equals("weapon")){
            itemType = Item.Type.WEAPON;
        } else if(str.equals("arm") || str.equals("armor")){
            itemType = Item.Type.ARMOR;
        } else if(forage && str.equals("accessory")){
            itemType = Item.Type.ACCESSORY;
        }

        return itemType;
    }

    public static int getOldItemStat(Player player, String str, boolean forage){
        int oldItemStat = 0;
        if(str.equals("weap") || str.equals("weapon")){
            oldItemStat = player.getWeapon();
        } else if(str.equals("arm") || str.equals("armor")){
            oldItemStat = player.getArmor();
        } else if(forage && str.equals("accessory")){
            oldItemStat = player.getAccessory();
        }

        return oldItemStat;
    }

    public static String getSuffix(Item.Type itemType){
        String suffix;
        if(itemType == Item.Type.ACCESSORY){
            suffix = "speed";
        } else{
            suffix = (itemType == Item.Type.WEAPON) ? "attack" : "defense";
        }
        return suffix;
    }
}
