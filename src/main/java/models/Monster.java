package models;

import java.util.ArrayList;

public class Monster extends Entity {
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
}
