package models;

public class Player extends Entity{
    private String id;

    private int gold;
    private double intelligence, woodcutting;
    private int levelExp, woodCuttingExp;
    private boolean alive;

    public Player(String id) {
        super(1, 50, 1, 1, 1);
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

    public void increSpeed(double amt){
        setSpeed(getSpeed() + amt);
    }

    public void increStrength(double amt){
        setStrength(getStrength() + amt);
    }

    public void increPower(double amt){
        setPower(getPower() + amt);
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
        return "Level: " + getLevel() + " Health: " + getHealth() + "\n"
                + "Power: " + getPower() + " Speed: " + getSpeed() + " Strength: " + getStrength()+ "\n" +
                "Gold: $" + getGold();
    }
}
