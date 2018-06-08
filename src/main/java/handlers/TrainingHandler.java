package handlers;

import database.PlayerDatabase;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.entities.MessageChannel;

public class TrainingHandler {
    private Player player;
    private Stamina stamina;
    private MessageChannel channel;

    private PlayerDatabase playerDatabase;

    public TrainingHandler(Player player, Stamina stamina, MessageChannel channel, PlayerDatabase playerDatabase){
        this.player = player;
        this.stamina = stamina;
        this.channel = channel;
        this.playerDatabase = playerDatabase;
    }

    public void trainSpeed(int numTimesToTrain){
        int curStamina = stamina.getStamina();
        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(stamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }
        player.increSpeed(numTimesToTrain);
        playerDatabase.insertPlayer(player);

        int newStamina = stamina.getStamina() - numTimesToTrain;
        updateStamina(newStamina);

        sendTrainMessage("speed", newStamina, numTimesToTrain);
    }

    public void trainPower(int numTimesToTrain){
        int curStamina = stamina.getStamina();
        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(stamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }
        player.increPower(numTimesToTrain);
        playerDatabase.insertPlayer(player);

        int newStamina = stamina.getStamina() - numTimesToTrain;
        updateStamina(newStamina);

        sendTrainMessage("power", newStamina, numTimesToTrain);
    }

    public void trainStrength(int numTimesToTrain){
        int curStamina = stamina.getStamina();
        if(curStamina == 0){
            sendNoStaminaMessage();
            return;
        } else if(!hasEnoughStamina(stamina, numTimesToTrain)) {
            numTimesToTrain = curStamina;
        }
        player.increStrength(numTimesToTrain);
        playerDatabase.insertPlayer(player);

        int newStamina = stamina.getStamina() - numTimesToTrain;
        updateStamina(newStamina);

        sendTrainMessage("strength", newStamina, numTimesToTrain);
    }

    public boolean hasEnoughStamina(Stamina stamina, int staminaUsage){
        return stamina.getStamina() >= staminaUsage;
    }

    public void updateStamina(int newStamina){
        Stamina curStamina = playerDatabase.retreivePlayerStamina(player.getId());

        curStamina.setStamina(newStamina);
        playerDatabase.insertPlayerStamina(curStamina);
    }

    public void sendTrainMessage(String statType, int stamina, int staminaUsed){
        channel.sendMessage("Successfully trained " + statType + ". You used " + staminaUsed + " stamina and now have " + stamina + " stamina left.").queue();
    }

    public void sendNoStaminaMessage(){
        channel.sendMessage("You have 0 stamina. You regain 1 stamina every 5 minutes.").queue();
    }
}
