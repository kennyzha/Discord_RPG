import ExperienceTests.LevelExpTests;
import ExperienceTests.LevelUpTests;
import ExperienceTests.MonsterExpGivenTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LevelUpTests.class,
        LevelExpTests.class,
        MonsterExpGivenTest.class,
        PlayerStatsTest.class
})

public class JunitTestSuite {


}
