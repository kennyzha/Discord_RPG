package models;

public class Item {
    public enum Type {WEAPON, SHIELD, BODY, HELMET, GLOVES, BOOTS, LEG, CONSUMABLE};
    public enum Rarity{COMMON, RARE, EPIC, LEGENDARY, MYTH};

    private String name;
    private int statValue;
    private int sellValue;
    private Type itemType;
    private Rarity rarity;

    public Item(String name, int statValue, Type itemType, Rarity rarity, int sellValue) {
        this.name = name;
        this.statValue = statValue;
        this.itemType = itemType;
        this.rarity = rarity;
        this.sellValue = sellValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatValue() {
        return statValue;
    }

    public void setStatValue(int statValue) {
        this.statValue = statValue;
    }

    public Type getItemType() {
        return itemType;
    }

    public void setItemType(Type itemType) {
        this.itemType = itemType;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public int getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public double getDropRate(){
        switch(getRarity()){
            case COMMON:
                return .15;
            case RARE:
                return .05;
             case EPIC:
                return .01;
            case LEGENDARY:
                return .005;
            case MYTH:
                return 0;
        }
        return 0;
    }

    public static int rollItemStat(int level){
        int lowerBoundStat = getLowerBoundStat(level);
        int upperBoundStat = getUpperBoundStat(level);

        int diff = upperBoundStat - lowerBoundStat;

        return (int) (Math.random() * diff) + lowerBoundStat;
    }

    public static int getLowerBoundStat(int level){
        return getLowerBoundLevel(level) * getLevelMultiplier(level - 50);
    }

    public static int getUpperBoundStat(int level){
        return getUpperBoundLevel(level) * getLevelMultiplier(level);
    }

    public static int getUpperBoundLevel(int level){
        return getLevelBracket(level + 50) * 50;
    }

    public static int getLowerBoundLevel(int level){
        return getLevelBracket(level) * 50;
    }

    public static int getLevelMultiplier(int level){
        int levelBracket = getLevelBracket(level);

        if(levelBracket == 0){
            return 1;
        } else if(levelBracket >= 9){
            levelBracket += (levelBracket - 8);
        }

        int levelMultiplier = levelBracket * 5;

        return levelMultiplier;
    }

    public static int getLevelBracket(int level){
        return level/50;
    }

    public static Rarity rollItemRarity(){
        int roll = generateNumber();

        if(roll >= 50){
            return Rarity.COMMON;
        } else if(roll >= 20){
            return Rarity.RARE;
        } else if(roll > 1){
            return Rarity.EPIC;
        } else{
            return Rarity.LEGENDARY;
        }
    }

    public static int generateNumber(){
        return (int) (Math.random() * 100) + 1;
    }
}
