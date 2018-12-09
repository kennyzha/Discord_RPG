package handlers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import database.PlayerCache;
import database.PlayerDatabase;
import models.Player;
import net.dv8tion.jda.core.entities.Member;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class HighscoreHandler {
    private long timeSinceUpdated;

    private ArrayList<Player> levelHighscore;
    private ArrayList<Player> speedHighscore;
    private ArrayList<Player> powerHighscore;
    private ArrayList<Player> strengthHighscore;
    private ArrayList<Player> totalHighscore;
    private ArrayList<Player> goldHighscore;


    private static final int HIGHSCORE_SIZE = 10;
    private static final long HIGHSCORE_REFRESH_TIME = TimeUtil.ONE_HOUR;

    private static Cache<String, ArrayList<Player>> guildLevelHighscores = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();
    private static Cache<String, ArrayList<Player>> guildTotalHighscores = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();

    public HighscoreHandler() {
        this.timeSinceUpdated = 0L;
        initHighscoreArrays();
    }

    public static ArrayList<Player> getGuildLevelsHighscores(String guildID, List<Member> members){
        try {
            return guildLevelHighscores.get(guildID, () -> {
                System.out.println("updating level hs");
                ArrayList<Player> players = PlayerCache.convertMembersToPlayers(members);
                return getTopLevelsFromPlayers(players);
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<Player> getGuildTotalsHighscores(String guildID, List<Member> members){
        try {
            return guildTotalHighscores.get(guildID, () -> {
                System.out.println("updating total hs");
                ArrayList<Player> players = PlayerCache.convertMembersToPlayers(members);
                ArrayList<Player> topTotalsArr = getTopTotalsFromPlayers(players);
                return topTotalsArr;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Player> getLevelHighscore(){
        if(levelHighscore.isEmpty() || isStale()){
            updateHighscores();
        }

        return levelHighscore;
    }

    public static ArrayList<Player> getTopLevelsFromPlayers(ArrayList<Player> players){
        PriorityQueue<Player> levelPq = new PriorityQueue<>((p1, p2) -> (p1.getLevel() - p2.getLevel()));

        for(Player p : players){
            levelPq.add(p);

            if(levelPq.size() > HIGHSCORE_SIZE){
                levelPq.poll();
            }
        }

        ArrayList<Player> levelsHighscore = new ArrayList<>();
        while(!levelPq.isEmpty()){
            levelsHighscore.add(levelPq.poll());
        }
        Collections.reverse(levelsHighscore);
        return levelsHighscore;
    }

    public static ArrayList<Player> getTopTotalsFromPlayers(ArrayList<Player> players){
        PriorityQueue<Player> totalPq = new PriorityQueue<>((p1, p2) -> (int) (p1.getTotalStats() - p2.getTotalStats()));

        for(Player p : players){
            totalPq.add(p);
            if(totalPq.size() > HIGHSCORE_SIZE){
                totalPq.poll();
            }
        }

        ArrayList<Player> totalHighscore = new ArrayList<>();
        while(!totalPq.isEmpty()){
            totalHighscore.add(totalPq.poll());
        }

        Collections.reverse(totalHighscore);
        return totalHighscore;
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

    public ArrayList<Player> getTotalHighscore(){
        if(totalHighscore.isEmpty() || isStale()){
            updateHighscores();
        }

        return totalHighscore;
    }

    public ArrayList<Player> getGoldHighscore(){
        if(goldHighscore.isEmpty() || isStale()){
            updateHighscores();
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
        PriorityQueue<Player> totalPq = new PriorityQueue<>((p1, p2) -> (int) (p1.getTotalStats() - p2.getTotalStats()));
        PriorityQueue<Player> goldPq = new PriorityQueue<>((p1, p2) -> (p1.getGold() - p2.getGold()));

        List<Player> players = PlayerCache.getPlayerCacheAsList();

        populateQueues(players, levelPq, powerPq, speedPq, strengthPq, totalPq, goldPq);
        populateHighscoreArrays(levelPq, powerPq, speedPq, strengthPq, totalPq, goldPq);
        reverseArrays();
        System.gc();
    }

    public void populateQueues(List<Player> players, PriorityQueue<Player> levelPq, PriorityQueue<Player> powerPq, PriorityQueue<Player> speedPq, PriorityQueue<Player> strengthPq,
                               PriorityQueue<Player> totalPq, PriorityQueue<Player> goldPq){

        for(Player p : players){
            levelPq.add(p);
            powerPq.add(p);
            speedPq.add(p);
            strengthPq.add(p);
            totalPq.add(p);
            goldPq.add(p);

            if(levelPq.size() > HIGHSCORE_SIZE){
                levelPq.poll();
                powerPq.poll();
                speedPq.poll();
                strengthPq.poll();
                totalPq.poll();
                goldPq.poll();
            }
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

    private void reverseArrays(){
        Collections.reverse(levelHighscore);
        Collections.reverse(speedHighscore);
        Collections.reverse(powerHighscore);
        Collections.reverse(strengthHighscore);
        Collections.reverse(totalHighscore);
        Collections.reverse(goldHighscore);
    }
}
