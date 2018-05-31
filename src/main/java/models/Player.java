package models;

public class Player extends Entity{
    private String id;

    private int money;
    private double intelligence, woodcutting;
    private int levelExp, woodCuttingExp;

    public Player(String id) {
        super(1, 200, 1, 1, 1);
        this.id = id;
        this.woodcutting = 1;
        this.money = 0;
        this.levelExp = 0;
        this.woodCuttingExp = 0;
    }

    public int getMoney() {
        return money;
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
                "Gold: $" + getMoney();
    }
}
