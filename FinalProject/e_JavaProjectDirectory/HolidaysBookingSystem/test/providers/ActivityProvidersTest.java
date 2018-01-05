package holidays.providers;

import java.util.List;

import holidays.components.Activity;
import junit.framework.TestCase;

public class ActivityProvidersTest extends TestCase {
	
	ActivityProviders activityProvider;
	
	public void setUp() {
		activityProvider = new ActivityProviders();
	}

	public void testActivityProvider()
    {	
		List<Activity> activityList = activityProvider.activityByIds("1");
		
		assertEquals(1, activityList.size());
    }
	
	public void testNoActivityFound() {
		List<Activity> activityList = activityProvider.activityByIds("100");
		assertTrue(activityList.isEmpty());
	}
	
	
}
