package models;

public class Player {
    private String id;

    private int level, money, health;
    private double speed, power, strength, intelligence, woodcutting;
    private int levelExp, woodCuttingExp;

    public Player(String id) {
        this.id = id;
        this.level = 1;
        this.speed = 1;
        this.power = 1;
        this.strength = 1;
        this.health = 200;
        this.woodcutting = 1;
        this.money = 0;
        this.levelExp = 0;
        this.woodCuttingExp = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMoney() {
        return money;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double defense) {
        this.power = defense;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void incSpeed(int amt){
        setSpeed(getSpeed() + amt);
    }

    public void incStrength(int amt){
        setStrength(getStrength() + amt);
    }

    public void incPower(int amt){
        setPower(getPower() + amt);
    }

    public void incHealth(int amt){
        setHealth(getHealth() + amt);
    }

    public double getTotalStats(){
        return getSpeed() + getPower() + getStrength();
    }

    @Override
    public String toString() {
        return "Level: " + getLevel() + " Health: " + getHealth() + "\n"
                + "Power: " + getPower() + " Speed: " + getSpeed() + " Strength: " + getStrength()+ "\n" +
                "Cash: $" + getMoney();
    }
}
