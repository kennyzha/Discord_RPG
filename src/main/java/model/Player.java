package model;

public class Player {
    private int level, attack, defense, strength, woodcutting;
    private int levelExp, attackExp, defenseExp, strengthExp, woodCuttingExp;

    public Player() {
        this.level = 1;
        this.attack = 1;
        this.defense = 1;
        this.strength = 1;
        this.woodcutting = 1;
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
}
