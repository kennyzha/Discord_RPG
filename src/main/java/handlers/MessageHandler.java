package handlers;

import models.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class MessageHandler {

    public MessageEmbed createProfileEmbed(User user, Player player){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(user.getName(), null, user.getEffectiveAvatarUrl());
        eb.setColor(Color.CYAN);
        eb.setTitle("Level: " + player.getLevel() + " (" + player.getLevelExp() + "/" + player.calcExpToNextLevel() + ")");
        eb.addField("Health", Integer.toString(player.getHealth()), true);
        eb.addBlankField(true);
        eb.addField("Gold", Integer.toString(player.getGold()), true);
        eb.addField("Power", Double.toString(player.getPower()), true);
        eb.addField("Speed", Double.toString(player.getSpeed()), true);
        eb.addField("Strength", Double.toString(player.getStrength()), true);
        return eb.build();
    }
}
