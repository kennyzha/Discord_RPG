package ItemTests;

import models.Item;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ItemRollTest {
    @Test
    public void itemStatRollNormalTest(){

        int level = 88;
        int itemStat = 100;

        assertEquals(itemStat, Item.rollItemStat(level, Item.Rarity.COMMON));
        assertEquals(Item.Rarity.COMMON, Item.getItemRarity(level, itemStat));
    }

    @Test
    public void itemStatRollNormalTest2(){
        int level = 100;
        int itemStat = 1000;
        assertEquals(itemStat, Item.rollItemStat(level, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest3(){
        assertEquals(3000, Item.rollItemStat(177, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest4(){
        assertEquals(50000, Item.rollItemStat(543, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest5(){
        assertEquals(66000, Item.rollItemStat(550, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollBoundsUncommonTest(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 109;
        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.UNCOMMON);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(level, itemRoll));
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 1001 && highest == 1499);
    }

    @Test
    public void itemStatRollBoundsUncommonTest2(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 150;
        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.UNCOMMON);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(level, itemRoll));
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 3001 && highest == 3749);
    }

    @Test
    public void itemStatRollBoundsRareTest(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 109;
        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.RARE);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.RARE, Item.getItemRarity(level, itemRoll));
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 1501 && highest == 1999);
    }

    @Test
    public void itemStatRollBoundsRareTest2(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 58;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.RARE);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.RARE, Item.getItemRarity(level, itemRoll));

        }
        assertTrue(lowest == 326 && highest == 549);
    }

    @Test
    public void itemStatRollBoundsRareTest3(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(532, Item.Rarity.RARE);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        assertTrue(lowest == 54001 && highest == 57999);
    }
    @Test
    public void itemStatRollBoundsEpicTest(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 50;
        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.EPIC, Item.getItemRarity(level, itemRoll));

        }
        assertTrue(lowest == 550 && highest == 999);
    }

    @Test
    public void itemStatRollBoundsEpicTest2(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 103;
        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.EPIC, Item.getItemRarity(level, itemRoll));

        }
        assertTrue(lowest == 2000 && highest == 2999);
    }

    @Test
    public void itemStatRollBoundsEpicTest3(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        int level = 248;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.EPIC, Item.getItemRarity(level, itemRoll));

        }
        assertTrue(lowest == 8000 && highest == 9999);
    }

    @Test
    public void itemStatRollBoundsEpicTest4(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;
        int level = 532;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(level, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);

            assertEquals(Item.Rarity.EPIC, Item.getItemRarity(level, itemRoll));

        }
        assertTrue(lowest == 58000 && highest == 65999);
    }

    @Test
    public void itemStatRollBoundsLegendaryTest(){
        int level = 50;
        int itemRoll = Item.rollItemStat(50, Item.Rarity.LEGENDARY);

        assertTrue(1000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest2(){
        int level = 147;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(3000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest3(){
        int level = 150;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(6000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest4(){
        int level = 151;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(6000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest5(){
        int level = 333;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(21000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest6(){
        int level = 541;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(66000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemRollTest(){
        int common = 0;
        int uncommon = 0;
        int rare = 0;
        int epic = 0;
        int legendary = 0;

        for(int i = 0; i < 100000; i++){
            Item.Rarity rarity = Item.rollItemRarity();

            switch(rarity){
                case COMMON:
                    common++;
                    break;
                case UNCOMMON:
                    uncommon++;
                    break;
                case RARE:
                    rare++;
                    break;
                case EPIC:
                    epic++;
                    break;
                case LEGENDARY:
                    legendary++;
                    break;
            }
        }

        assertEquals(50000, common, 2500);
        assertEquals(30000, uncommon, 1500);
        assertEquals(15000, rare, 750);
        assertEquals(4000, epic, 200);
        assertEquals(1000, legendary, 50);
    }
}
