import ItemTests.ItemRollTest;
import ItemTests.ItemTest;
import MonsterTests.MonsterExpGivenTest;
import PlayerTests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LevelUpTests.class,
        LevelExpTests.class,
        MonsterExpGivenTest.class,
        PlayerStatsTest.class,
        HealthGainedTests.class,
        ItemTest.class,
        ItemRollTest.class
})

public class JunitTestSuite {
}
