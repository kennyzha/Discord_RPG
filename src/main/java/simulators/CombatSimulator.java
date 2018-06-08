package simulators;

import handlers.CombatHandler;
import models.CombatResult;
import models.Player;

public class CombatSimulator {

    CombatHandler combatHandler;
    private Player player1;
    private Player player2;

    public static void main(String[] args){
        CombatSimulator simulator = new CombatSimulator();
        CombatResult results = simulator.pvp(simulator.player1, simulator.player2);

        System.out.println(results.getCombatResultString());
        System.out.println(results.getEntityOneStats());
    }

    public CombatSimulator(){
        combatHandler = new CombatHandler();
        player1 = new Player("");
        player1.setHealth(5000);
        player1.setStats(400,600,100);
        player2 = new Player("");
        player2.setHealth(5000);
        player2.setStats(400, 500, 200);
    }

    public CombatResult pvp(Player p1, Player p2){
        return combatHandler.fightPlayer(p1, p2);
    }
}
