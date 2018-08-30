package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vote {
    private String user;
    private String bot;
    private String type;
    private String query;

    @JsonProperty("isWeekend")
    private boolean weekend;

    public Vote(){

    }

    public Vote(String user, String bot, String type, String query, boolean isWeekend) {
        this.user = user;
        this.bot = bot;
        this.type = type;
        this.query = query;
        this.weekend = isWeekend;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    @Override
    public String toString() {
        return String.format("User %s just did a %s and weekend is %s", getUser(), getType(), weekend);
    }
}
