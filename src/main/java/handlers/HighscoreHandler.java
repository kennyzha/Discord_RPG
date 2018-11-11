package handlers;

import database.PlayerDatabase;
import models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class HighscoreHandler {
    private long timeSinceUpdated;

    private ArrayList<Player> levelHighscore;
    private ArrayList<Player> speedHighscore;
    private ArrayList<Player> powerHighscore;
    private ArrayList<Player> strengthHighscore;
    private ArrayList<Player> totalHighscore;
    private ArrayList<Player> goldHighscore;

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
            Collections.reverse(levelHighscore);
        }
            return levelHighscore;
    }

    public ArrayList<Player> getSpeedHighscore(){
        if(speedHighscore.isEmpty() || isStale()){
            updateHighscores();
            Collections.reverse(speedHighscore);
        }
        return speedHighscore;
    }

    public ArrayList<Player> getPowerHighscore(){
        if(powerHighscore.isEmpty() || isStale()){
            updateHighscores();
            Collections.reverse(powerHighscore);
        }
        return powerHighscore;
    }

    public ArrayList<Player> getStrengthHighscore(){
        if(strengthHighscore.isEmpty() || isStale()){
            updateHighscores();
            Collections.reverse(strengthHighscore);
        }
        return strengthHighscore;
    }

    public ArrayList<Player> getTotalHighscore(){
        if(totalHighscore.isEmpty() || isStale()){
            updateHighscores();
            Collections.reverse(totalHighscore);
        }
        return totalHighscore;
    }

    public ArrayList<Player> getGoldHighscore(){
        if(goldHighscore.isEmpty() || isStale()){
            updateHighscores();
            Collections.reverse(goldHighscore);
        }
        return goldHighscore;
    }

    public boolean isStale(){
        long curTime = System.currentTimeMillis();
        long elapsedTime = curTime - timeSinceUpdated;

        return elapsedTime > HIGHSCORE_REFRESH_TIME;
    }

    private void updateHighscores(){
        this.timeSinceUpdated = System.currentTimeMillis();
        initHighscoreArrays();

        PriorityQueue<Player> levelPq = new PriorityQueue<>((p1, p2) -> (p1.getLevel() - p2.getLevel()));
        PriorityQueue<Player> powerPq = new PriorityQueue<>((p1, p2) -> (int) (p1.getPower() - p2.getPower()));
        PriorityQueue<Player> speedPq = new PriorityQueue<>((p1, p2) -> (int) (p1.getSpeed() - p2.getSpeed()));
        PriorityQueue<Player> strengthPq = new PriorityQueue<>((p1, p2) -> (int) (p1.getStrength() - p2.getStrength()));
        PriorityQueue<Player> totalPq = new PriorityQueue<>((p1,p2) -> (int) (p1.getTotalStats() - p2.getTotalStats()));
        PriorityQueue<Player> goldPq = new PriorityQueue<>((p1,p2) -> (p1.getGold() - p2.getGold()));

        List<Player> players = playerDatabase.retreivePlayers();

        populateQueues(players, levelPq, powerPq, speedPq, strengthPq, totalPq, goldPq);
        populateHighscoreArrays(levelPq, powerPq, speedPq, strengthPq, totalPq, goldPq);
    }

    public void populateQueues(List<Player> players, PriorityQueue<Player> levelPq, PriorityQueue<Player> powerPq, PriorityQueue<Player> speedPq, PriorityQueue<Player> strengthPq,
                               PriorityQueue<Player> totalPq, PriorityQueue<Player> goldPq){
        for(Player p : players){
            if(levelPq.size() >= HIGHSCORE_SIZE){
                levelPq.poll();
                powerPq.poll();
                speedPq.poll();
                strengthPq.poll();
                totalPq.poll();
                goldPq.poll();
            }

            levelPq.add(p);
            powerPq.add(p);
            speedPq.add(p);
            strengthPq.add(p);
            totalPq.add(p);
            goldPq.add(p);
        }
    }

    public void populateHighscoreArrays(PriorityQueue<Player> levelPq, PriorityQueue<Player> powerPq, PriorityQueue<Player> speedPq, PriorityQueue<Player> strengthPq,
                                        PriorityQueue<Player> totalPq, PriorityQueue<Player> goldPq){
        int size = Math.min(HIGHSCORE_SIZE, levelPq.size());

        for(int i = 0; i < size; i++){
            levelHighscore.add(levelPq.poll());
            powerHighscore.add(powerPq.poll());
            speedHighscore.add(speedPq.poll());
            strengthHighscore.add(strengthPq.poll());
            totalHighscore.add(totalPq.poll());
            goldHighscore.add(goldPq.poll());
        }
    }

    private void initHighscoreArrays(){
        this.levelHighscore = new ArrayList<>();
        this.speedHighscore = new ArrayList<>();
        this.powerHighscore = new ArrayList<>();
        this.strengthHighscore = new ArrayList<>();
        this.totalHighscore = new ArrayList<>();
        this.goldHighscore = new ArrayList<>();
    }
}
