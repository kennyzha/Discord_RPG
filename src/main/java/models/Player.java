package models;

public class Player {
    private String id;

    private int level, attack, defense, strength, health, intelligence, woodcutting, money;
    private int levelExp, woodCuttingExp;

    public Player(String id) {
        this.id = id;
        this.level = 1;
        this.attack = 1;
        this.defense = 1;
        this.strength = 1;
        this.health = 100;
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(int woodcutting) {
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
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

    public void incAttack(int amt){
        setAttack(getAttack() + amt);
    }

    public void incStrength(int amt){
        setStrength(getStrength() + amt);
    }

    public void incDefense(int amt){
        setDefense(getDefense() + amt);
    }

    public void incHealth(int amt){
        setHealth(getHealth() + amt);
    }

    public int getTotalStats(){
        return getAttack() + getStrength() + getDefense();
    }
    @Override
    public String toString() {
        return "Level: " + getLevel() + " Health: " + getHealth() + " Intelligence: " + getIntelligence() + "\n"
                + "Attack: " + getAttack() + " Strength: " + getStrength() + " Defense: " + getDefense() + "\n" +
                "Cash: " + getMoney();
    }
}
