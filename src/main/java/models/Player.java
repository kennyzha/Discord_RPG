package models;

public class Player {
    private String id;

    private int level, attack, defense, strength, health, woodcutting, money;
    private int levelExp, attackExp, defenseExp, strengthExp, woodCuttingExp;

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
        this.attackExp = 0;
        this.defenseExp = 0;
        this.strengthExp = 0;
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

    public int getAttackExp() {
        return attackExp;
    }

    public void setAttackExp(int attackExp) {
        this.attackExp = attackExp;
    }

    public int getDefenseExp() {
        return defenseExp;
    }

    public void setDefenseExp(int defenseExp) {
        this.defenseExp = defenseExp;
    }

    public int getStrengthExp() {
        return strengthExp;
    }

    public void setStrengthExp(int strengthExp) {
        this.strengthExp = strengthExp;
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
}
