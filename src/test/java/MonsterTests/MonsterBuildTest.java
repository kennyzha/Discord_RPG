package MonsterTests;

import models.Monster;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MonsterBuildTest {

    @Test
    public void monsterSpeedBuildTest(){
        Monster m = new Monster("m", 125, 15000, 5000, 25000, 1000, Monster.Build.SPEED);

        assertEquals(2500, m.getSpeed(), .01);
        assertEquals(1500, m.getPower(), .01);
        assertEquals(1000, m.getStrength(), .01);
    }

    @Test
    public void monsterSpeedBuildTest2(){
        Monster m = new Monster("m", 150, 15000, 7500, 25000, 1000, Monster.Build.SPEED);

        assertEquals(3750, m.getSpeed(), .01);
        assertEquals(2250, m.getPower(), .01);
        assertEquals(1500, m.getStrength(), .01);
    }

    @Test
    public void monsterSpeedBuildTest3(){
        Monster m = new Monster("m", 200, 15000, 13000, 25000, 1000, Monster.Build.SPEED);

        assertEquals(6500, m.getSpeed(), .01);
        assertEquals(3900, m.getPower(), .01);
        assertEquals(2600, m.getStrength(), .01);
    }

    @Test
    public void monsterSpeedBuildTest4(){
        Monster m = new Monster("m", 225, 15000, 17000, 25000, 1000, Monster.Build.SPEED);

        assertEquals(8500, m.getSpeed(), .01);
        assertEquals(5100, m.getPower(), .01);
        assertEquals(3400, m.getStrength(), .01);
    }

    @Test
    public void monsterSpeedBuildTest5(){
        Monster m = new Monster("m", 375, 15000, 45000, 25000, 1000, Monster.Build.SPEED);

        assertEquals(22500, m.getSpeed(), .01);
        assertEquals(13500, m.getPower(), .01);
        assertEquals(9000, m.getStrength(), .01);
    }

    @Test
    public void monsterStrengthBuildTest(){
        Monster m = new Monster("m", 175, 15000, 10000, 25000, 1000, Monster.Build.STRENGTH);

        assertEquals(3000, m.getSpeed(), .01);
        assertEquals(2000, m.getPower(), .01);
        assertEquals(5000, m.getStrength(), .01);
    }

    @Test
    public void monsterStrengthBuildTest2(){
        Monster m = new Monster("m", 225, 15000, 17000, 25000, 1000, Monster.Build.STRENGTH);

        assertEquals(5100, m.getSpeed(), .01);
        assertEquals(3400, m.getPower(), .01);
        assertEquals(8500, m.getStrength(), .01);
    }
}
