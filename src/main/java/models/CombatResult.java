package models;

public class CombatResult {
    private StringBuilder combatString;
    private StringBuilder combatResultString;
    private CombatStatistic entityOneStats;
    private CombatStatistic entityTwoStats;

    public CombatResult() {
        this.combatString = new StringBuilder();
        this.combatResultString = new StringBuilder();
        this.entityOneStats = new CombatStatistic();
        this.entityTwoStats = new CombatStatistic();

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
}
