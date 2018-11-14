package simulators;

import config.MonsterConstants;
import handlers.CombatHandler;
import utils.CombatResult;
import models.Monster;
import models.Player;

import java.text.DecimalFormat;

public class CombatSimulator {

    CombatHandler combatHandler;
    private Player player1;
    private Player player2;

    public static void main(String[] args){
       CombatSimulator simulator = new CombatSimulator();
//        int num = 5;
//        int wins = 0;
//
//        for(int i = 0; i < 100; i++){
//            CombatResult results = simulator.pvp(simulator.player1, simulator.player2);
//            System.out.println(results.getCombatResultString());
//
//            if(results.isWinner()){
//                wins++;
//            }
//        }
//
//        System.out.println("Player wins: " + wins);
//
//        simulator = new CombatSimulator();
//
/*        CombatResult pvmResults = simulator.pvm(simulator.player1, MonsterConstants.FAIRY, 500);
        System.out.println(pvmResults.getCombatString());
        System.out.println(pvmResults.getCombatResultString());*/

       int total = 0;
        for(int i = 1; i < 1501; i++){
            simulator.player1.setLevel(i);
            total += simulator.player1.calcExpToNextLevel();

            DecimalFormat format = new DecimalFormat("#,###");
            double exp = simulator.player1.calcExpToNextLevel();

            int hpGained = simulator.player1.calcActualHealthGained(simulator.player1.calcBaseHealthGained());
            simulator.player1.increHealth(hpGained);

            if(i % 50 == 0){
                System.out.println("Level " + i);
                System.out.println("Exp to level: " + format.format(exp) + " | HP: " + format.format((double) simulator.player1.getHealth()) + " | HP GAIN: " + hpGained);
            }
        }

        System.out.println(total);

        System.out.println(simulator.monstersNeededToLevel(simulator.player1, MonsterConstants.WYVERN, 400, 450));

    }

    public CombatSimulator(){
        combatHandler = new CombatHandler();
        player1 = new Player("1");
        player2 = new Player("2");

        player2.setStats(900000,1800000,300000);
        player1.setStats(300000,1000000,1700000);
    }

    public CombatResult pvp(Player p1, Player p2){
        return combatHandler.fightPlayer(p1, p2);
    }

    public CombatResult pvm(Player p1, Monster m1, int numFights){
        return combatHandler.fightMonster(p1, m1, numFights);
    }

    public int monstersNeededToLevel(Player p, Monster m, int initialLevel, int targetLevel){
        int monstersKilled = 0;
        p.setLevel(initialLevel);
        while(p.getLevel() < targetLevel){
            pvm(p, m, 1);
            p.updateLevelAndExp();
            monstersKilled++;
        }

        return monstersKilled;
    }

    public void initPlayers(){
        // level 200
        player2.setStats(3600, 6000, 2400);
        player1.setStats(2400, 3600, 6000);
        player1.setHealth(50000);
        player2.setHealth(50000);

        // level 300
        player2.setStats(7800, 13000, 5200);
        player1.setStats(5200, 7800, 13000);
        player1.setHealth(101000);
        player2.setHealth(101000);

        // level 400
        player1.setStats(9600, 14400, 24000);
        player2.setStats(14400, 24000, 9600);
        player1.setHealth(175000);
        player2.setHealth(175000);

        // level 600
        player1.setStats(26000, 39000, 65000);
        player2.setStats(39000, 65000, 26000);
        player1.setHealth(385000);
        player2.setHealth(385000);

        // level 1000
        player1.setStats(100000, 150000, 250000);
        player2.setStats(150000, 250000, 100000);
        player1.setHealth(1057000);
        player2.setHealth(1057000);

        // level 1500
        player2.setStats(600000, 1000000, 400000);
        player1.setStats(400000, 600000, 1000000);
        player1.setHealth(2371406);
        player2.setHealth(2371406);

        player2.setStats(10000, 10000, 100000);
        player1.setStats(100000, 100000, 100000);
        player1.setHealth(500000);
        player2.setHealth(500000);
    }
}
