package handlers;

import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CombatHandler {

    public Player simulateCombat(Player p1, Player p2, MessageChannel channel){
        int startingHealth = p1.getHealth();
        int startingHealth2 = p2.getHealth();

        double lowSpeed = calcLowSpeed(p1.getSpeed());
        double highSpeed = p1.getSpeed();
        double lowSpeed2 = calcLowSpeed(p2.getSpeed());
        double highSpeed2 = p2.getSpeed();

        for(int i = 0; i < 20; i++){
            double speedRoll = generateRoll(lowSpeed, highSpeed);
            double speedRoll2 = generateRoll(lowSpeed2, highSpeed2);

            if(speedRoll > speedRoll2){
                System.out.print("[1]");
                calcHitDamage(p1, p2, 0,0);
            } else{
                System.out.print("[2]");
                calcHitDamage(p2, p1, 0,0);
            }
        }

        return p1;
    }

    public double calcHitDamage(Player p1, Player p2, double wep, double arm){
        double lowDmg = calcLowDamage(p1.getStrength(), p1.getPower(), 0);
        double highDmg = calcHighDamage(p1.getStrength(), p1.getPower(), 0);

        double dmgRoll = generateRoll(lowDmg, highDmg);

        double lowDef = calcLowDefense(p2.getStrength(), 0);
        double highDef = calcHighDefense(p2.getStrength(), 0);

        double defRoll = generateRoll(lowDef, highDef);

        double hitDmg = Math.max(0, dmgRoll - defRoll);

        System.out.printf("Player attacked and did %s damage (dmg roll: %s def roll: %s)\n", hitDmg, dmgRoll, defRoll);
        return hitDmg;
    }

    public double calcLowDamage(double str, double pow, double wep){
        double effectiveStrength = calcEffectiveStr(str);
        double effectivePower = calcEffectivePow(pow);

        return effectiveStrength/6 + effectivePower/2 + wep;
    }

    public double calcHighDamage(double str, double pow, double wep){
        return calcEffectiveStr(str)/4 + 3*calcEffectivePow(pow)/4 + wep;
    }

    public double calcLowDefense(double str, double armor){
        return (calcEffectiveStr(str)/3 + armor);
    }

    public double calcHighDefense(double str, double armor){
        return (calcEffectiveStr(str)/2 + armor);
    }

    public double calcLowSpeed(double speed){
        return calcEffectiveSpd(speed)/4;
    }

    public double calcEffectiveStr(double str){
        return (str >= 500 ? ( 500 + (str - 500) * .8) : str );
    }

    public double calcEffectivePow(double pow){
        return (pow >= 500 ? ( 500 + (pow - 500) * .8) : pow );
    }

    public double calcEffectiveSpd(double spd){
        return (spd >= 500 ? ( 500 + (spd - 500) * .8) : spd );
    }

    public double generateRoll(double lowerBound, double upperBound){
        return (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }
}
