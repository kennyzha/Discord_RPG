package models;

import config.ApplicationConstants;

public class Stamina {
    private String playerId;
    private int stamina;
    private long timeSinceUsed;

    public Stamina(String playerId){
        this.playerId = playerId;
        this.stamina = 100;
        timeSinceUsed = System.currentTimeMillis();
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public long getTimeSinceUsed() {
        return timeSinceUsed;
    }

    public void setTimeSinceUsed(long timeSinceUsed) {
        this.timeSinceUsed = timeSinceUsed;
    }

    public int updateStamina(){
        long curTime = System.currentTimeMillis();
        long timeElapsed = curTime - timeSinceUsed;
        int baseStaminaGained = (int) (timeElapsed % ApplicationConstants.STAMINA_REFRESH_RATE);
        int staminaGained = baseStaminaGained * ApplicationConstants.STAMINA_GAINED_PER_REFRESH;

        timeSinceUsed = curTime;
        return Math.min(ApplicationConstants.MAX_STAMINA, stamina + staminaGained);

    }
}
