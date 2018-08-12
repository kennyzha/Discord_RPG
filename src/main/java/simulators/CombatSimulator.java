package simulators;

import config.MonsterConstants;
import handlers.CombatHandler;
import models.CombatResult;
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
//        for(int i = 0; i < 500; i++){
//            CombatResult results = simulator.pvp(simulator.player1, simulator.player2);
//            System.out.println(results.getCombatResultString());
//
//            if(results.isWinner()){
//                wins++;
//            }
//        }

//        System.out.println("Player wins: " + wins);
//
//        simulator = new CombatSimulator();
//
//        CombatResult pvmResults = simulator.pvm(simulator.player1, MonsterConstants.ORC, 2000);
//        System.out.println(pvmResults.getCombatString());
//        System.out.println(pvmResults.getCombatResultString());

//       int total = 0;
//        for(int i = 1; i < 700; i++){
//            simulator.player1.setLevel(i);
//            System.out.println("Level " + i);
//            total += simulator.player1.calcExpToNextLevel();
//
//            DecimalFormat format = new DecimalFormat("#,###");
//            double exp = simulator.player1.calcExpToNextLevel();
//
//            int hpGained = simulator.player1.calcActualHealthGained(simulator.player1.calcBaseHealthGained());
//            simulator.player1.increHealth(hpGained);
//            System.out.println("Exp to level: " + format.format(exp) + " | HP: " + format.format((double) simulator.player1.getHealth()) + " | HP GAIN: " + hpGained);
//        }
//
//        System.out.println(total);

        System.out.println(simulator.monstersNeededToLevel(simulator.player1, MonsterConstants.i, 325, 350));

    }

    public CombatSimulator(){
        combatHandler = new CombatHandler();
        player1 = new Player("1");
        player2 = new Player("2");

//        player1.setStats(30000,40000,30000);
        player1.setStats(60000,60000,30000);



        player2.setStats(15000,45000,90000);
 //       player2.setStats(20000, 30000, 50000);
        player2.setHealth(450000);


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
        while(p.getLevel() != targetLevel){
            pvm(p, m, 1);
            p.updateLevelAndExp();
            monstersKilled++;
        }

        return monstersKilled;
    }
}
