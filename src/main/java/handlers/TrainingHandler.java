package handlers;

import database.PlayerDatabase;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class TrainingHandler {
    private Player player;
    private User user;
    private MessageChannel channel;

    private PlayerDatabase playerDatabase;

    public TrainingHandler(Player player, User user, MessageChannel channel, PlayerDatabase playerDatabase){
        this.player = player;
        this.user = user;
        this.channel = channel;
        this.playerDatabase = playerDatabase;
    }

    public void trainSpeed(int numTimesToTrain){
        int curStamina = player.getStamina();
        double oldSpeed = player.getSpeed();

        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(curStamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }

        player.increSpeed(numTimesToTrain);
        int newStamina = curStamina - numTimesToTrain;
        player.setStamina(newStamina);
        playerDatabase.insertPlayer(player);

        double spdGained = player.getSpeed() - oldSpeed;
        sendTrainMessage(player.round(spdGained),"speed", newStamina, numTimesToTrain);
    }

    public void trainPower(int numTimesToTrain){
        int curStamina = player.getStamina();
        double oldPow = player.getPower();

        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(curStamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }
        player.increPower(numTimesToTrain);
        int newStamina = player.getStamina() - numTimesToTrain;
        player.setStamina(newStamina);
        playerDatabase.insertPlayer(player);

        double powGained = player.getPower() - oldPow;
        sendTrainMessage(player.round(powGained),"power", newStamina, numTimesToTrain);
    }

    public void trainStrength(int numTimesToTrain){
        int curStamina = player.getStamina();

        double oldStr = player.getStrength();

        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(curStamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }
        player.increStrength(numTimesToTrain);
        int newStamina = curStamina - numTimesToTrain;
        player.setStamina(newStamina);

        playerDatabase.insertPlayer(player);

        double strGained = player.getStrength() - oldStr;
        sendTrainMessage(player.round(strGained), "strength", newStamina, numTimesToTrain);
    }

    public boolean hasEnoughStamina(int stamina, int staminaUsage){
        return stamina >= staminaUsage;
    }

//    public void updateStamina(int newStamina){
//        Stamina curStamina = playerDatabase.retreivePlayerStamina(player.getId());
//
//        curStamina.setStamina(newStamina);
//        playerDatabase.insertPlayerStamina(curStamina);
//    }

    public void sendTrainMessage(double statGained, String statType,  int staminaUsed, int staminaLeft){
        MessageHandler messageHandler = new MessageHandler();
        channel.sendMessage(messageHandler.createEmbedTrainMessage(user, statGained, statType, staminaLeft, staminaUsed)).queue();
    }

    public void sendNoStaminaMessage(){
        MessageHandler messageHandler = new MessageHandler();
        channel.sendMessage(messageHandler.createDefaultEmbedMessage(user, "You have 0 stamina. You regain 1 stamina every 5 minutes.")).queue();
    }
}
