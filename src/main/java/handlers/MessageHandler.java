package handlers;

import config.ApplicationConstants;
import config.MonsterConstants;
import models.CombatResult;
import models.Monster;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

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
        eb.setDescription(String.format("You gained " + statGained + " %s. You used %s stamina and now have %s stamina left.", statType, staminaUsed, staminaLeft));

        if(statType.equals("power"))
            eb.setThumbnail(ApplicationConstants.POWER_IMG);
        else if(statType.equals("speed")){
            eb.setThumbnail(ApplicationConstants.SPEED_IMG);
        } else if(statType.equals("strength")){
            eb.setThumbnail(ApplicationConstants.STRENGTH_IMG);
        }
        return eb.build();
    }

    public MessageEmbed createEmbedMonsterListMessage(User user){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);

        ArrayList<Monster> monsters = MonsterConstants.getMonsters();

        for(Monster m: monsters){
            eb.addField(m.getName() + " (Level " + m.getLevel() + ")", m.toString(), false);
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
}
