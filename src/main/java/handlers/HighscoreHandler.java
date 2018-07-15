package handlers;

import database.PlayerDatabase;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HighscoreHandler {
    private long timeSinceUpdated;

    private ArrayList<Player> levelHighscore;
    private ArrayList<Player> speedHighscore;
    private ArrayList<Player> powerHighscore;
    private ArrayList<Player> strengthHighscore;

    private PlayerDatabase playerDatabase;

    private final int HIGHSCORE_SIZE = 10;
    private final long HIGHSCORE_REFRESH_TIME = 86400000;

    public HighscoreHandler() {
        playerDatabase = new PlayerDatabase();
        this.timeSinceUpdated = 0L;

        initHighscoreArrays();
    }

    public ArrayList<Player> getLevelHighscore(){
        if(levelHighscore.isEmpty() || isStale()){
            updateHighscores();
        }
            return levelHighscore;
    }

    public ArrayList<Player> getSpeedHighscore(){
        if(speedHighscore.isEmpty() || isStale()){
            updateHighscores();
        }
        return speedHighscore;
    }

    public ArrayList<Player> getPowerHighscore(){
        if(powerHighscore.isEmpty() || isStale()){
            updateHighscores();
        }
        return powerHighscore;
    }

    public ArrayList<Player> getStrengthHighscore(){
        if(strengthHighscore.isEmpty() || isStale()){
            updateHighscores();
        }
        return strengthHighscore;
    }

    public boolean isStale(){
        long curTime = System.currentTimeMillis();
        long elapsedTime = curTime - timeSinceUpdated;

        return elapsedTime > HIGHSCORE_REFRESH_TIME;
    }

    private void updateHighscores(){
        this.timeSinceUpdated = System.currentTimeMillis();
        initHighscoreArrays();

        PriorityQueue<Player> levelsPq = new PriorityQueue<>((p1, p2) -> (p2.getLevel() - p1.getLevel()));
        PriorityQueue<Player> powerPq = new PriorityQueue<>((p1, p2) -> (int) (p2.getPower() - p1.getPower()));
        PriorityQueue<Player> speedPq = new PriorityQueue<>((p1, p2) -> (int) (p2.getSpeed() - p1.getSpeed()));
        PriorityQueue<Player> strengthPq = new PriorityQueue<>((p1, p2) -> (int) (p2.getStrength() - p1.getStrength()));

        List<Player> players = playerDatabase.retreivePlayers();

        for(Player p : players){
            levelsPq.add(p);
            powerPq.add(p);
            speedPq.add(p);
            strengthPq.add(p);
        }

        for(int i = 0; i < HIGHSCORE_SIZE; i++){
            levelHighscore.add(levelsPq.poll());
            powerHighscore.add(powerPq.poll());
            speedHighscore.add(speedPq.poll());
            strengthHighscore.add(strengthPq.poll());
        }
    }

    private void initHighscoreArrays(){
        this.levelHighscore = new ArrayList<>();
        this.speedHighscore = new ArrayList<>();
        this.powerHighscore = new ArrayList<>();
        this.strengthHighscore = new ArrayList<>();
    }
}
