package database;

import com.amazonaws.services.dynamodbv2.document.*;
import com.google.gson.Gson;
import config.ApplicationConstants;
import models.Player;
import models.Stamina;

public class PlayerDatabase {
    private DynamoClient dynamoClient;
    private DynamoDB dynamoDB;

    private Gson gson;

    public PlayerDatabase(){
        this.dynamoClient = new DynamoClient();
        this.dynamoDB = dynamoClient.getDynamoDb();

        this.gson = new Gson();
    }

    public void insertPlayer(Player player){
        System.out.println("Attempting to insert: " + player.getId() + " " + gson.toJson(player));

        Table playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());
        playerTable.putItem(Item.fromJSON(gson.toJson(player)));

        System.out.println("Player inserted fine");

    }

    public Player selectPlayer(String id){
        System.out.println("Attempting to find: " + id);

        Table playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());
        Item item = playerTable.getItem(ApplicationConstants.PLAYER_PRIMARY_KEY, id);

        if(item == null)
            return null;

        System.out.println(item.toJSON());
        return gson.fromJson(item.toJSON(), Player.class);
    }

    public Stamina retreivePlayerStamina(String id){
        Table staminaTable = dynamoDB.getTable(dynamoClient.getStaminaTableName());
        Item item = staminaTable.getItem(ApplicationConstants.STAMINA_PRIMARY_KEY, id);

        if(item == null)
            return null;

        Stamina stamina = gson.fromJson(item.toJSON(), Stamina.class);

        if(stamina == null)
            return null;

        stamina.updateStamina();

        return stamina;
    }

    public void insertPlayerStamina(Stamina stamina){
        System.out.println(gson.toJson(stamina));
        Table staminaTable = dynamoDB.getTable(dynamoClient.getStaminaTableName());
        staminaTable.putItem(Item.fromJSON(gson.toJson(stamina)));
    }
}
