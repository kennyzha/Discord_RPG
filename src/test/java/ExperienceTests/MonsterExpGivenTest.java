package ExperienceTests;

import models.Monster;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class MonsterExpGivenTest {

    public void varianceTest(int baseExp, double variance){
        Monster testMonster = new Monster("test", 1, 1, 1, 1, 1, baseExp, 1, new ArrayList<>());

        final int NUM_TIMES = 1000;
        int maxValue = (int) (baseExp * variance);
        int curVar = 0;
        boolean isInRange = true;

        for(int i = 0; i < NUM_TIMES; i ++){
            curVar = testMonster.calcVariance(testMonster.getBaseExpGiven(), variance);

            if(curVar < 0 || curVar > maxValue){
                isInRange = false;
                break;
            }
        }

        assertTrue(String.format("Lower bound is expected to be %s and upper bound is expected to be %s but actual variance is %s", 0, maxValue, curVar), isInRange);
    }

    @Test
    public void varianceTest1(){
        varianceTest(50, .1);
    }

    @Test
    public void varianceTest2(){
        varianceTest(200, .1);
    }

    @Test
    public void varianceTest3(){
        varianceTest(5555, .1);
    }

    @Test
    public void varianceTest4(){
        varianceTest(62000, .25);
    }

    @Test
    public void varianceTest5(){
        varianceTest(581253, .33);
    }

    @Test
    public void varianceTest6(){
        varianceTest(8768765, .15);
    }
}
