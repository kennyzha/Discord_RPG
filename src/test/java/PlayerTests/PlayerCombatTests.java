package PlayerTests;

import handlers.CombatHandler;
import models.Player;
import org.junit.Before;
import org.junit.Test;
import utils.CombatResult;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PlayerCombatTests {

    private Player player;
    private Player playerTwo;

    @Before
    public void init(){
        player = new Player("PlayerOne");
        playerTwo = new Player("PlayerTwo");
    }

    @Test
    public void playerCombatTestWin(){
        CombatHandler combatHandler = new CombatHandler();
        player.setSpeed(500);
        player.setStrength(500);

        CombatResult combatResult = combatHandler.playerFight(player, playerTwo);

        assertTrue(combatResult.isWinner());
        assertEquals(combatResult.getEntityOneStats().getHealthRemaining(), 200);
        assertEquals(combatResult.getEntityTwoStats().getHealthRemaining(), 0);
    }
    @Test
    public void playerCombatTestLost(){
        CombatHandler combatHandler = new CombatHandler();

        playerTwo.setSpeed(6000);
        playerTwo.setStrength(10000);

        CombatResult combatResult = combatHandler.playerFight(player, playerTwo);

        assertEquals(combatResult.getResult(), CombatResult.Result.LOST);
        assertEquals(combatResult.getEntityOneStats().getHealthRemaining(), 0);
        assertEquals(combatResult.getEntityTwoStats().getHealthRemaining(), 200);

    }

    @Test
    public void playerCombatTestDraw(){
        CombatHandler combatHandler = new CombatHandler();

        player.setStrength(10000);
        playerTwo.setStrength(10000);

        CombatResult combatResult = combatHandler.playerFight(player, playerTwo);

        assertEquals(combatResult.getResult(), CombatResult.Result.DRAW);
        assertEquals(combatResult.getEntityOneStats().getHealthRemaining(), 200);
        assertEquals(combatResult.getEntityTwoStats().getHealthRemaining(), 200);
    }
}
