package PlayerTests;

import models.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HealthGainedTests {
    Player player;

    @Before
    public void init(){
        player = new Player("");
    }

    public void baseHpGainedTest(int level, int expectedHpGained){
        player.setLevel(level);
        int actual = player.calcBaseHealthGained();

        assertEquals(expectedHpGained, actual);
    }

    @Test
    public void baseHpGainedTwentyTest(){
        baseHpGainedTest(20, 50);
    }

    @Test
    public void baseHpGainedHundredTest(){
        baseHpGainedTest(100, 250);
    }

    @Test
    public void baseHpGainedTwoHundredTest(){
        baseHpGainedTest(200, 450);
    }

    @Test
    public void baseHpGainedFiveHundredTest(){
        baseHpGainedTest(500, 600);
    }

}
