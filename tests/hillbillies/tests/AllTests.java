package hillbillies.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	BoulderTest.class, 
	EntityTest.class,
    ItemTest.class, 
    LogTest.class, 
    PathFinderTest.class,
    PositionTest.class, 
//    TaskFactoryTest.class,
//    SchedulerTest.class,
    UnitTest.class, 
    WorldTest.class})
public class AllTests {
}
