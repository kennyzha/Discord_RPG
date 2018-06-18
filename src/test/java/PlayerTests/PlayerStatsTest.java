package PlayerTests;

import models.Player;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class PlayerStatsTest {
    Player player;

    @Before
    public void init(){
        player = new Player("1");
        player.setLevel(100);
    }

    @Test
    public void increaseSpeedTest(){
        double oldSpeed = player.getSpeed();
        player.increSpeed(100.0);
        assertEquals(oldSpeed + 75.0, player.getSpeed());
    }

    @Test
    public void increasePowerTest(){
        double oldPower = player.getPower();
        player.increPower(20);
        assertEquals(oldPower + 15, player.getPower());
    }

    @Test
    public void increaseStrengthTest(){
        double oldStrength = player.getStrength();
        player.increStrength(10);
        assertEquals(oldStrength + 7.5, player.getStrength());
    }

    @Test
    public void totalStatsTest(){
        double power = 250;
        double strength = 330;
        double speed = 120;
        double total = (power + speed + strength) * .75 + 3;

        player.increPower(power);
        player.increStrength(strength);
        player.increSpeed(speed);

        assertEquals(total, player.getTotalStats());
    }

    @Test
    public void statGainLevel50Test(){
        player.setLevel(50);
        double expected = .625;

        System.out.println("Lvl 50 stat gain: " + expected);
        assertEquals(expected, player.calcStatGain());
    }

    @Test
    public void statGainLeve200Test(){
        player.setLevel(200);
        double expected = 1.0;

        System.out.println("Lvl 200 stat gain: " + expected);
        assertEquals(expected, player.calcStatGain());
    }

    @Test
    public void statGainLevel300Test(){
        player.setLevel(300);
        double expected = 1.25;

        System.out.println("Lvl 300 stat gain: " + expected);
        assertEquals(expected, player.calcStatGain());
    }

    @Test
    public void statGainLevel400Test(){
        player.setLevel(400);
        double expected = 1.5;

        System.out.println("Lvl 400 stat gain: " + expected);
        assertEquals(expected, player.calcStatGain());
    }

    @Test
    public void statGainLevel500Test(){
        player.setLevel(500);
        double expected = 1.75;

        System.out.println("Lvl 500 stat gain: " + expected);
        assertEquals(expected, player.calcStatGain());
    }

    @Test
    public void statIncreLevel100Test(){
        player.setLevel(100);
        System.out.println("Old Speed " + player.getSpeed());
        player.increSpeed(20);
        System.out.println("New Speed " + player.getSpeed());

        assertEquals(16.0, player.getSpeed());
    }

    @Test
    public void statIncreLevel50Test(){
        player.setLevel(50);
        System.out.println("Old Speed " + player.getSpeed());
        player.increSpeed(20);
        System.out.println("New Speed " + player.getSpeed());

        assertEquals(20 * .625 + 1, player.getSpeed());
    }

    @Test
    public void statIncreLevelTwoTest(){
        player.setLevel(2);
        System.out.println("Old Speed " + player.getSpeed());
        player.increSpeed(20);
        System.out.println("New Speed " + player.getSpeed());

        double expected = 20 * .505 + 1;
        System.out.println(expected);
        assertEquals(expected, player.getSpeed());
    }

    @Test
    public void statIncreLevelThreeTest(){
        player.setLevel(3);
        System.out.println("Old Speed " + player.getSpeed());
        player.increSpeed(20);
        System.out.println("New Speed " + player.getSpeed());

        double expected = (20 * player.calcStatGain()) + 1.0;
        double rounded = player.round(expected);

        System.out.println(rounded);
        assertEquals(rounded, player.getSpeed());
    }
}
