package ItemTests;


import models.Item;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ItemTest {

    @Test
    public void generateRollTest(){
        for(int i = 0; i < 10000; i++){
            int roll = Item.generateNumber();
            assertTrue(roll > 0 && roll <= 100);
        }
    }
}
