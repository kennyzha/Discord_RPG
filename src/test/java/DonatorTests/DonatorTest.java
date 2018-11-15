package DonatorTests;

import utils.Donator;
import models.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DonatorTest {
    @Test
    public void isNotADonatorTest(){
        Player player = new Player("abc");
        assertEquals(false, Donator.isDonator(player));
    }

    @Test
    public void isADonatorTest(){
        Player player = new Player("abc");

        System.out.println(player.getDonatorEndTime());
        Donator.applyDonatorPacks(player, 1);
        System.out.println(player.getDonatorEndTime());

        assertEquals(true, Donator.isDonator(player));
    }

    @Test
    public void donatorDaysTest(){
        Player player = new Player("abc");
        Donator.applyDonatorPacks(player, 1);

        System.out.println(Donator.isDonator(player));
        assertEquals(31, Donator.getDonatorTimeDays(player), 1);
    }

    @Test
    public void donatorDaysTest2(){
        Player player = new Player("abc");
        int numDonatorPacks = 3;
        Donator.applyDonatorPacks(player, 3);

        System.out.println(Donator.isDonator(player));
        assertEquals(93, Donator.getDonatorTimeDays(player), 1);
    }

    @Test
    public void donatorDaysTest3(){
        Player player = new Player("abc");

        long threeDays = 1000 * 60 * 60 * 24 * 9;
        player.setDonatorEndTime(System.currentTimeMillis() + threeDays);

        assertEquals(9, Donator.getDonatorTimeDays(player), 1);
    }

    @Test
    public void donatorDaysExpiredTest(){
        Player player = new Player("abc");

        long threeDays = 1000 * 60 * 60 * 24 * 9;
        player.setDonatorEndTime(System.currentTimeMillis() - threeDays);

        assertTrue(Donator.getDonatorTimeDays(player) <= 0);
    }

    @Test
    public void donatorDaysExpiredTest2(){
        Player player = new Player("abc");
        player.setDonatorEndTime(System.currentTimeMillis());

        assertTrue(Donator.getDonatorTimeDays(player) <= 0);
    }
}
