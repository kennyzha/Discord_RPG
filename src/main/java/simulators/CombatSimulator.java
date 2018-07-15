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
        int num = 5;
        int wins = 0;

        for(int i = 0; i < 500; i++){
            CombatResult results = simulator.pvp(simulator.player2, simulator.player1);
//            System.out.println(results.getCombatResultString());

            if(results.isWinner()){
                wins++;
            }
        }

        System.out.println("Player wins: " + wins);

        simulator = new CombatSimulator();

        CombatResult pvmResults = simulator.pvm(simulator.player1, MonsterConstants.GIANT, 20);
        System.out.println("Asdf");
        System.out.println(pvmResults.getCombatString());
        System.out.println(pvmResults.getCombatResultString());


//        for(int i = 1; i < 150; i++){
//            simulator.player1.setLevel(i);
//            System.out.println("Level " + i);
//
//            DecimalFormat format = new DecimalFormat("#,###");
//            double exp = simulator.player1.calcExpToNextLevel();
//
//            int hpGained = simulator.player1.calcActualHealthGained(simulator.player1.calcBaseHealthGained());
//            simulator.player1.increHealth(hpGained);
//            System.out.println("Exp to level: " + format.format(exp) + " | HP: " + format.format((double) simulator.player1.getHealth()) + " | HP GAIN: " + hpGained);
//        }
    }

    public CombatSimulator(){
        combatHandler = new CombatHandler();
        player1 = new Player("");
//        player1.setStats(1500,600,900);
//        player1.setStats(600,900,1500);
        player1.setStats(1000,2000,500);
        player1.setHealth(3500);
        player2 = new Player("");
//        player1.setStats(400, 400, 200);
        player2.setStats(100,1850,1850);


    }

    public CombatResult pvp(Player p1, Player p2){
        return combatHandler.fightPlayer(p1, p2);
    }

    public CombatResult pvm(Player p1, Monster m1, int numFights){
        return combatHandler.fightMonster(p1, m1, numFights);
    }
}
