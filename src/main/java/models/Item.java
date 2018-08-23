package models;

import java.util.Random;

public class Item {
    public enum Type {WEAPON, ARMOR, CONSUMABLE};
    public enum Rarity{COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTH};

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

    public static int rollItemStat(int level, Rarity rarity){
        int lowerBoundStat = getLowerBoundStat(level);
        int upperBoundStat = getUpperBoundStat(level);
        int diff = upperBoundStat - lowerBoundStat;

        switch(rarity){
            case COMMON:
                return lowerBoundStat;
            case UNCOMMON:
                diff /= 4;
                diff--;
                break;
            case RARE:
                diff /= 4;
                lowerBoundStat += diff;
                diff--;
                break;
            case EPIC:
                diff /= 2;
                lowerBoundStat += diff;
                lowerBoundStat--;
                break;
            case LEGENDARY:
                return upperBoundStat;
        }
        return (int) (Math.random() * diff) + lowerBoundStat + 1;
    }

    public static Rarity getItemRarity(int level, int statValue){
        int lowerBound = getLowerBoundStat(level);
        int upperBound = getUpperBoundStat(level);
        int diff = upperBound - lowerBound;

        if(statValue < lowerBound || statValue > upperBound){
            return null;
        }

        if(statValue == lowerBound){
            return Rarity.COMMON;
        } else if(statValue == upperBound){
            return Rarity.LEGENDARY;
        } else if (statValue >= lowerBound + diff/2){
            return Rarity.EPIC;
        } else if(statValue >= lowerBound + diff/4){
            return Rarity.RARE;
        } else{
            return Rarity.UNCOMMON;
        }
    }

    public static int getLowerBoundStat(int level){
        return getLowerBoundLevel(level) * getLevelMultiplier(level - 50) * 2;
    }

    public static int getUpperBoundStat(int level){
        return getUpperBoundLevel(level) * getLevelMultiplier(level) * 2;
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

        if(roll > 45){
            return Rarity.COMMON;
        } else if(roll > 15){
            return Rarity.RARE;
        } else if(roll > 3){
            return Rarity.EPIC;
        } else{
            return Rarity.LEGENDARY;
        }
    }

    public static int generateNumber(){
        return (int) (Math.random() * 100) + 1;
    }
}
