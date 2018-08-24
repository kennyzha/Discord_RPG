package utils;

import net.dv8tion.jda.core.JDA;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class PostStats {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void toDiscordBots(JDA jda, String api_key) {
        System.out.println("Posting servercount to discordbots");

        String url = "https://discordbots.org/api/bots/"+jda.getSelfUser().getId()+"/stats";

        JSONObject data = new JSONObject();
        data.put("server_count", jda.getGuildCache().size());

        System.out.println(jda.getGuildCache().size());
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), data.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("User-Agent", "DiscordBot " + jda.getSelfUser().getName())
                .addHeader("Authorization", api_key)
                .build();

        try {
            Response r = CLIENT.newCall(request).execute();
            System.out.println("Posted server count, response: " + r.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}