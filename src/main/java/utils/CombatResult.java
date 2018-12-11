package utils;

public class CombatResult {

    public enum Result{WIN, LOST, DRAW}
    private StringBuilder combatString;
    private StringBuilder combatResultString;
    private CombatStatistic entityOneStats;
    private CombatStatistic entityTwoStats;
    private Result result;

    public CombatResult() {
        this.combatString = new StringBuilder();
        this.combatResultString = new StringBuilder();
        this.entityOneStats = new CombatStatistic();
        this.entityTwoStats = new CombatStatistic();
        this.result = null;
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
        return this.result == Result.WIN;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult(){ return this.result; }
}
