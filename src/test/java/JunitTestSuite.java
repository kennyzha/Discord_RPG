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
        HealthGainedTests.class
})

public class JunitTestSuite {
}
