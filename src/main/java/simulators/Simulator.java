package simulators;

import config.ApplicationConstants;
import org.discordbots.api.client.DiscordBotListAPI;

public class Simulator {
    public static void main(String[] args){
        System.out.println("hello");

        String token = ApplicationConstants.DISCORD_BOT_TOKEN;

        DiscordBotListAPI api = new DiscordBotListAPI.Builder().token(token).botId("449444515548495882").build();

        boolean voted = false;

        String userId = "190556636312633344"; // ID of the user you're checking
        api.hasVoted(userId).whenComplete((hasVoted, e) -> {
            if(hasVoted){
                System.out.println("This person has voted!");

            }
            else
                System.out.println("This person has not voted!");
        });

    }
}
