package database;

import models.Player;
import net.dv8tion.jda.core.entities.Member;
import utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerCache {
    private static long lastUpdatedTime;
    private static HashMap<String, Player> playerMap;

    public static HashMap<String, Player> getPlayerCache(){
        if(needsUpdate()){
            updatePlayerCache();
        }
        return playerMap;
    }

    public static ArrayList<Player> getPlayerCacheAsList(){
        return new ArrayList<>(getPlayerCache().values());
    }

    public static boolean isStale(){
        return System.currentTimeMillis() - lastUpdatedTime > TimeUtil.ONE_HOUR;
    }

    private static void updatePlayerCache(){
        PlayerDatabase playerDatabase = new PlayerDatabase();
        List<Player> players = playerDatabase.retreiveAllPlayers();
        System.out.println("updating player cache");
        playerMap = new HashMap<>();

        players.forEach( (player) -> {
                playerMap.put(player.getId(), player);
        });

        lastUpdatedTime = System.currentTimeMillis();
    }

    public static void printAllPlayersFromCache(){
        HashMap<String, Player> playerCache = getPlayerCache();

        for(String key : playerCache.keySet()){
            System.out.println(playerCache.get(key.toString()));
        }
    }

    private static boolean needsUpdate(){
        return playerMap == null || isStale();
    }

    public static ArrayList<Player> convertMembersToPlayers(List<Member> members){
        HashMap<String, Player> cache = getPlayerCache();
        ArrayList<Player> players = new ArrayList<>();

        for(Member member : members){
            String userID = member.getUser().getId();
            if(cache.containsKey(userID)){
                players.add(cache.get(userID));
            }
        }
        return players;
    }
}
