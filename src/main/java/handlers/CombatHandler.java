package handlers;

import com.github.kennedyoliveira.pastebin4j.*;
import config.ApplicationConstants;
import models.CombatResult;
import models.CombatStatistic;
import models.Entity;
import models.Player;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CombatHandler {

    private CombatResult combatResult;
    private CombatStatistic entityOneStats;
    private CombatStatistic entityTwoStats;

    public CombatHandler() {
        this.combatResult = new CombatResult();
        this.entityOneStats = new CombatStatistic();
        this.entityTwoStats = new CombatStatistic();
    }

    public void simulateCombat(Entity p1, Entity p2, MessageChannel channel){
        int curHealth = p1.getHealth();
        int curHealth2 = p2.getHealth();

        double lowSpeed = calcLowSpeed(p1.getSpeed());
        double highSpeed = p1.getSpeed();
        double lowSpeed2 = calcLowSpeed(p2.getSpeed());
        double highSpeed2 = p2.getSpeed();

        for(int roundNumber = 1; roundNumber < 201; roundNumber++){
            if(isFightOver(curHealth, curHealth2)){
                entityOneStats.setRoundsPassed(roundNumber);
                entityTwoStats.setRoundsPassed(roundNumber);
                break;
            }

            double speedRoll = generateRoll(lowSpeed, highSpeed);
            double speedRoll2 = generateRoll(lowSpeed2, highSpeed2);

            if(speedRoll > speedRoll2){
                int hitDmg = calcHitDamage(p1, p2, 0,0);
                curHealth2 = curHealth2 - hitDmg > 0 ? (curHealth2 - hitDmg) : 0;

                entityOneStats.increNumHitsGiven();
                entityOneStats.checkMinMaxDmgDealt(hitDmg);

                combatResult.appendToCombatString(String.format(roundNumber + ". You did %s dmg (%s left)\n", hitDmg, curHealth2));
            } else{
                int hitDmg = calcHitDamage(p2, p1, 0,0);
                curHealth = (curHealth - hitDmg) > 0 ? (curHealth - hitDmg) : 0;

                entityTwoStats.increNumHitsGiven();
                entityTwoStats.checkMinMaxDmgDealt(hitDmg);

                combatResult.appendToCombatString(String.format(roundNumber + ". He did %s dmg (%s left)\n", hitDmg, curHealth));
            }
        }

        if(curHealth > 0 && curHealth2 > 0){
            combatResult.appendToCombatResult(String.format("The fight ended with a draw because 200 rounds have gone by. You have %s health remaining while the enemy has %s health remaining.", curHealth, curHealth2));
        }

        System.out.println("Combat String: \n" + combatResult.getCombatString());
        System.out.println("Combat Result \n" + combatResult.getCombatResultString());
        System.out.println("Combat Statistics: " + entityOneStats.toString());

        //  channel.sendMessage(pasteBinHandler.postContentAsGuest("Discord RPG Fight", content.toString())).queue();
        channel.sendMessage(entityOneStats.toString() + "\n" + combatResult.getCombatResultString()).queue();
    }

    public boolean isFightOver(int health, int health2){
        if(health <= 0){
            combatResult.appendToCombatResult(String.format("The enemy won the fight with %s health left.\n", health2));
            return true;
        } else if(health2 <= 0){
            combatResult.appendToCombatResult(String.format("\n You won the fight with %s health left.\n", health));
            return true;
        }

        return false;
    }

    public int calcHitDamage(Entity p1, Entity p2, double weap, double arm){
        double lowDmg = calcLowDamage(p1.getStrength(), p1.getPower(), weap);
        double highDmg = calcHighDamage(p1.getStrength(), p1.getPower(), weap);

        double dmgRoll = generateRoll(lowDmg, highDmg);

        double lowDef = calcLowDefense(p2.getStrength(), arm);
        double highDef = calcHighDefense(p2.getStrength(), arm);

        double defRoll = generateRoll(lowDef, highDef);

        double hitDmg = Math.max(0, dmgRoll - defRoll);

//        System.out.printf("Player attacked and did %s damage (dmg roll: %s def roll: %s)\n", hitDmg, dmgRoll, defRoll);
        return (int) Math.ceil(hitDmg);
    }

    public double calcLowDamage(double str, double pow, double weap){
        double effectiveStrength = calcEffectiveStr(str);
        double effectivePower = calcEffectivePow(pow);

        return effectiveStrength/6 + effectivePower/2 + weap;
    }

    public double calcHighDamage(double str, double pow, double weap){
        return calcEffectiveStr(str)/4 + 3*calcEffectivePow(pow)/4 + weap;
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
