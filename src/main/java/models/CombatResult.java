package models;

public class CombatResult {
    private StringBuilder combatString;
    private String combatResult;

    private int totalDamageDealt, minDmgDealt, maxDmgDealt, numHitsGiven, healthRemaining;
    private int totalDamageDealt2, minDmgDealt2, maxDmgDealt2, numHitsGiven2, healthRemaining2;
    private int roundsPassed;

    public CombatResult() {
        this.combatString = new StringBuilder();
        this.combatResult = "";
    }

    public String getCombatString(){
        return combatString.toString();
    }
    public void appendToCombatString(String s){
        combatString.append(s);
    }

    public String getCombatResult() {
        return combatResult;
    }

    public void setCombatResult(String combatResult) {
        this.combatResult = combatResult;
    }

    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public int getMinDmgDealt() {
        return minDmgDealt;
    }

    public void setMinDmgDealt(int dmg) {
        this.minDmgDealt = minDmgDealt < dmg ? minDmgDealt : dmg;
    }

    public int getMaxDmgDealt() {
        return maxDmgDealt;
    }

    public void setMaxDmgDealt(int dmg) {
        this.maxDmgDealt = maxDmgDealt > dmg ? maxDmgDealt : dmg;
    }

    public int getNumHitsGiven() {
        return numHitsGiven;
    }

    public void setNumHitsGiven(int numHitsGiven) {
        this.numHitsGiven = numHitsGiven;
    }

    public int getHealthRemaining() {
        return healthRemaining;
    }

    public void setHealthRemaining(int healthRemaining) {
        this.healthRemaining = healthRemaining;
    }

    public int getTotalDamageDealt2() {
        return totalDamageDealt2;
    }

    public void incTotalDamageDealt2(int dmg) {
        this.totalDamageDealt2 = totalDamageDealt2 + dmg;
    }

    public int getMinDmgDealt2() {
        return minDmgDealt2;
    }

    public void setMinDmgDealt2(int dmg) {
        this.minDmgDealt2 = minDmgDealt2 < dmg ? minDmgDealt2 : dmg;
    }

    public int getMaxDmgDealt2() {
        return maxDmgDealt2;
    }

    public void setMaxDmgDealt2(int dmg) {
        this.maxDmgDealt2 = maxDmgDealt2 > dmg ? maxDmgDealt : dmg;
    }

    public int getNumHitsGiven2() {
        return numHitsGiven2;
    }

    public void setNumHitsGiven2(int numHitsGiven2) {
        this.numHitsGiven2 = numHitsGiven2;
    }

    public int getHealthRemaining2() {
        return healthRemaining2;
    }

    public void setHealthRemaining2(int healthRemaining2) {
        this.healthRemaining2 = healthRemaining2;
    }

    public int getRoundsPassed() {
        return roundsPassed;
    }

    public void setRoundsPassed(int roundsPassed) {
        this.roundsPassed = roundsPassed;
    }

    public void incRoundsPassed(){
        setRoundsPassed(getRoundsPassed() + 1);
    }

    public double calcAvgDmgDealt(){
        return getTotalDamageDealt() / getRoundsPassed();
    }

    public double calcAvgDmgDealt2(){
        return getTotalDamageDealt2() / getRoundsPassed();
    }

    @Override
    public String toString(){
        return String.format("Your stats: \n" +
                            "Health remaining: %s Lowest damage dealt: %s Highest damage dealt: %s \n" +
                            "Average Damage: %s Total Hits Given: %s \n" +
                            "Enemy's stats: \n" +
                            "Health remaining: %s Lowest damage dealt: %s Highest damage dealt: %s \n" +
                            "Average Damage: %s Total Hits Given: %s",
                            healthRemaining, getMinDmgDealt(), getMaxDmgDealt(), calcAvgDmgDealt(), getNumHitsGiven(),
                            healthRemaining2, getMinDmgDealt2(), maxDmgDealt2, calcAvgDmgDealt2(), getNumHitsGiven2());
    }
}
