package database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.gson.Gson;
import config.ApplicationConstants;
import models.Player;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.*;

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

    private Player selectPlayer(String id){
        Table playerTable = dynamoDB.getTable(dynamoClient.getPlayerTableName());
        Item item = playerTable.getItem(ApplicationConstants.PLAYER_PRIMARY_KEY, id);

        if(item == null)
            return null;

        Player player = gson.fromJson(item.toJSON(), Player.class);
        player.updateStamina();

        player.getInventory();
        return player;
    }

    public Player grabPlayer(String id){
        Player player = selectPlayer(id);
        if(player == null){
            Player newPlayer = new Player(id);

//            if(ApplicationConstants.TEST_SERVER){
//                newPlayer.setLevel(100);
//                newPlayer.setHealth(13335);
//                newPlayer.setGold(500000);
//                newPlayer.setPower(4000);
//                newPlayer.setSpeed(4000);
//                newPlayer.setStrength(4000);
//            }

            insertPlayer(newPlayer);
            player = newPlayer;

//            Stamina stamina = new Stamina(id);
//            insertPlayerStamina(stamina);
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

//    public Stamina retreivePlayerStamina(String id){
//        Table staminaTable = dynamoDB.getTable(dynamoClient.getStaminaTableName());
//        Item item = staminaTable.getItem(ApplicationConstants.STAMINA_PRIMARY_KEY, id);
//
//        if(item == null)
//            return null;
//
//        Stamina stamina = gson.fromJson(item.toJSON(), Stamina.class);
//
//        if(stamina == null)
//            return null;
//
//        stamina.updateStamina();
//
//        return stamina;
//    }

//    public void insertPlayerStamina(Stamina stamina){
//        Table staminaTable = dynamoDB.getTable(dynamoClient.getStaminaTableName());
//        staminaTable.putItem(Item.fromJSON(gson.toJson(stamina)));
//    }

    public List<Player> retreiveAllPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        AmazonDynamoDB amazonDynamoDB = dynamoClient.getAmazonDynamoDB();

        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#L", "level");
        Map<String, AttributeValue> expressionAttributeValues =
                new HashMap<>();
        expressionAttributeValues.put(":val", new AttributeValue().withN("50"));

        Map<String, AttributeValue> lastEvaluatedKey = null;

        do{
            ScanRequest scanRequest = new ScanRequest().withTableName(dynamoClient.getPlayerTableName())
                    .withExclusiveStartKey(lastEvaluatedKey)
                    .withFilterExpression("#L > :val")
                    .withExpressionAttributeNames(expressionAttributeNames)
                    .withExpressionAttributeValues(expressionAttributeValues);
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
                lastEvaluatedKey = scanResult.getLastEvaluatedKey();
            }
        } while(lastEvaluatedKey != null);


        return players;
    }
    
    public List<Player> retreivePlayers(List<Member> members){
        List<Player> players = new ArrayList<>();
        TableKeysAndAttributes playerKeyAndAttributes = new TableKeysAndAttributes(dynamoClient.getPlayerTableName());

        int count = 0;
        for(Member member : members){
            if(count == 100){
               addPlayerBatchToArray(playerKeyAndAttributes, players);
               playerKeyAndAttributes = new TableKeysAndAttributes(dynamoClient.getPlayerTableName());
                count = 0;
                continue;
            }
            count++;
            playerKeyAndAttributes.addHashOnlyPrimaryKey("id", member.getUser().getId());
        }

        return players;
    }

    public void addPlayerBatchToArray(TableKeysAndAttributes playerKeyAndAttributes, List<Player> players){
        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(playerKeyAndAttributes);

        for (String tableName : outcome.getTableItems().keySet()) {
            System.out.println("Items in table " + tableName);
            List<Item> items = outcome.getTableItems().get(tableName);
            for (Item item : items) {
                Player player = gson.fromJson(item.toJSON(), Player.class);
                players.add(player);
                System.out.println(item);
            }
        }
    }
}
