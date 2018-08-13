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
        int itemStat = 50;

        assertEquals(itemStat, Item.rollItemStat(level, Item.Rarity.COMMON));
        assertEquals(Item.Rarity.COMMON, Item.getItemRarity(level, itemStat));
    }

    @Test
    public void itemStatRollNormalTest2(){
        int level = 100;
        int itemStat = 500;
        assertEquals(itemStat, Item.rollItemStat(level, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest3(){
        assertEquals(1500, Item.rollItemStat(177, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest4(){
        assertEquals(25000, Item.rollItemStat(543, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest5(){
        assertEquals(33000, Item.rollItemStat(550, Item.Rarity.COMMON));
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
        assertTrue(lowest == 501 && highest == 999);
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
        assertTrue(lowest == 51 && highest == 274);
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
        assertTrue(lowest == 25001 && highest == 28999);
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
        assertTrue(lowest == 275 && highest == 499);
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
        assertTrue(lowest == 1000 && highest == 1499);
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
        assertTrue(lowest == 4000 && highest == 4999);
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
        assertTrue(lowest == 29000 && highest == 32999);
    }

    @Test
    public void itemStatRollBoundsLegendaryTest(){
        int level = 50;
        int itemRoll = Item.rollItemStat(50, Item.Rarity.LEGENDARY);

        assertTrue(500 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest2(){
        int level = 147;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(1500 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest3(){
        int level = 150;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(3000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest4(){
        int level = 151;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(3000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest5(){
        int level = 333;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(10500 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest6(){
        int level = 541;
        int itemRoll = Item.rollItemStat(level, Item.Rarity.LEGENDARY);

        assertTrue(33000 == itemRoll);
        assertTrue(Item.Rarity.LEGENDARY == Item.getItemRarity(level , itemRoll));
    }
}
