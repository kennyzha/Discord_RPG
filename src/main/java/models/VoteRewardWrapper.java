package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteRewardWrapper {

    @JsonProperty("Authorization")
    private String authorization;

    private Vote vote;

    public VoteRewardWrapper(){

    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String Authorization) {
        this.authorization = Authorization;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
