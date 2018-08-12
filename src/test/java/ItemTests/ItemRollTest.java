package ItemTests;

import models.Item;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ItemRollTest {
    @Test
    public void itemStatRollNormalTest(){
        assertEquals(50, Item.rollItemStat(88, Item.Rarity.COMMON));
    }

    @Test
    public void itemStatRollNormalTest2(){
        assertEquals(500, Item.rollItemStat(100, Item.Rarity.COMMON));
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

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(109, Item.Rarity.RARE);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 500 && highest == 999);
    }

    @Test
    public void itemStatRollBoundsRareTest2(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(58, Item.Rarity.RARE);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 50 && highest == 274);
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
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 25000 && highest == 28999);
    }
    @Test
    public void itemStatRollBoundsEpicTest(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(50, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 275 && highest == 499);
    }

    @Test
    public void itemStatRollBoundsEpicTest2(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(103, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 1000 && highest == 1499);
    }

    @Test
    public void itemStatRollBoundsEpicTest3(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(248, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 4000 && highest == 4999);
    }

    @Test
    public void itemStatRollBoundsEpicTest4(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(532, Item.Rarity.EPIC);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 29000 && highest == 32999);
    }

    @Test
    public void itemStatRollBoundsLegendaryTest(){
        assertTrue(500 == Item.rollItemStat(50, Item.Rarity.LEGENDARY));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest2(){
        assertTrue(1500 == Item.rollItemStat(147, Item.Rarity.LEGENDARY));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest3(){
        assertTrue(3000 == Item.rollItemStat(150, Item.Rarity.LEGENDARY));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest4(){
        assertTrue(3000 == Item.rollItemStat(151, Item.Rarity.LEGENDARY));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest5(){
        assertTrue(10500 == Item.rollItemStat(333, Item.Rarity.LEGENDARY));
    }

    @Test
    public void itemStatRollBoundsLegendaryTest6(){
        assertTrue(33000 == Item.rollItemStat(541, Item.Rarity.LEGENDARY));
    }
}
