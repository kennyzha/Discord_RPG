package commands;

import database.PlayerDatabase;
import handlers.MessageHandler;
import handlers.TrainingHandler;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class Train {
    public static void trainCommand(String[] msgArr, MessageChannel channel, PlayerDatabase playerDatabase, User user, MessageHandler messageHandler){
        if(msgArr.length < 3){
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a number between 1 and 60 and the type of stat you would like to train. e.g. r!train power 10")).queue();
        }else{
            String statToTrain = msgArr[1];
            try{
                int numTimesToTrain = Integer.parseInt(msgArr[2]);

                if(numTimesToTrain < 1 || numTimesToTrain > 60){
                    channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a number between 1 and 60 and the type of stat you would like to train. e.g. r!train power 10")).queue();
                } else{
                    Player player = playerDatabase.grabPlayer(user.getId());

                    TrainingHandler trainingHandler = new TrainingHandler(player, user, channel, playerDatabase);

                    if(statToTrain.equals("speed") || statToTrain.equals("spd")){
                        trainingHandler.trainSpeed(numTimesToTrain);
                    }else if(statToTrain.equals("power")  || statToTrain.equals("pwr") || statToTrain.equals("pow")){
                        trainingHandler.trainPower(numTimesToTrain);
                    }else if(statToTrain.equals("strength") || statToTrain.equals("str")){
                        trainingHandler.trainStrength(numTimesToTrain);
                    } else{
                        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Invalid argument. Failed to train:" + statToTrain + ". You can only train power, speed and strength.")).queue();
                    }
                }
            } catch(Exception e){
                channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Please include a valid number between 1 and 60. e.g. r!train speed 10")).queue();
            }
        }
    }
}
