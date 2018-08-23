package models;

import config.MonsterConstants;

import java.util.ArrayList;

public class Monster extends Entity {

    public enum Build {SPEED, STRENGTH};
    private String name;
    private int baseExpGiven, basedGoldDropped;
    private ArrayList<Item> itemDropList;

    public Monster(String name, int level, int health, double speed, double power, double strength, int baseExpGiven, int basedGoldDropped, ArrayList<Item> itemDropList) {
        super(level, health, speed, power, strength);
        this.name = name;
        this.baseExpGiven = baseExpGiven;
        this.basedGoldDropped = basedGoldDropped;
        this.itemDropList = itemDropList;
    }

    public Monster(String name, int level, int health, int totalStats, int baseExpGiven, int basedGoldDropped, Build build){
        this.name = name;
        this.baseExpGiven = baseExpGiven;
        this.basedGoldDropped = basedGoldDropped;
        this.itemDropList = new ArrayList<>();

        this.setLevel(level);
        this.setHealth(health);

        if(build == Build.SPEED){
            setSpeedBuild(totalStats);
        } else {
            setStrengthBuild(totalStats);
        }
    }

    private void setSpeedBuild(int totalStats) {
        setPower(totalStats * .3);
        setSpeed(totalStats * .5);
        setStrength(totalStats * .2);
    }

    private void setStrengthBuild(int totalStats) {
        setPower(totalStats * .2);
        setSpeed(totalStats * .3);
        setStrength(totalStats * .5);
    }

    public String getName() {
        return name;
    }

    public int getBaseExpGiven() {
        return baseExpGiven;
    }

    public int getBasedGoldDropped() {
        return basedGoldDropped;
    }

    public void setBaseExpGiven(int baseExpGiven) {
        this.baseExpGiven = baseExpGiven;
    }

    public int calcExpGiven(){
        return baseExpGiven + (calcVariance(baseExpGiven, .1) * posOrNegOne());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasedGoldDropped(int basedGoldDropped) {
        this.basedGoldDropped = basedGoldDropped;
    }

    public int calcGoldDropped(){
        return basedGoldDropped + (calcVariance(basedGoldDropped, .1) * posOrNegOne());
    }

    public ArrayList<Item> getItemDropList() {
        return itemDropList;
    }

    public void setItemDropList(ArrayList<Item> itemDropList) {
        this.itemDropList = itemDropList;
    }

    public static Monster identifyMonster(String monsterString){
        switch(monsterString.toLowerCase()) {
            case "slime":
                return MonsterConstants.SLIME;
            case "spider":
                return MonsterConstants.SPIDER;
            case "goblin":
                return MonsterConstants.GOBLIN;
            case "kobold":
                return MonsterConstants.KOBOLD;
            case "rock":
                return MonsterConstants.ROCK;
            case "troll":
                return MonsterConstants.TROLL;
            case "ghoul":
                return MonsterConstants.GHOUL;
            case "giant":
                return MonsterConstants.GIANT;
            case "orc":
                return MonsterConstants.ORC;
            case "ogre":
                return MonsterConstants.OGRE;
            case "ent":
                return MonsterConstants.ENT;
            case "ghost":
                return MonsterConstants.GHOST;
            case "gnome":
                return MonsterConstants.GNOME;
            case "nymph":
                return MonsterConstants.NYMPH;
            case "dwarf":
                return MonsterConstants.DWARF;
            case "pixie":
                return MonsterConstants.PIXIE;
            case "zombie":
                return MonsterConstants.ZOMBIE;
            case "harpy":
                return MonsterConstants.HARPY;
            case "mummy":
                return MonsterConstants.MUMMY;
            case "fairy":
                return MonsterConstants.FAIRY;
            case "minotaur":
                return MonsterConstants.MINOTAUR;
            case "nix":
                return MonsterConstants.NIX;
            case "bunyip":
                return MonsterConstants.BUNYIP;
            case "elf":
                return MonsterConstants.ELF;
            case "yeti":
                return MonsterConstants.YETI;
            case "mermaid":
                return MonsterConstants.MERMAID;
            case "wyvern":
                return MonsterConstants.WYVERN;
        }
        return null;
    }

    @Override
    public String toString(){
        return String.format("Health: %s Power: %s Speed: %s Strength: %s", getHealth(), getPower(), getSpeed(), getStrength());
    }
}
