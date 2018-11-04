package webhooks;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.*;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import config.ApplicationConstants;
import database.PlayerDatabase;
import models.DonatorTransaction;
import models.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

                if (authIsValid(auth)) {
                    if (event.get("body") != null) {
                        JSONObject body = (JSONObject)parser.parse((String)event.get("body"));

                        Gson gson = new Gson();
                        DonatorTransaction donator = gson.fromJson((String) event.get("body"), DonatorTransaction.class);
                        System.out.println(donator.toString());

                        PlayerDatabase playerDatabase = new PlayerDatabase();
                        if(donator.getBuyer_id() != null && !donator.getBuyer_id().equals("")){
                            Player player = playerDatabase.grabPlayer(donator.getBuyer_id());

                            System.out.println(donator.toString());

                            if(donator.getStatus() != null){
                                String donatorStatus = donator.getStatus();
                                if(donatorStatus.equals("completed")){
                                    System.out.println(donatorStatus);
                                } else if(donatorStatus.equals("reversed") || donatorStatus.equals("refunded")){
                                    System.out.println(donatorStatus);
                                }
                            }
                        }

                    }
                } else {
                    System.out.println("unauthorized");
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
