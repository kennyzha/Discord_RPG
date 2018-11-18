package DonatorTests;

import utils.Donator;
import models.Player;
import org.junit.Test;
import utils.TimeUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.TimeUtil.*;

public class DonatorTest {
    @Test
    public void isNotADonatorTest(){
        Player player = new Player("abc");
        assertEquals(false, Donator.isDonator(player));
    }

    @Test
    public void isNotADonatorTest2(){
        Player player = new Player("abc");
        player.setDonatorEndTime(System.currentTimeMillis() - 1);
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
    public void isADonatorTest2(){
        Player player = new Player("abc");

        player.setDonatorEndTime(System.currentTimeMillis() + ONE_MINUTE);

        assertEquals(true, Donator.isDonator(player));
    }

    @Test
    public void donatorDaysTest(){
        Player player = new Player("abc");
        Donator.applyDonatorPacks(player, 1);

        System.out.println(Donator.isDonator(player));
        assertEquals(31, Donator.donatorDaysRemaining(player), 1);
    }

    @Test
    public void donatorDaysTest2(){
        Player player = new Player("abc");
        int numDonatorPacks = 3;
        Donator.applyDonatorPacks(player, numDonatorPacks);

        System.out.println(Donator.isDonator(player));
        assertEquals(93, Donator.donatorDaysRemaining(player), 1);
    }

    @Test
    public void donatorDaysTest3(){
        Player player = new Player("abc");

        long threeDays = ONE_DAY * 4 - 1;
        player.setDonatorEndTime(System.currentTimeMillis() + threeDays);

        assertEquals(3, Donator.donatorDaysRemaining(player));
    }

    @Test
    public void donatorMinutesTest(){
        Player player = new Player("abc");

        long fiftyMinutes = ONE_MINUTE * 51 - 1;
        player.setDonatorEndTime(System.currentTimeMillis() + fiftyMinutes);

        assertEquals(0, Donator.donatorDaysRemaining(player));
        assertEquals(0, Donator.donatorHoursRemaining(player));
        assertEquals(50, Donator.donatorMinutesRemaining(player));
    }

    @Test
    public void donatorMinutesTest2(){
        Player player = new Player("abc");

        long oneDay = ONE_DAY - 1;
        player.setDonatorEndTime(System.currentTimeMillis() + oneDay);

        assertEquals(0, Donator.donatorDaysRemaining(player));
        assertEquals(23, Donator.donatorHoursRemaining(player));
        assertEquals(59, Donator.donatorMinutesRemaining(player));
    }

    @Test
    public void donatorDaysAlmostExpiredTest(){
        Player player = new Player("abc");

        long oneDay = ONE_DAY * 2 - 1;
        player.setDonatorEndTime(System.currentTimeMillis() + oneDay);

        assertEquals(1, Donator.donatorDaysRemaining(player));
    }

    @Test
    public void donatorDaysExpiredTest(){
        Player player = new Player("abc");

        long threeDays = ONE_DAY * 3;
        player.setDonatorEndTime(System.currentTimeMillis() - threeDays);

        assertTrue(!Donator.isDonator(player));
        assertTrue(Donator.donatorDaysRemaining(player) <= 0);
    }

    @Test
    public void donatorDaysExpiredTest2(){
        Player player = new Player("abc");
        player.setDonatorEndTime(System.currentTimeMillis());

        assertTrue(!Donator.isDonator(player));
        assertTrue(Donator.donatorDaysRemaining(player) <= 0);
    }

    @Test
    public void removeDonatorPackTest(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 2);
        Donator.removeDonatorPacks(player, 2);

        assertTrue(!Donator.isDonator(player));
    }

    @Test
    public void removeDonatorPackTest2(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 2);
        Donator.removeDonatorPacks(player, 1);

        player.setDonatorEndTime(player.getDonatorEndTime() - 1);
        assertEquals(30, Donator.donatorDaysRemaining(player));
        assertEquals(23, Donator.donatorHoursRemaining(player));
        assertEquals(59 , Donator.donatorMinutesRemaining(player));

        assertTrue(Donator.isDonator(player));
    }

    @Test
    public void donatorTimeRemainingStringTest(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 1);
        player.setDonatorEndTime(player.getDonatorEndTime() - 1);
        String expected = "Days: 30 Hours: 23 Minutes: 59";

        System.out.println(Donator.getDonatorTimeRemainingString(player));
        assertTrue(expected.equals(Donator.getDonatorTimeRemainingString(player)));
    }

    @Test
    public void donatorTimeRemainingStringTest2(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 1);
        player.setDonatorEndTime(player.getDonatorEndTime() - ONE_DAY - 1);
        String expected = "Days: 29 Hours: 23 Minutes: 59";

        System.out.println(Donator.getDonatorTimeRemainingString(player));
        assertTrue(expected.equals(Donator.getDonatorTimeRemainingString(player)));
    }

    @Test
    public void donatorTimeRemainingStringTest3(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 1);
        player.setDonatorEndTime(player.getDonatorEndTime() - (ONE_DAY * 21) - (ONE_HOUR * 5) - (ONE_MINUTE * 34) - 1);
        String expected = "Days: 9 Hours: 18 Minutes: 25";

        System.out.println(Donator.getDonatorTimeRemainingString(player));
        assertTrue(expected.equals(Donator.getDonatorTimeRemainingString(player)));
    }

    @Test
    public void donatorTimeRemainingStringTest4(){
        Player player = new Player("Abc");
        Donator.applyDonatorPacks(player, 2);
        player.setDonatorEndTime(player.getDonatorEndTime() - (ONE_DAY * 21) - (ONE_HOUR * 5) - (ONE_MINUTE * 34) - 1);
        String expected = "Days: 40 Hours: 18 Minutes: 25";

        System.out.println(Donator.getDonatorTimeRemainingString(player));
        assertTrue(expected.equals(Donator.getDonatorTimeRemainingString(player)));
    }
}
