package models;

import config.ApplicationConstants;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> inventory;

    public void Inventory(Player player){
        this.inventory = player.getInventory();
    }

    public boolean addItem(String item, int amount){
        int amountOwned =  getInventory().getOrDefault(item, 0);

        if(amount <= 0){
            return false;
        }

        inventory.put(item, amountOwned + amount);
        return true;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }
}
