package models;

public class Player extends Entity{
    private String id;

    private int gold;
    private double intelligence, woodcutting;
    private int levelExp, woodCuttingExp;
    private boolean alive;

    public enum Stat {POWER, SPEED, STRENGTH};
    public Player(String id) {
        super(1, 200, 1, 1, 1);
        this.id = id;
        this.woodcutting = 1;
        this.gold = 0;
        this.levelExp = 0;
        this.woodCuttingExp = 0;
        this.alive = true;
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
        double baseGain = .5;
        double additionalGain = getLevel() * .0025;

        return baseGain + additionalGain;
    }
    public void increSpeed(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getSpeed() + totalStatGain) * 100.0) / 100.0;
        setSpeed(rounded);
    }

    public void increStrength(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getStrength() + totalStatGain) * 100.0) / 100.0;

        setStrength(rounded);
    }

    public void increPower(double amt){
        double totalStatGain = amt * calcStatGain();
        double rounded = Math.round((getPower() + totalStatGain) * 100.0) / 100.0;
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
        return getLevelExp() > calcExpToNextLevel();
    }

    public void updateLevelAndExp(){
        while(leveledUp()){
            int leftOverExp = getLevelExp() - calcExpToNextLevel();
            setLevel(getLevel() + 1);
            setLevelExp(leftOverExp);
            int hpGained = calcActualHealthGained(calcBaseHealthGained());
            increHealth(hpGained);
            System.out.println("Leveled up to " + getLevel() + " and hp gained: " + hpGained);
        }
    }

    public double getTotalStats(){
        return getSpeed() + getPower() + getStrength();
    }

    public int calcBaseHealthGained(){
        double multiplier = 1;
        if(getLevel() <= 100){
            multiplier = 2.5;
        } else if(getLevel() <= 200){
            multiplier = 2.25;
        } else if(getLevel() <= 300){
            multiplier = 2;
        } else{
            return 600;
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

    @Override
    public String toString() {
        return "Level: " + getLevel() + " Experience: " + getLevelExp() + "/" + calcExpToNextLevel() + " Health: " + getHealth() + "\n"
                + "Power: " + getPower() + " Speed: " + getSpeed() + " Strength: " + getStrength()+ "\n" +
                "Gold: " + getGold();
    }
}
