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

        assertEquals(.75, player.calcStatGain());
    }

    @Test
    public void level133StatGainTest(){
        player.setLevel(133);

        assertEquals(.8325, player.calcStatGain());
    }

    @Test
    public void level200StatGainTest(){
        player.setLevel(200);

        assertEquals(1.0, player.calcStatGain());
    }

    @Test
    public void level300StatGainTest(){
        player.setLevel(300);

        assertEquals(1.5, player.calcStatGain());
    }

    @Test
    public void level342StatGainTest(){
        player.setLevel(342);

        assertEquals(1.71, player.calcStatGain());
    }

    @Test
    public void level653StatGainTest(){
        player.setLevel(653);

        assertEquals(3.265, player.calcStatGain());
    }

    @Test
    public void level600StatGainTest(){
        player.setLevel(600);

        assertEquals(3.0, player.calcStatGain());
    }

    @Test
    public void level800StatGainTest(){
        player.setLevel(800);

        assertEquals(4.0, player.calcStatGain());
    }

    @Test
    public void level850StatGainTest(){
        player.setLevel(850);

        assertEquals(4.25, player.calcStatGain());
    }

    @Test
    public void level1100StatGainTest(){
        player.setLevel(1100);

        assertEquals(6.0, player.calcStatGain());
    }

    @Test
    public void level1500StatGainTest(){
        player.setLevel(1500);

        assertEquals(10.0, player.calcStatGain());
    }
}
