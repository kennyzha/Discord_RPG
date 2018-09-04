package handlers;

import config.ApplicationConstants;
import config.MonsterConstants;
import models.*;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import utils.CombatResult;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MessageHandler {
    private DecimalFormat format;
    public MessageHandler(){
        format = new DecimalFormat("#,###.##");
    }

    public MessageEmbed createProfileEmbed(User user, Player player, Stamina stamina){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);

        eb.setTitle("Level " + player.getLevel() + " (" + format.format(player.getLevelExp()) + "/" + format.format(player.calcExpToNextLevel()) + ")");

        eb.addField("Health", format.format(player.getHealth()), true);
        eb.addField("Gold", format.format(player.getGold()), true);
        eb.addField("Total Stats", format.format(player.getTotalStats()), true);
        eb.addField("Power", format.format(player.getPower()) + " (" + player.getPowerPercentage() + "%)", true);
        eb.addField("Speed", format.format(player.getSpeed()) + " (" + player.getSpeedPercentage() + "%)", true);
        eb.addField("Strength", format.format(player.getStrength()) + " (" + player.getStrengthPercentage() + "%)", true);

        if(player.getLevel() >= 50){
            eb.addField("Weapon", format.format(player.getWeapon()), true);
            eb.addBlankField(true);
            eb.addField("Armor", format.format(player.getArmor()), true);
        }

        eb.setFooter("Stamina: " + stamina.getStamina() + " / 20", null);
        return eb.build();
    }

    public MessageEmbed createEmbedFightMessage(User user, String enemyName, CombatResult combatResult){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);

        eb.setTitle(user.getName() + " vs " + enemyName);
        eb.setDescription(combatResult.getCombatResultString() + "\n" + combatResult.getEntityOneStats().toString());
        eb.setThumbnail(ApplicationConstants.FIGHT_IMG);
        return eb.build();
    }


    public MessageEmbed createEmbedTrainMessage(User user, double statGained, String statType, int staminaUsed, int staminaLeft){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);

        eb.setDescription(String.format("You gained " + statGained + " %s.", statType));
        eb.setFooter(String.format("Stamina: %s / 20 (-%s)", staminaLeft + staminaUsed, staminaUsed), null);

        if(statType.equals("power"))
            eb.setThumbnail(ApplicationConstants.POWER_IMG);
        else if(statType.equals("speed")){
            eb.setThumbnail(ApplicationConstants.SPEED_IMG);
        } else if(statType.equals("strength")){
            eb.setThumbnail(ApplicationConstants.STRENGTH_IMG);
        }
        return eb.build();
    }

    public MessageEmbed createEmbedMonsterListMessage(User user, int playerLevel){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);

        ArrayList<Monster> monsters = MonsterConstants.getMonsters();
        for(Monster m: monsters){
            int levelDiff = Math.abs(m.getLevel() - playerLevel);

            if(levelDiff <= 100){
                eb.addField(m.getName() + " (Level " + m.getLevel() + ")", m.toString(), false);
            }
        }

        return eb.build();
    }

    public MessageEmbed createDefaultEmbedMessage(User user, String description){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);
        eb.setDescription(description);

        return eb.build();
    }

    public void setEmbedMessageDefaults(EmbedBuilder eb, User user){
        eb.setAuthor(user.getName(), null, user.getEffectiveAvatarUrl());
        eb.setColor(Color.CYAN);
    }

    public MessageEmbed createHighscoreEmbedMessage(User user, ArrayList<Player> players, JDA jda, String highscoreType){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);
        eb.setTitle(highscoreType + " Highscore");
        eb.setFooter("Updated daily", null);

        int rankCounter = 1;
        for(Player p: players){
            User curUser = jda.getUserById(p.getId());
            if(curUser == null){
                continue;
            }
            
            eb.appendDescription(rankCounter + ". " + curUser.getName() + "#" + curUser.getDiscriminator() + " (Level " + p.getLevel() + ")\n");
            eb.appendDescription(String.format("   Power: %s Speed: %s Strength: %s Total: %s\n\n", format.format(p.getPower()), format.format(p.getSpeed()), format.format(p.getStrength()), format.format(p.getTotalStats())));
            rankCounter++;
        }

        return eb.build();
    }

    public MessageEmbed createTotalHighscoreEmbedMessage(User user, ArrayList<Player> players, JDA jda, String highscoreType) {
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);
        eb.setTitle(highscoreType + " Highscore");
        eb.setFooter("Updated daily", null);

        int rankCounter = 1;
        for (Player p : players) {
            User curUser = jda.getUserById(p.getId());
            if (curUser == null) {
                continue;
            }

            eb.appendDescription(rankCounter + ". " + curUser.getName() + "#" + curUser.getDiscriminator() + " (Level " + p.getLevel() + ")" + "\n" +
                        "   Total Stats: " + format.format(p.getTotalStats()) + "\n\n");
            rankCounter++;
        }

        return eb.build();
    }

    public MessageEmbed createGoldHighscoreEmbedMessage(User user, ArrayList<Player> players, JDA jda, String highscoreType) {
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);
        eb.setTitle(highscoreType + " Highscore");
        eb.setFooter("Updated daily", null);

        int rankCounter = 1;
        for (Player p : players) {
            User curUser = jda.getUserById(p.getId());
            if (curUser == null) {
                continue;
            }

            eb.appendDescription(rankCounter + ". " + curUser.getName() + "#" + curUser.getDiscriminator() + " (Level " + p.getLevel() + ")" + "\n"
                                + "   Total Gold: " + format.format(p.getGold()) + "\n\n");
            rankCounter++;
        }

        return eb.build();
    }

    public MessageEmbed createCrateEmbedMessage(User user, Player player, int cost, int itemLowerBound, int itemUpperBound){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);

        eb.appendDescription(String.format("Your crate currently costs %s each.\nYour item can roll %s ~ %s ATK/DEF.\n\nThe cost and stat range increases every 50 level interval.\nOne Attack/Defense is similar to 1/4 point of power/strength.\nLegendary items give 5 percent permanent stats.",
                format.format(cost) , format.format(itemLowerBound), format.format(itemUpperBound)));
        eb.setThumbnail("https://i.imgur.com/OYIWNY7.png");
        eb.setFooter(String.format("Weapon: %s ATK  Armor: %s DEF", format.format(player.getWeapon()), format.format(player.getArmor())), null);
        return eb.build();
    }

    public MessageEmbed createCrateOpeningEmbed(User user, Player player, String crateSummary, int oldItemRoll, int itemRoll, Item.Rarity rarity, Item.Type itemType){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);

        String suffix = itemType == Item.Type.WEAPON ? "attack" : "defense";

        eb.appendDescription(crateSummary);

        String itemTypeString = itemType.toString().toLowerCase();
        if(oldItemRoll >= itemRoll){
            eb.appendDescription(String.format("\nYou decided to keep using your %s %s %s.",  format.format(oldItemRoll), suffix, itemTypeString));
        } else{
            eb.appendDescription(String.format("\nYou replaced your old %s %s %s with a shiny new %s %s %s %s.", format.format(oldItemRoll), suffix, itemTypeString, format.format(itemRoll), suffix, Item.getItemRarity(player.getLevel(), itemRoll), itemTypeString));
        }

        String img = "https://i.imgur.com/OYIWNY7.png";

        switch(rarity){
            case COMMON:
                img = (itemType == Item.Type.WEAPON) ? ApplicationConstants.WEAPON_COMMON_IMG : ApplicationConstants.ARMOR_COMMON_IMG;
                break;
            case UNCOMMON:
                img = (itemType == Item.Type.WEAPON) ? ApplicationConstants.WEAPON_UNCOMMON_IMG : ApplicationConstants.ARMOR_UNCOMMON_IMG;
                break;
            case RARE:
                img = (itemType == Item.Type.WEAPON) ? ApplicationConstants.WEAPON_RARE_IMG : ApplicationConstants.ARMOR_RARE_IMG;
                break;
            case EPIC:
                img = (itemType == Item.Type.WEAPON) ? ApplicationConstants.WEAPON_EPIC_IMG : ApplicationConstants.ARMOR_EPIC_IMG;
                break;
            case LEGENDARY:
                img = (itemType == Item.Type.WEAPON) ? ApplicationConstants.WEAPON_LEGENDARY_IMG : ApplicationConstants.ARMOR_LEGENDARY_IMG;
                break;
        }

        eb.setThumbnail(img);
        eb.setFooter(String.format("Min Roll: %s Max Roll: %s", format.format(Item.getLowerBoundStat(player.getLevel())), format.format(Item.getUpperBoundStat(player.getLevel()))), null);

        return eb.build();
    }
}
