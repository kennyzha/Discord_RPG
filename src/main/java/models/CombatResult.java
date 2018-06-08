package models;

public class CombatResult {
    private StringBuilder combatString;
    private StringBuilder combatResultString;
    private CombatStatistic entityOneStats;
    private CombatStatistic entityTwoStats;
    private boolean winner;

    public CombatResult() {
        this.combatString = new StringBuilder();
        this.combatResultString = new StringBuilder();
        this.entityOneStats = new CombatStatistic();
        this.entityTwoStats = new CombatStatistic();
        this.winner = false;
    }

    public String getCombatString(){
        return combatString.toString();
    }
    public void appendToCombatString(String s){
        combatString.append(s);
    }

    public StringBuilder getCombatResultString() {
        return combatResultString;
    }

    public void appendToCombatResult(String s) {
        combatResultString.append(s);
    }

    public CombatStatistic getEntityOneStats() {
        return entityOneStats;
    }

    public CombatStatistic getEntityTwoStats() {
        return entityTwoStats;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
