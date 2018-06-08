package ExperienceTests;

import models.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LevelExpTests {
    Player player;

    @Before
    public void init(){
        player = new Player("1");
    }

    public long calcNextLevelExp(int curLevel){
        player.setLevel(curLevel);
        return player.calcExpToNextLevel();
    }

    @Test
    public void nextLevelTenTest(){
        long expectedExpTo11 = 13000 - 9300;
        assertEquals(expectedExpTo11, calcNextLevelExp(10));
    }

    @Test
    public void nextLevelFiftyTest(){
        long expectedExpToLevel51 = 1965000 - 1847300;
        assertEquals(expectedExpToLevel51, calcNextLevelExp(50));
    }

    @Test
    public void nextLevelFiveHundredTest(){
        long expectedExpToLevel51 = 2070900000 - 2058474800;
        assertEquals(expectedExpToLevel51, calcNextLevelExp(500));
    }

    @Test
    public void nextLevelOneThousandTest(){
        long expectedExpToLevel51 = 16616800000L - 16566949800L;
        assertEquals(expectedExpToLevel51, calcNextLevelExp(1000));
    }
}
