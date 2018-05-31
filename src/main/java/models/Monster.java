package models;

import java.util.ArrayList;

public class Monster extends Entity {
    private int minExpGiven, maxExpGiven;
    private ArrayList<Item> itemDropList;

    public Monster(int level, int health, double speed, double power, double strength, int minExpGiven, int maxExpGiven, ArrayList<Item> itemDropList) {
        super(level, health, speed, power, strength);
        this.minExpGiven = minExpGiven;
        this.maxExpGiven = maxExpGiven;
        this.itemDropList = itemDropList;
    }

    public int getMinExpGiven() {
        return minExpGiven;
    }

    public void setMinExpGiven(int minExpGiven) {
        this.minExpGiven = minExpGiven;
    }

    public int getMaxExpGiven() {
        return maxExpGiven;
    }

    public void setMaxExpGiven(int maxExpGiven) {
        this.maxExpGiven = maxExpGiven;
    }

    public ArrayList<Item> getItemDropList() {
        return itemDropList;
    }

    public void setItemDropList(ArrayList<Item> itemDropList) {
        this.itemDropList = itemDropList;
    }
}
