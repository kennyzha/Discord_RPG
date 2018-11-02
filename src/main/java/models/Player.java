package models;

import config.ApplicationConstants;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;

public class Player extends Entity{
    private String id;

    private int gold;
    private double intelligence, woodcutting;
    private int levelExp, woodCuttingExp;
    private boolean alive;

    private long donatorEndTime;

    private int stamina;
    private long staminaLastUpdateTime;

    private String forageDate;
    private int forageAmount;

    private HashMap<String, Integer> inventory;

    public Player(String id) {
        super(1, 200, 1, 1, 1);
        this.id = id;
        this.woodcutting = 1;
        this.gold = 0;
        this.levelExp = 0;
        this.woodCuttingExp = 0;
        this.alive = true;
        this.forageDate = LocalDate.now().toString();
        this.forageAmount = 0;
        this.stamina = ApplicationConstants.MAX_STAMINA;
        this.staminaLastUpdateTime = System.currentTimeMillis();
        this.inventory = new HashMap<>();
        this.donatorEndTime = System.currentTimeMillis();
    }

    public long getDonatorEndTime() {
        return donatorEndTime;
    }

    public void setDonatorEndTime(long donatorEndTime) {
        this.donatorEndTime = donatorEndTime;
    }

    public void addDonatorTime(long milliseconds){
        if(isDonator()){
            setDonatorEndTime(getDonatorEndTime() + milliseconds);
        } else {
            setDonatorEndTime(System.currentTimeMillis() + milliseconds);
        }
    }

    public boolean isDonator(){
        return (getDonatorEndTime() - System.currentTimeMillis()) > 0;
    }

    public HashMap<String, Integer> getInventory() {
        if(inventory == null){
            this.inventory = new HashMap<>();
        }
        return this.inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        if(inventory == null){
            this.inventory = new HashMap<>();
        } else {
            this.inventory = inventory;
        }
    }

    public boolean addItem(String item, int amount){
        int amountOwned =  inventory.getOrDefault(item.toLowerCase(), 0);

        if(amount <= 0){
            return false;
        }

        inventory.put(item, amountOwned + amount);
        return true;
    }

    public boolean consumeItems(String item, int amount){
        item = item.toLowerCase();
        if(!containsItemQuantity(item, amount)){
            return false;
        }

        int amountLeft = getInventory().get(item) - amount;

        if(amountLeft == 0){
            getInventory().remove(item);
        } else{
            getInventory().put(item, getInventory().get(item) - amount);
        }
        return true;
    }

    public boolean containsItemQuantity(String item, int amount){
        return getInventory().getOrDefault(item.toLowerCase(), 0) >= amount;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public long getStaminaLastUpdateTime() {
        return staminaLastUpdateTime;
    }

    public void setStaminaLastUpdateTime(long staminaLastUpdateTime) {
        this.staminaLastUpdateTime = staminaLastUpdateTime;
    }

    public void updateStamina(){
        Long updatedTime = System.currentTimeMillis();

        if(this.stamina < ApplicationConstants.MAX_STAMINA){
            Long elapsedTime = updatedTime - this.staminaLastUpdateTime;
            int staminaRefreshRate = (isDonator()) ? ApplicationConstants.DONATOR_STAMINA_REFRESH_RATE : ApplicationConstants.STAMINA_REFRESH_RATE;
            Long leftOverTime = elapsedTime % staminaRefreshRate;
            updatedTime -= leftOverTime;

            int staminaGained = (int) (elapsedTime / ApplicationConstants.STAMINA_REFRESH_RATE);

            setStamina(Math.min(this.stamina + staminaGained, ApplicationConstants.MAX_STAMINA));
        }

        setStaminaLastUpdateTime(updatedTime);

    }

    public String getForageDate() {
        if(this.forageDate == null){
            this.forageDate = "";
        }
        return forageDate;
    }

    public void setForageDate(String forageDate) {
        this.forageDate = forageDate;
    }

    public int getForageAmount() {
        String date = LocalDate.now().toString();

        if(!getForageDate().equals(date)){
            forageAmount = 0;
            forageDate = date;
        }

        return forageAmount;
    }

    public void setForageAmount(int forageAmount) {
        this.forageAmount = forageAmount;
    }
    public int getGold() {
        return gold;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(double woodcutting) {
        this.woodcutting = woodcutting;
    }

    public int getLevelExp() {
        return levelExp;
    }

    public void setLevelExp(int levelExp) {
        this.levelExp = levelExp;
    }

    public int getWoodCuttingExp() {
        return woodCuttingExp;
    }

    public void setWoodCuttingExp(int woodCuttingExp) {
        this.woodCuttingExp = woodCuttingExp;
    }

    public void setGold(int gold) {

        if(gold < 0){
            return;
        }

        this.gold = gold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double calcStatGain(){
        int curLevel = getLevel();
        double baseGain = .5;
        double multiplier;

        if(curLevel <= 200){
            multiplier = .0025;
        } else if(curLevel <= 600){
            baseGain = 1;
            multiplier = .005;
            curLevel -= 200;
        } else {
            baseGain = 3;
            multiplier = .01;
            curLevel -= 600;
        }

        double levelStatGain = curLevel * multiplier;

        return baseGain + levelStatGain;
    }
    public void increSpeed(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getSpeed() + totalStatGain) * 1000.0) / 1000.0;
        setSpeed(rounded);
    }

    public void increStrength(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getStrength() + totalStatGain) * 1000.0) / 1000.0;

