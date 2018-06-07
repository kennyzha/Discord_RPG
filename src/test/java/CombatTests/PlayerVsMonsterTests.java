package CombatTests;

import models.Player;
import org.junit.Before;

public class PlayerVsMonster {
    Player player;

    @Before
    public void init(){
        player = new Player("1");
        player.setHealth(250);
        player.setStrength(100);
        player.setPower(100);
        player.setSpeed(100);
    }
}
