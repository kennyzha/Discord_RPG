package utils;

import models.Player;

public class Donator {
    public static final long ONE_MONTH = 1000L * 60 * 60 * 24 * 31;

    public static boolean isDonator(Player player){
        return getDonatorTimeMillisecond(player) > 0;
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
            System.out.println("ONE_MONTH = " + ONE_MONTH);
            System.out.println("extension = " + extension);
            addDonatorTime(player, extension);
        }

    }

    public static void removeDonatorPacks(Player player, int numDonatorPacks){
        if(numDonatorPacks > 0 && isDonator(player)){
            long remainingTime = getDonatorTimeMillisecond(player);
            long removeAmount = numDonatorPacks * ONE_MONTH;

            if(remainingTime - removeAmount > 0){
                player.setDonatorEndTime(remainingTime - removeAmount);
            } else{
                player.setDonatorEndTime(System.currentTimeMillis());
            }

        }
    }

    public static long getDonatorTimeMillisecond(Player player){
        if(player != null) {
            return player.getDonatorEndTime() - System.currentTimeMillis();
        }

        return 0;
    }

    public static int getDonatorTimeDays(Player player){
        return (int) (getDonatorTimeMillisecond(player) / (ONE_MONTH / 31));
    }

}
