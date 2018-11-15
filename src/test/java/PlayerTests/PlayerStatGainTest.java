package PlayerTests;

import models.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PlayerStatGainTest {
    private Player player;

    @Before
    public void init(){
        player = new Player("123");
    }

    @Test
    public void level100StatGainTest(){
        player.setLevel(100);

        assertEquals(player.calcStatGain(), .75);
    }

    @Test
    public void level133StatGainTest(){
        player.setLevel(133);

        assertEquals(player.calcStatGain(), .8325);
    }

    @Test
    public void level200StatGainTest(){
        player.setLevel(200);

        assertEquals(player.calcStatGain(), 1.0);
    }

    @Test
    public void level300StatGainTest(){
        player.setLevel(300);

        assertEquals(player.calcStatGain(), 1.5);
    }

    @Test
    public void level342StatGainTest(){
        player.setLevel(342);

        assertEquals(player.calcStatGain(), 1.71);
    }

    @Test
    public void level600StatGainTest(){
        player.setLevel(600);

        assertEquals(player.calcStatGain(), 3.0);
    }

    @Test
    public void level800StatGainTest(){
        player.setLevel(800);

        assertEquals(player.calcStatGain(), 5.0);
    }
}
