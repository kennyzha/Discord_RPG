package models;

public class Crate {
/*
        public static int[] cost = new int[]{10000, 50000, 150000, 250000, 500000, 1000000, 1500000, 3000000};
        public static int[] cost = new int[]{5000, 5000, 20000, 40000, 75000, 150000, 300000, 500000, 750000, 100000;
        public static int[] cost = new int[]{5000, 5000, 20000, 40000,          // 0 , 50, 100, 150
                                            75000, 150000, 300000, 600000,      // 200, 250, 300, 350
                                            800000, 1000000, 1000000, 1000000,  // 400, 450, 500, 550
                                            1000000, 1000000, 1000000, 1000000,
                                            1000000, 1000000, 1000000};
    */
    private static int[] cost = new int[]{
            2000, 2000, 5000, 20000,            // 0 , 50, 100, 150
            50000, 75000, 100000, 150000,       // 200, 250, 300, 350
            200000, 300000, 350000, 400000,     // 400, 450, 500, 550
            450000, 450000, 450000, 450000};    // 600, 650, 700, 750

    public static int openCrate(int playerLevel){
        Item.Rarity rarity = Item.rollItemRarity();
        return Item.rollItemStat(playerLevel, rarity);
    }

    public static int getCrateCost(int playerLevelBracket){
        if(playerLevelBracket > cost.length || playerLevelBracket < 0){
            return cost[cost.length - 1];
        }

        return cost[playerLevelBracket];
    }

}
