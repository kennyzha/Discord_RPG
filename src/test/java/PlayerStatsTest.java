import models.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PlayerStatsTest {
    Player player;

    @Before
    public void init(){
        player = new Player("1");
    }

    @Test
    public void increaseSpeedTest(){
        double oldSpeed = player.getSpeed();
        player.increSpeed(100.0);
        assertEquals(oldSpeed + 100, player.getSpeed());
    }

    @Test
    public void increasePowerTest(){
        double oldPower = player.getPower();
        player.increPower(250);
        assertEquals(oldPower + 250, player.getPower());
    }

    @Test
    public void increaseStrengthTest(){
        double oldStrength = player.getStrength();
        player.increStrength(100.5);
        assertEquals(oldStrength + 100.5, player.getStrength());
    }

    @Test
    public void totalStatsTest(){
        double power = 250;
        double strength = 330.5;
        double speed = 120;
        double total = power + speed + strength;

        player.setPower(power);
        player.setStrength(strength);
        player.setSpeed(speed);

        assertEquals(total, player.getTotalStats());
    }
}
