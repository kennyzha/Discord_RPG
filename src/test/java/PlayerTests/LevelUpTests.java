package PlayerTests;

import config.MonsterConstants;
import handlers.CombatHandler;
import models.CombatResult;
import models.Monster;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class LevelUpTests {
    CombatHandler combatHandler;
    Player overPoweredPlayer;
    Player underPoweredPlayer;

    @Before
    public void init(){
        combatHandler = new CombatHandler();

        overPoweredPlayer = new Player("");
        overPoweredPlayer.setPower(99999);
        overPoweredPlayer.setSpeed(99999);
        overPoweredPlayer.setStrength(99999);

        underPoweredPlayer = new Player("");
        underPoweredPlayer.setPower(50);
        underPoweredPlayer.setSpeed(50);
        underPoweredPlayer.setStrength(50);

    }

    @Test
    public void playerLevelUpSlimeTest() {
        Monster slime = MonsterConstants.SLIME;

        int oldLevel = overPoweredPlayer.getLevel();
        int oldHealth = overPoweredPlayer.getHealth();

        CombatResult combatResult = combatHandler.fightMonster(overPoweredPlayer, slime, 10);

        int newLevel = overPoweredPlayer.getLevel();
        int newHealth = overPoweredPlayer.getHealth();

        System.out.println(combatResult.getCombatResultString());
        System.out.println(combatResult.getEntityOneStats());
        System.out.println(String.format("Player used to be level %d now he is level %d", oldLevel, newLevel));

        assertEquals(4, newLevel);

        int healthDiff = newHealth - oldHealth;
        assertTrue(healthDiff >= 7);
    }

    @Test
    public void playerLevelUpKoboldTest(){
        Monster kobold = MonsterConstants.KOBOLD;
        overPoweredPlayer.setLevel(20);

        int oldLevel = overPoweredPlayer.getLevel();
        CombatResult combatResult = combatHandler.fightMonster(overPoweredPlayer, kobold, 100);
        int newLevel = overPoweredPlayer.getLevel();

        System.out.println(combatResult.getCombatResultString());
        System.out.println(combatResult.getEntityOneStats());
        System.out.println(String.format("Player used to be level %d now he is level %d", oldLevel, newLevel));
        assertEquals(30, newLevel);
    }

    @Test
    public void underPoweredTest(){
        Monster kobold = MonsterConstants.KOBOLD;
        underPoweredPlayer.setLevel(20);

        CombatResult combatResult = combatHandler.fightMonster(underPoweredPlayer, kobold, 20);

        System.out.println(combatResult.getCombatResultString());
        System.out.println(combatResult.getEntityOneStats());
    }

}
