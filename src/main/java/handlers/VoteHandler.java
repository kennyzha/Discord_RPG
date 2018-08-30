package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApplicationConstants;
import models.VoteRewardWrapper;
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
        System.out.println("hello");
        VoteRewardWrapper wrapper = objectMapper.readValue(inputStream, VoteRewardWrapper.class);
        System.out.println("1");
        try{
            String auth = wrapper.getAuthorization();
            JDA jda = new JDABuilder(AccountType.BOT).setToken(ApplicationConstants.TOKEN).buildBlocking();
            System.out.println("connected woo");
            if(!auth.equals(ApplicationConstants.WEBHOOK_AUTH)){
                jda.getUserById("190556636312633344").openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage("Invalid auth." + wrapper.getVote().toString()).queue();
                });
                System.out.println("sending invalid auth msg");
                objectMapper.writeValue(outputStream, "Invalid auth." + wrapper.getVote().toString());
                System.out.println("A");
            } else{
                jda.getUserById("190556636312633344").openPrivateChannel().queue((privateChannel) -> {
                    privateChannel.sendMessage("Valid auth." + wrapper.getVote().toString()).queue();
                });
                System.out.println("sending valid auth msg");

                objectMapper.writeValue(outputStream, wrapper.getVote().toString());
                System.out.println("B");
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
