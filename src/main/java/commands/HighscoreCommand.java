package commands;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import handlers.HighscoreHandler;
import handlers.MessageHandler;
import models.Player;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HighscoreCommand {
    public static void highscore(String[] msgArr, MessageChannel channel, MessageHandler messageHandler, HighscoreHandler highscoreHandler, User user, Guild guild, JDA jda){
        String highscoreType = "";
        ArrayList<Player> players;
        if(msgArr.length < 2){
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Highscores update daily. Available highscores: Level, Power, Speed, Strength, Total, Gold. e.g r!highscore total")).queue();
            return;
        } else{
            switch(msgArr[1]){
                case "level":
                case "levels":
                    highscoreType = "Level";
                    players = highscoreHandler.getLevelHighscore();
                    break;
                case "speed":
                    players = highscoreHandler.getSpeedHighscore();
                    highscoreType = "Speed";
                    break;
                case "power":
                    players = highscoreHandler.getPowerHighscore();
                    highscoreType = "Power";
                    break;
                case "strength":
                    players = highscoreHandler.getStrengthHighscore();
                    highscoreType = "Strength";
                    break;
                case "total":
                case "totals":
                    players = highscoreHandler.getTotalHighscore();
                    highscoreType = "Total Stats";
                    channel.sendMessage(messageHandler.createTotalHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
                    return;
                case "gold":
                    players = highscoreHandler.getGoldHighscore();
                    highscoreType = "Gold";
                    channel.sendMessage(messageHandler.createGoldHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
                    return;
                case "local":
                    if(guild == null){
                        String msg = "You can only get the local highscores in a Discord server.";
                        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, msg)).queue();
                        return;
                    }

                    if(msgArr.length >= 3){
                        if(msgArr[2].equals("level") || msgArr[2].equals("levels")){
                            players = HighscoreHandler.getGuildLevelsHighscores(guild.getId(), guild.getMembers());
                            highscoreType = "Local Level";
                        } else if(msgArr[2].equals("total") || msgArr[2].equals("totals")){
                           players = HighscoreHandler.getGuildTotalsHighscores(guild.getId(), guild.getMembers());
                           highscoreType = "Local Total Stats";
                        } else{
                            String msg = "Available local server highscores are only Level and Total stats. r!highscore local total";
                            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, msg)).queue();
                            return;
                        }
                    } else{
                        String msg = "Available local server highscores are only Level and Total stats. r!highscore local total";
                        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, msg)).queue();
                        return;
                    }
                    break;
                default:
                    channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Available highscores: Local, Level, Power, Speed, Strength, Total, Gold. e.g r!highscore total")).queue();
                    return;
            }
        }
        channel.sendMessage(messageHandler.createHighscoreEmbedMessage(user, players, jda, highscoreType)).queue();
    }
}
