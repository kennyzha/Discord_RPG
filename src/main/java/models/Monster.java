package models;

import java.util.ArrayList;

public class Monster extends Entity {
    private int minExpGiven, maxExpGiven;
    private ArrayList<Integer> itemDropList;

    public Monster(int level, int health, double speed, double power, double strength, int minExpGiven, int maxExpGiven, ArrayList<Integer> itemDropList) {
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

    public ArrayList<Integer> getItemDropList() {
        return itemDropList;
    }

    public void setItemDropList(ArrayList<Integer> itemDropList) {
        this.itemDropList = itemDropList;
    }
}
