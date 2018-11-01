package commands;

import database.PlayerDatabase;
import handlers.MessageHandler;
import handlers.TrainingHandler;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class Train {
    private static final String ERROR_MESSAGE = "Please include a number greater than 0 and the type of stat you would like to train. e.g. r!train power 10";

    public static void trainCommand(String[] msgArr, MessageChannel channel, PlayerDatabase playerDatabase, User user, MessageHandler messageHandler){
        if(msgArr.length < 3){
            channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ERROR_MESSAGE)).queue();
        }else{
            String statToTrain = msgArr[1];
            try{
                int numTimesToTrain = Integer.parseInt(msgArr[2]);

                if(numTimesToTrain < 1){
                    channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ERROR_MESSAGE)).queue();
                } else{
                    Player player = playerDatabase.grabPlayer(user.getId());
                    TrainingHandler trainingHandler = new TrainingHandler(player, user, channel, playerDatabase);

                    trainStat(trainingHandler, statToTrain, numTimesToTrain, channel, messageHandler, user);
                }
            } catch(Exception e){
                channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, ERROR_MESSAGE)).queue();
            }
        }
    }

    private static void trainStat(TrainingHandler trainingHandler, String statToTrain, int numTimesToTrain, MessageChannel channel, MessageHandler messageHandler, User user){
        switch (statToTrain) {
            case "speed":
            case "spd":
                trainingHandler.trainSpeed(numTimesToTrain);
                break;
            case "power":
            case "pwr":
            case "pow":
                trainingHandler.trainPower(numTimesToTrain);
                break;
            case "strength":
            case "str":
                trainingHandler.trainStrength(numTimesToTrain);
                break;
            default:
                channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "Invalid argument. Failed to train:" + statToTrain + ". You can only train power, speed and strength.")).queue();
                break;
        }
    }
}
