package handlers;

import models.*;
import net.dv8tion.jda.core.entities.MessageChannel;
import utils.CombatStatistic;
import utils.CombatResult;

import java.text.DecimalFormat;

public class CombatHandler {

    private CombatResult combatResult;
    private DecimalFormat format;

    public CombatHandler() {
        this.combatResult = new CombatResult();
        this.format = new DecimalFormat("#,###.##");
    }

    public CombatResult fightMonster(Player player, Monster monster, int numTimes){
        int totalExpEarned = 0;
        int totalGoldEarned = 0;
        int numWins = 0;

        for(int i = 0; i < numTimes; i++){
            simulateCombat(player, monster, null);

            if(combatResult.isWinner()){
                numWins++;
                int expEarned = monster.calcExpGiven();

                totalExpEarned += expEarned;
                totalGoldEarned += monster.calcGoldDropped();

                combatResult.setWinner(false);
            }
        }

        combatResult.appendToCombatResult(String.format("\nYou won %d/%d times and gained %s exp and %s gold.", numWins, numTimes, format.format(totalExpEarned), format.format(totalGoldEarned)));

        player.increGold(totalGoldEarned);
        playerLevelUp(player, totalExpEarned);
        return combatResult;
    }

    public CombatResult fightPlayer(Player player, Player enemyPlayer){
        simulateCombat(player, enemyPlayer, null);
        return combatResult;
    }

    public void simulateCombat(Entity p1, Entity p2, MessageChannel channel){
        int curHealth = p1.getHealth();
        int curHealth2 = p2.getHealth();
        double lowSpeed = calcLowSpeed(p1.getSpeed());
        double lowSpeed2 = calcLowSpeed(p2.getSpeed());

        CombatStatistic entityOneStats = combatResult.getEntityOneStats();
        CombatStatistic entityTwoStats = combatResult.getEntityTwoStats();

        for(int roundNumber = 1; roundNumber < 201; roundNumber++){
            if(isFightOver(curHealth, curHealth2, roundNumber - 1)){
                break;
            }

            double speedRoll = generateRoll(lowSpeed, p1.getSpeed());
            double speedRoll2 = generateRoll(lowSpeed2, p2.getSpeed());

            if(speedRoll > speedRoll2){
                int hitDmg = calcHitDamage(p1, p2, p1.getWeapon(),p2.getArmor());
                curHealth2 = curHealth2 - hitDmg > 0 ? (curHealth2 - hitDmg) : 0;

                entityOneStats.updateDamageStats(hitDmg);
                combatResult.appendToCombatString(String.format(roundNumber + ". You did %s dmg (%s left)\n", format.format(hitDmg), format.format(curHealth2)));
            } else{
                int hitDmg = calcHitDamage(p2, p1, p2.getWeapon(),p1.getArmor());
                curHealth = (curHealth - hitDmg) > 0 ? (curHealth - hitDmg) : 0;

                entityTwoStats.updateDamageStats(hitDmg);
                combatResult.appendToCombatString(String.format(roundNumber + ". He did %s dmg (%s left)\n", format.format(hitDmg), format.format(curHealth)));
            }

            entityOneStats.increRoundsPassed(1);
            entityTwoStats.increRoundsPassed(1);
        }

        if(curHealth > 0 && curHealth2 > 0){
            combatResult.appendToCombatResult(String.format("The fight ended with a draw because 200 rounds have gone by. You have %s health remaining while the enemy has %s health remaining.\n", format.format(curHealth), format.format(curHealth2)));
            combatResult.setWinner(false);
        }
    }

    public void playerLevelUp(Player player, int totalExpEarned){
        int oldPlayerLevel = player.getLevel();
        int oldHealth = player.getHealth();

        player.increExp(totalExpEarned);
        player.updateLevelAndExp();

        int lvlsGained = player.getLevel() - oldPlayerLevel;
        int healthGained = player.getHealth() - oldHealth;

        if(lvlsGained == 1){
            combatResult.appendToCombatResult( String.format(" You have gained a level and %s health. You are now level %s.",healthGained, player.getLevel()));
        } else if(lvlsGained > 1){
            combatResult.appendToCombatResult( String.format(" You have gained %s levels and %s health. You are now level %s.", lvlsGained, healthGained, player.getLevel()));
        }
    }

    public boolean isFightOver(int health, int health2, int round){
        if(health <= 0){
            combatResult.appendToCombatResult(String.format("The enemy won the fight with %s health left on round %s.\n", format.format(health2), round));
            combatResult.setWinner(false);
            return true;
        } else if(health2 <= 0){
            combatResult.appendToCombatResult(String.format("You won the fight with %s health left on round %s.\n", format.format(health), round));
            combatResult.setWinner(true);
            return true;
        }

        return false;
    }

    public int calcHitDamage(Entity p1, Entity p2, double weap, double arm){
        double lowDmg = calcLowDamage(p1.getStrength(), p1.getPower(), weap, arm);
        double highDmg = calcHighDamage(p1.getStrength(), p1.getPower(), weap, arm);

        double dmgRoll = generateRoll(lowDmg, highDmg);

        double lowDef = calcLowDefense(p2.getStrength(), arm);
        double highDef = calcHighDefense(p2.getStrength(), arm);

        double defRoll = generateRoll(lowDef, highDef);

        double hitDmg = Math.max(0, dmgRoll - defRoll);

        return (int) Math.ceil(hitDmg);
    }

    public double calcLowDamage(double str, double pow, double weap, double arm){
        double effectiveStrength = calcEffectiveStr(str + arm/4);
        double effectivePower = calcEffectivePow(pow + weap/4);

        return effectiveStrength/6 + effectivePower/2;
    }

    public double calcHighDamage(double str, double pow, double weap, double arm){
        return calcEffectiveStr(str + arm/4)/4 + 3*calcEffectivePow(pow + weap/4)/4;
    }

    public double calcLowDefense(double str, double armor){
        return (calcEffectiveStr(str + armor/4)/3 );
    }

    public double calcHighDefense(double str, double armor){
        return (calcEffectiveStr(str + armor/4)/2);
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
        return (Math.random() * (upperBound - lowerBound) + 1) + lowerBound;
    }
}
