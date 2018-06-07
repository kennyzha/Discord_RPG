package models;

import java.util.ArrayList;

public class Monster extends Entity {
    private int expGiven;
    private ArrayList<Item> itemDropList;

    public Monster(int level, int health, double speed, double power, double strength, int expGiven, ArrayList<Item> itemDropList) {
        super(level, health, speed, power, strength);
        this.expGiven = expGiven;
        this.itemDropList = itemDropList;
    }

    public int getExpGiven() {
        return expGiven;
    }

    public void setExpGiven(int expGiven) {
        this.expGiven = expGiven;
    }

    public ArrayList<Item> getItemDropList() {
        return itemDropList;
    }

    public void setItemDropList(ArrayList<Item> itemDropList) {
        this.itemDropList = itemDropList;
    }
}
