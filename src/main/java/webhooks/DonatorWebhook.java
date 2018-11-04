package webhooks;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.*;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
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

                if ( auth != null && auth.equals("lol")) {
                    if (event.get("body") != null) {
                        JSONObject body = (JSONObject)parser.parse((String)event.get("body"));
                        bodyString = body.toJSONString();
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
}
