package models;

import java.time.LocalDateTime;

public class Donator {

    public static boolean donatorStatusIsVaild(Player player){
        if(!player.isDonator()){
            return false;
        }

        LocalDateTime donatatorExpiratation = LocalDateTime.parse(player.getDonatorEndDate());

        if(dateIsValid()){
            return true;
        }

        return false;
    }

    private static boolean dateIsValid() {
        return true;
    }
}
