package webhooks;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.*;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import config.ApplicationConstants;
import config.ItemConstants;
import database.PlayerDatabase;
import models.DonatorTransaction;
import models.Inventory;
import models.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Donator;

public class DonatorWebhook implements RequestStreamHandler {
    JSONParser parser = new JSONParser();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();

        try{
            JSONObject event = (JSONObject) parser.parse(reader);
            String auth = "";
            String bodyString = "";
            if (event.get("headers") != null) {
                JSONObject hps = (JSONObject)event.get("headers");
                System.out.println("hps: " + hps.toJSONString());
                auth = (String) hps.get("Authorization");

                if (event.get("body") != null) {
                    if (authIsValid(auth)) {
                        Gson gson = new Gson();
                        DonatorTransaction donator = gson.fromJson((String) event.get("body"), DonatorTransaction.class);
                        System.out.println(donator.toString());

                        PlayerDatabase playerDatabase = new PlayerDatabase();
                        if(donator.getBuyer_id() != null && !donator.getBuyer_id().equals("")){
                            if(donator.getStatus() != null){
                                Player player = playerDatabase.grabPlayer(donator.getBuyer_id());
                                System.out.println(player.toString());

                                String donatorStatus = donator.getStatus();
                                if(donatorStatus.equals("completed")){
                                    Donator.applyDonatorPacks(player, 1);
                                    player.addItem(ItemConstants.RESET_SCROLL.toString(), 2);
                                    System.out.println("Applied DP to " + player.getId());
                                } else if(donatorStatus.equals("reversed") || donatorStatus.equals("refunded")){
                                    Donator.removeDonatorPacks(player, 1);
                                    System.out.println("Removed DP from " + player.getId());
                                }
                                playerDatabase.insertPlayer(player);
                            }
                        }
                    else {
                        System.out.println("unauthorized: " + event.get("body"));
                    }
                } else{
                        System.out.println("wrong auth");
                    }
            }
            }
            System.out.println("auth: " + auth);
            System.out.println("payload: " + bodyString);
        } catch(ParseException parseException){
            System.out.println(parseException.getMessage());
        }
    }

    private boolean authIsValid(String auth){
        return auth != null && auth.equals(ApplicationConstants.DONATOR_WEBHOOK_AUTH);
    }
}
