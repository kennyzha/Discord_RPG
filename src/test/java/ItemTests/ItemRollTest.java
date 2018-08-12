package ItemTests;

import models.Item;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class ItemRollTest {
    @Test
    public void itemStatRollBoundsTest(){
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < 100000; i++){
            int itemRoll = Item.rollItemStat(109);
            lowest = Integer.min(itemRoll, lowest);
            highest = Integer.max(itemRoll, highest);
        }
        System.out.println(lowest + " " + highest);
        assertTrue(lowest == 500 && highest == 1499);
    }
}
