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
import java.util.ArrayList;

public class MessageHandler {

    public MessageEmbed createProfileEmbed(User user, Player player, Stamina stamina){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb, user);

        eb.setTitle("Level: " + player.getLevel() + " (" + player.getLevelExp() + "/" + player.calcExpToNextLevel() + ")");
        eb.addField("Health", Integer.toString(player.getHealth()), true);
        eb.addField("Stamina", stamina.getStamina() + "/20", true);
        eb.addField("Gold", Integer.toString(player.getGold()), true);
        eb.addField("Power", Double.toString(player.getPower()), true);
        eb.addField("Speed", Double.toString(player.getSpeed()), true);
        eb.addField("Strength", Double.toString(player.getStrength()), true);

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

    public MessageEmbed createHighscoreEmbedMessage(User user, ArrayList<Player> players, JDA jda){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);

        for(Player p: players){
            User curUser = jda.getUserById(p.getId());
            if(curUser == null){
                System.out.println(p.getId() + " is null");
                continue;
            }
            eb.addField(curUser.getName() + "#" + curUser.getDiscriminator() + " (Level: " + p.getLevel() + ")", " Power: " +  p.getPower() + " Speed: " + p.getSpeed() + " Strength: " + p.getStrength(), false);
        }

        return eb.build();
    }
}
