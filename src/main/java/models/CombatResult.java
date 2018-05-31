package models;

public class CombatResult {
    private StringBuilder combatString;
    private StringBuilder combatResultString;

    public CombatResult() {
        this.combatString = new StringBuilder();
        this.combatResultString = new StringBuilder();
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
}
