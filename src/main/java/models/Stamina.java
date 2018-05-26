package models;

import config.ApplicationConstants;

public class Stamina {
    private String playerId;
    private int stamina;
    private long timeSinceUpdated;

    public Stamina(String playerId){
        this.playerId = playerId;
        this.stamina = 100;
        timeSinceUpdated = System.currentTimeMillis();
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

    public long getTimeSinceUpdated() {
        return timeSinceUpdated;
    }

    public void setTimeSinceUpdated(long timeSinceUpdated) {
        this.timeSinceUpdated = timeSinceUpdated;
    }

    public void updateStamina(){
        long curTime = System.currentTimeMillis();
        System.out.println("cur time " + curTime);
        System.out.println("time since updated " + timeSinceUpdated);
        long timeElapsed = curTime - timeSinceUpdated;
        System.out.println("time elapsed " + timeElapsed);
        int baseStaminaGained = (int) (timeElapsed / ApplicationConstants.STAMINA_REFRESH_RATE);
        System.out.println("based stamina gained " + baseStaminaGained);

        int staminaGained = baseStaminaGained * ApplicationConstants.STAMINA_GAINED_PER_REFRESH;

        System.out.println("stamina gained" + staminaGained);

        if(staminaGained > 0){
            Long leftOverTime = timeElapsed % ApplicationConstants.STAMINA_REFRESH_RATE;
            setTimeSinceUpdated(curTime - leftOverTime);
        }
        setStamina(Math.min(ApplicationConstants.MAX_STAMINA, stamina + staminaGained));
    }
}