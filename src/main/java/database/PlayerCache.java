package database;

import models.Player;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerCache {
    private static long lastUpdatedTime;
    private static HashMap<String, Player> playerMap;

    public static HashMap<String, Player> getPlayerCache(){
        if(playerMap == null || isStale()){
            updatePlayerCache();
        }
        return playerMap;
    }

    public static boolean isStale(){
        return System.currentTimeMillis() - lastUpdatedTime > TimeUtil.ONE_DAY;
    }

    public static void updatePlayerCache(){
        PlayerDatabase playerDatabase = new PlayerDatabase();
        List<Player> players = playerDatabase.retreiveAllPlayers();
    }
}
