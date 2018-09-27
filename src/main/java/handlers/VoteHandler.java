package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApplicationConstants;
import database.PlayerDatabase;
import models.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VoteHandler implements RequestStreamHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        System.out.println("Voting webhook triggered.");
        VoteRewardWrapper wrapper = objectMapper.readValue(inputStream, VoteRewardWrapper.class);

        if(wrapper.getVote().getType().equals("test")){
            System.out.println("TEST: " + wrapper.getAuthorization() + " " + wrapper.getVote().toString());
            return;
        }

        String auth = wrapper.getAuthorization();
        String user = wrapper.getVote().getUser();

        if(auth.equals(ApplicationConstants.WEBHOOK_AUTH)){
            System.out.println("VOTE: VALID AUITH. " + wrapper.getAuthorization() + wrapper.getVote().toString());
            System.out.println("Refreshing player stamina");
            PlayerDatabase playerDatabase = new PlayerDatabase();
            Player player = playerDatabase.grabPlayer(user);
            player.setStamina(player.getStamina() + 20);
//            playerDatabase.insertPlayerStamina(new Stamina(user));
            System.out.println("Refreshed player stamina");

            if(wrapper.getVote().isWeekend()){
                System.out.println("Its the weekends. Adding one crate of gold to player.");

                int crateCost = Crate.cost[Item.getLevelBracket(player.getLevel())];
                player.setGold(player.getGold() + (crateCost));
                System.out.println("Gold added: " + crateCost);
            }
            playerDatabase.insertPlayer(player);
        } else{
            System.out.println("VOTE: INVALID AUTH. " + wrapper.getAuthorization() + wrapper.getVote().toString() );
        }


/*
        try{
            JDA jda = new JDABuilder(AccountType.BOT).setToken(ApplicationConstants.TEST_TOKEN).buildBlocking();
            System.out.println("connected woo");
            if(!auth.equals(ApplicationConstants.WEBHOOK_AUTH)){
                jda.getUserById("190556636312633344").openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage("Invalid auth: " + wrapper.getAuthorization() + " " + wrapper.getVote().toString()).queue();
                });
                System.out.println("sending invalid auth msg");
                objectMapper.writeValue(outputStream, "Invalid auth." + wrapper.getAuthorization() + " " + wrapper.getVote().toString());
                System.out.println("A");
                System.out.println(wrapper.getVote().toString());
            } else{
                jda.getUserById("190556636312633344").openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage("Valid auth: " + wrapper.getAuthorization() + " " + wrapper.getVote().toString()).queue();
                });
                System.out.println("sending valid auth msg");

                objectMapper.writeValue(outputStream, wrapper.getVote().toString());
                System.out.println("B");
                System.out.println(wrapper.getVote().toString());
            }
            System.out.println("sleeping");
            Thread.sleep(5000);
            System.out.println("done sleeping");
        } catch (Exception e){
            System.out.println("bummer + " + e.getMessage());
            e.printStackTrace();
        }*/


    }
}
