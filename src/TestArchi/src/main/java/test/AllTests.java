package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ LoaderTest.class, DroolsPlaceEntityTest.class, DroolsCommunicationsTest.class })
public class AllTests {
	
}