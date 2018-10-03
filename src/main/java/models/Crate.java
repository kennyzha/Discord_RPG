package models;

public class Crate {
//    public static int[] cost = new int[]{10000, 50000, 150000, 250000, 500000, 1000000, 1500000, 3000000};
//    public static int[] cost = new int[]{5000, 5000, 20000, 40000, 75000, 150000, 300000, 500000, 750000, 1000000};
    public static int[] cost = new int[]{5000, 5000, 20000, 40000, 75000, 150000, 300000, 600000, 800000, 1000000,
        1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000};


    public static int openCrate(int playerLevel){
        Item.Rarity rarity = Item.rollItemRarity();
        return Item.rollItemStat(playerLevel, rarity);
    }

}
