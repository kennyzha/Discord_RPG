package database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.gson.Gson;
import config.ApplicationConstants;
import models.Player;
import models.Stamina;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        Table playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());
        playerTable.putItem(Item.fromJSON(gson.toJson(player)));
    }

    public Player selectPlayer(String id){
        Table playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());
        Item item = playerTable.getItem(ApplicationConstants.PLAYER_PRIMARY_KEY, id);

        if(item == null)
            return null;

        Player player = gson.fromJson(item.toJSON(), Player.class);
        player.updateStamina();

        return player;
    }

    public Player grabPlayer(String id){
        Player player = selectPlayer(id);
        if(player == null){
            Player newPlayer = new Player(id);
            insertPlayer(newPlayer);
            player = newPlayer;

            Stamina stamina = new Stamina(id);
            insertPlayerStamina(stamina);
        }

        return player;
    }

    public Player grabMentionedPlayer(Message message, MessageChannel channel, String command){
        List<User> mentionedList = message.getMentionedUsers();
        if(mentionedList.size() != 1){
            channel.sendMessage(String.format("%s, please mention the name of the user you wish to interact with !%s @name", message.getAuthor().getName(), command )).queue();
            return null;
        }

        User userMentioned = mentionedList.get(0);
        Player player = selectPlayer(userMentioned.getId());

        if(player == null){
            channel.sendMessage(message.getAuthor().getName() + ", the mentioned user does not play this awesome game. You should get that person to play.").queue();
            return null;
        }
        return player;
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
        Table staminaTable = dynamoDB.getTable(dynamoClient.getStaminaTableName());
        staminaTable.putItem(Item.fromJSON(gson.toJson(stamina)));
    }

    public List<Player> retreivePlayers(){
        ArrayList<Player> players = new ArrayList<>();
        AmazonDynamoDB amazonDynamoDB = dynamoClient.getAmazonDynamoDB();
        ScanRequest scanRequest = new ScanRequest().withTableName(dynamoClient.getPlayerTableName());

        ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

        for (Map<String, AttributeValue> item : scanResult.getItems()){
            try {
                String id = item.get("id").getS();
                String level = item.get("level").getN();
                String speed = item.get("speed").getN();
                String power = item.get("power").getN();
                String strength = item.get("strength").getN();
                String gold = item.get("gold").getN();

                Player player = new Player(id);
                player.setLevel(Integer.parseInt(level));
                player.setSpeed(Double.parseDouble(speed));
                player.setPower(Double.parseDouble(power));
                player.setStrength(Double.parseDouble(strength));
                player.setGold(Integer.parseInt(gold));
                players.add(player);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return players;
    }
}
