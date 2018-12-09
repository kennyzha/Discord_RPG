package utils;

import models.Player;

import static utils.TimeUtil.*;

public class Donator {
    public static boolean isDonator(Player player){
        if(player == null){
            return false;
        }

        return millisecondsRemaining(player.getDonatorEndTime(), System.currentTimeMillis()) > 0;
    }

    private static void addDonatorTime(Player player, long milliseconds){
        if(player != null){
            if(isDonator(player)){
                player.setDonatorEndTime(player.getDonatorEndTime() + milliseconds);
            } else {
                player.setDonatorEndTime(System.currentTimeMillis() + milliseconds);
            }
        }
    }

    public static void applyDonatorPacks(Player player, int numDonatorPacks){
        if(player != null && numDonatorPacks > 0){
            long extension = numDonatorPacks * ONE_MONTH;
            addDonatorTime(player, extension);
        }
    }

    public static void removeDonatorPacks(Player player, int numDonatorPacks){
        if(player != null && numDonatorPacks > 0 && isDonator(player)){
            long removeAmount = numDonatorPacks * ONE_MONTH;
            player.setDonatorEndTime(player.getDonatorEndTime() - removeAmount);
        }
    }

    public static int donatorDaysRemaining(Player player){
        return daysRemaining(player.getDonatorEndTime(), System.currentTimeMillis());
    }

    public static int donatorMinutesRemaining(Player player){
        return minutesRemaining(player.getDonatorEndTime(), System.currentTimeMillis()) % 60;
    }

    public static int donatorHoursRemaining(Player player){
        return hoursRemaining(player.getDonatorEndTime(), System.currentTimeMillis()) % 24;
    }

    public static String getDonatorTimeRemainingString(Player player){
        int daysRemaining = donatorDaysRemaining(player);
        int hoursRemaining = donatorHoursRemaining(player);
        int minutesRemaining = donatorMinutesRemaining(player);

        return String.format("Days: %s Hours: %s Minutes: %s", daysRemaining, hoursRemaining, minutesRemaining);
    }
}
