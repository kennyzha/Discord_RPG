package ItemTests;

import models.Crate;
import models.Item;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CrateTest {
    @Test
    public void itemCrateCostTest(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(1));

        assertEquals(actualCost, 2000);
    }

    @Test
    public void itemCrateCostTest2(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(49));

        assertEquals(actualCost, 2000);
    }

    @Test
    public void itemCrateCostTest3(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(50));

        assertEquals(actualCost, 2000);
    }

    @Test
    public void itemCrateCostTest4(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(100));

        assertEquals(actualCost, 5000);
    }

    @Test
    public void itemCrateCostTest5(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(155));

        assertEquals(actualCost, 20000);
    }
    @Test
    public void itemCrateCostTest6(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(590));

        assertEquals(actualCost, 400000);
    }

    @Test
    public void itemCrateCostTest7(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(10000));

        assertEquals(actualCost, 450000);
    }

    @Test
    public void itemCrateCostTes8(){
        int actualCost = Crate.getCrateCost(Item.getLevelBracket(600));

        assertEquals(actualCost, 450000);
    }
}
