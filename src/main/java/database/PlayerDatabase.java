package database;

import com.amazonaws.services.dynamodbv2.document.*;
import com.google.gson.Gson;
import models.Player;

public class PlayerDatabase {
    private DynamoClient dynamoClient;
    private DynamoDB dynamoDB;
    private Table playerTable;
    public static final String PLAYER_PRIMARY_KEY = "id";

    private Gson gson;

    public PlayerDatabase(){
        this.dynamoClient = new DynamoClient();
        this.dynamoDB = dynamoClient.getDynamoDb();
        this.playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());

        this.gson = new Gson();
    }

    public void insertPlayer(Player player){
        System.out.println("Attempting to insert: " + player.getId() + " " + gson.toJson(player));
        playerTable.putItem(Item.fromJSON(gson.toJson(player)));
        System.out.println("Player inserted fine");

    }

    public Player selectPlayer(String id){
        System.out.println("Attempting to find: " + id);
        Item item = playerTable.getItem(PLAYER_PRIMARY_KEY, id);

        if(item == null)
            return null;

        System.out.println(item.toJSON());
        return gson.fromJson(item.toJSON(), Player.class);
    }
}
