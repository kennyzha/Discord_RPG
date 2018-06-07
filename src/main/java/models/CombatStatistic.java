package models;

public class CombatStatistic {
    private int totalDamageDealt, minDmgDealt, maxDmgDealt, numHitsGiven, roundsPassed;

    public CombatStatistic() {
        this.numHitsGiven = 0;
        this.totalDamageDealt = 0;
        this.roundsPassed = 1;
        this.minDmgDealt = Integer.MAX_VALUE;
        this.maxDmgDealt = Integer.MIN_VALUE;
    }

    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void addToTotalDamage(int dmg) {
        this.totalDamageDealt += dmg;
    }

    public int getMinDmgDealt() {
        return minDmgDealt;
    }

    public void setMinDmgDealt(int minDmgDealt) {
        this.minDmgDealt = minDmgDealt;
    }

    public int getMaxDmgDealt() {
        return maxDmgDealt;
    }

    public void setMaxDmgDealt(int maxDmgDealt) {
        this.maxDmgDealt = maxDmgDealt;
    }

    public int getRoundsPassed() {
        return roundsPassed;
    }

    public void setRoundsPassed(int roundsPassed) {
        this.roundsPassed = roundsPassed;
    }

    public void checkMinMaxDmgDealt(int dmg){
        if(getMinDmgDealt() > dmg){
            setMinDmgDealt(dmg);
        }

        if(getMaxDmgDealt() < dmg){
            setMaxDmgDealt(dmg);
        }
    }

    public int getNumHitsGiven() {
        return numHitsGiven;
    }

    public void increNumHitsGiven() {
        this.numHitsGiven++;
    }

    public double calcAvgDmgDealt(){
        return getTotalDamageDealt() / getRoundsPassed();
    }

    @Override
    public String toString(){
        return String.format("Lowest damage dealt: %s Highest Damage dealt: %s \n" +
                "Average damage dealt: %s Number of hits dealt: %s", getMinDmgDealt(), getMaxDmgDealt(), calcAvgDmgDealt(), getNumHitsGiven());
    }
}
