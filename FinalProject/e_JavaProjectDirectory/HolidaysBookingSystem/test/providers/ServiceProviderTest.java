package holidays.providers;

import java.util.List;
import junit.framework.TestCase;
import holidays.services.ServiceProvider;
import holidays.components.Hotel;
import holidays.services.HolidayPackage;

public class ServiceProviderTest extends TestCase {
	
	ServiceProvider serviceProvider;
	
	public void setUp() {
		serviceProvider = new ServiceProvider();
	}

	public void testServiceProvider()
    {	
		List<HolidayPackage> packageList = serviceProvider.packageByIds("1");
		
		assertEquals(1, packageList.size());
    }
	
	public void testNoPackageFound() {
		List<HolidayPackage> packageList = serviceProvider.packageByIds("100");
		assertTrue(packageList.isEmpty());
	}

	public void testSerachPackage() {
		List<HolidayPackage> packageList = serviceProvider.searchPackage("New York");
		assertTrue(!packageList.isEmpty());
	}
	
	public void testSelectPackage() {
		List<HolidayPackage> packageList = serviceProvider.searchPackage("New York");
		int id = packageList.get(0).getId();
		HolidayPackage hp = serviceProvider.selectPackage(id, packageList);
		assertEquals(id, hp.getId());
	}
	
	public void testBookPackage() {
		List<HolidayPackage> packageList = serviceProvider.searchPackage("New York");
		int id = packageList.get(0).getId();
		HolidayPackage hp = serviceProvider.selectPackage(id, packageList);
		hp = serviceProvider.bookPackage(hp);
		assertEquals(id, hp.getId());
	}
	
	/*
	 * This main method is to verify the cofoja contracts in service provider class
	 */
	/*public static void main(String arg[]) {
		ServiceProvider sp = new ServiceProvider();
		List<HolidayPackage> hp = sp.searchPackage("fadsfdf");
		System.out.println("passed");
		
		List<HolidayPackage> hpList = sp.searchPackage("New York");
		int id = hpList.get(0).getId();
		HolidayPackage hp = sp.selectPackage(id, hpList);
		sp.bookPackage(hp);
		System.out.println(hp.getName());
	}*/

}
