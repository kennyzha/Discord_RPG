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

        if(amount <= 0 || amountOwned + amount > ApplicationConstants.INVENTORY_LIMIT){
            return false;
        }

        inventory.put(item, amountOwned + amount);
        return true;
    }

    public void addItemConsumeExcess(String itemName, int amount, StringBuilder sb, Channel channel, User user){
        int itemAmtOwned = getInventory().get(itemName);
        int newItemTotal =  amount + itemAmtOwned;
        int itemExcess = newItemTotal - ApplicationConstants.INVENTORY_LIMIT;

        addItem(itemName, ApplicationConstants.INVENTORY_LIMIT - itemAmtOwned);

//        consume(channel, "r!consume speed potion " + itemExcess, user);

        if (amount == 1) {
            sb.append(String.format("You decided to drink %s %s because you can only hold a max of 100 of each item.\n", itemExcess, itemName));
        } else {
            sb.append(String.format("You decided to drink %s %ss because you can only hold a max of 100 of each item.\n", itemExcess, itemName));
        }
    }
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }
}
