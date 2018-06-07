package CombatTests;

import config.ApplicationConstants;
import handlers.CombatHandler;
import models.CombatResult;
import models.Player;
import org.junit.Before;
import org.junit.Test;

public class PlayerVsMonsterTests {
    Player player;
    CombatHandler combatHandler;

    @Before
    public void init(){
        combatHandler = new CombatHandler();
        player = new Player("1");
        player.setHealth(250);
        player.setStrength(100);
        player.setPower(100);
        player.setSpeed(100);
    }

    @Test
    public void playerVsMonster(){
        CombatResult combatResult = combatHandler.simulateCombat(player, ApplicationConstants.KOBOLD, null);

        System.out.println(combatResult.getEntityOneStats() + "\n" + combatResult.getCombatResultString());
    }
}
