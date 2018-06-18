package handlers;

import models.CombatResult;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

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
        eb.setThumbnail("http://www.thegoodsurvivalist.com/wp-content/myimages/2014/05/dirty-fighting.png");
        return eb.build();
    }


    public MessageEmbed createEmbedTrainMessage(User user, double statGained, String statType, int staminaUsed, int staminaLeft){
        EmbedBuilder eb = new EmbedBuilder();
        setEmbedMessageDefaults(eb , user);
        //"Successfully trained " + statType + ". You used " + staminaUsed + " stamina and now have " + stamina + " stamina left."
        eb.setDescription(String.format("You gained " + statGained + " %s. You used %s stamina and now have %s stamina left.", statType, staminaUsed, staminaLeft));

        if(statType.equals("power"))
            eb.setThumbnail("https://www.shareicon.net/data/512x512/2015/12/06/683488_man_512x512.png");
        else if(statType.equals("speed")){
            eb.setThumbnail("https://t4.rbxcdn.com/cee4c230345374225df92e9946566fe9");
        } else if(statType.equals("strength")){
            eb.setThumbnail("https://cdn0.iconfinder.com/data/icons/fighting-1/225/brawl005-512.png");
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
}
