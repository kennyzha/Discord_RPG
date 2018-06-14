package models;

public class CombatStatistic {
    private int totalDamageDealt, minDmgDealt, maxDmgDealt, numHitsGiven, roundsPassed;

    public CombatStatistic() {
        this.numHitsGiven = 0;
        this.totalDamageDealt = 0;
        this.roundsPassed = 0;
        this.minDmgDealt = Integer.MAX_VALUE;
        this.maxDmgDealt = 0;
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

    public void increRoundsPassed(int rounds){
        setRoundsPassed(getRoundsPassed() + rounds);
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

    public double calcAvgNumHitsGiven(){
        if(getRoundsPassed() == 0) return 0;

        double percentHits = (double) getNumHitsGiven() / getRoundsPassed() * 100.0;
        double roundedPercentage = Math.round(percentHits * 100.0) / 100.0;
        return roundedPercentage;
    }

    public void increNumHitsGiven() {
        this.numHitsGiven++;
    }

    public int calcAvgDmgDealt(){
        if(getNumHitsGiven() == 0)
            return 0;

        return (int) (getTotalDamageDealt() / getNumHitsGiven());
    }

    public void updateDamageStats(int hitDmg){
        increNumHitsGiven();
        checkMinMaxDmgDealt(hitDmg);
        addToTotalDamage(hitDmg);
    }

    @Override
    public String toString(){
        return String.format("\nLowest damage dealt: %s Highest Damage dealt: %s \n" +
                "Average damage dealt: %s Percentage hits dealt: %s", (getMinDmgDealt() == Integer.MAX_VALUE) ? 0 : getMinDmgDealt(), getMaxDmgDealt(), calcAvgDmgDealt(), calcAvgNumHitsGiven());
    }
}
