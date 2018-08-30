package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Vote;
import models.VoteRewardWrapper;

public class VoteRewardHandler implements RequestHandler<VoteRewardWrapper, String> {
/*    public String myHandler(String token, String userID, String type, boolean isWeekend){
        System.out.println("voted");
        return "successs";
    }*/

    @Override
    public String handleRequest(VoteRewardWrapper vote, Context context) {
        String auth = vote.getAuthorization();


        return vote.getVote().toString();
    }
}