        setStrength(rounded);
    }

    public void increPower(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getPower() + totalStatGain) * 1000.0) / 1000.0;

        setPower(rounded);
    }

    public void increHealth(int amt){
        setHealth(getHealth() + amt);
    }

    public void increExp(int amt){
        setLevelExp(getLevelExp() + amt);
    }

    public void increGold(int amt){
        setGold(getGold() + amt);
    }

    public boolean leveledUp(){
        return getLevelExp() >= calcExpToNextLevel();
    }

    public void updateLevelAndExp(){
        while(leveledUp()){
            int leftOverExp = getLevelExp() - calcExpToNextLevel();
            setLevel(getLevel() + 1);
            setLevelExp(leftOverExp);
            int hpGained = calcActualHealthGained(calcBaseHealthGained());
            increHealth(hpGained);
        }
    }

    public double getTotalStats(){
        return getSpeed() + getPower() + getStrength();
    }

    public String getSpeedPercentage(){
        DecimalFormat format = new DecimalFormat("#,###.##");

        return format.format(getSpeed() / getTotalStats() * 100);
    }

    public String getPowerPercentage(){
        return new DecimalFormat("#,###.##").format(getPower() / getTotalStats() * 100);
    }

    public String getStrengthPercentage(){
        return new DecimalFormat("#,###.##").format(getStrength() / getTotalStats() * 100);
    }

    public int calcBaseHealthGained(){
        double multiplier;

        if(getLevel() <= 100){
            multiplier = 2.5;
        } else if(getLevel() <= 200){
            multiplier = 2.25;
        } else {
            multiplier = 2;
        }

        return (int) (multiplier * getLevel());
    }

    public int calcActualHealthGained(int baseHealth){
        return baseHealth + calcVariance(baseHealth, .1);
    }

    public int calcExpToNextLevel(){
        int nextLvl = getLevel() + 1;
        return (int) (50 * (Math.pow(nextLvl, 2) - (5 * nextLvl) + 8));
    }

    public double round(double num){
        return (double) (Math.round(num * 1000d) / 1000d);
    }

    public void applyLegendaryEffect(){
        this.setSpeed((this.getSpeed() * .05) + this.getSpeed() + 33.333);
        this.setPower((this.getPower() * .05) + this.getPower() + 33.333);
        this.setStrength((this.getStrength() * .05) + this.getStrength() + 33.333);
    }

    public int calcDonatorBonusGold(int gold){
        return (int) (gold * .25);

    }

    @Override
    public String toString() {
        return "Level: " + getLevel() + " Experience: " + getLevelExp() + "/" + calcExpToNextLevel() + " Health: " + getHealth() + "\n"
                + "Power: " + getPower() + " Speed: " + getSpeed() + " Strength: " + getStrength()+ "\n" +
                "Gold: " + getGold();
    }
}
