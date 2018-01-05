package holidays.providers;

import java.util.List;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import holidays.components.Flight;

import junit.framework.TestCase;

public class FlightsProviderTest extends TestCase {
	
	FlightsProvider flightProvider;
	
	
	public void setUp() {
		flightProvider = new FlightsProvider();
	}

	public void testFlightsProvider()
    {	
		List<Flight> flightList = flightProvider.flightsByIds("1");
		
		assertEquals(1, flightList.size());
    }
	
	public void testNoFlightFound() {
		List<Flight> flightList = flightProvider.flightsByIds("100");
		assertTrue(flightList.isEmpty());
	}
	
	/*public static void main(String arg[]) {
		System.out.println(gcd(0,0));
	}*/
	
	
	public  void testDisplayFlight() {
		FlightsProvider flightProvider = new FlightsProvider();
		List<Flight> flightList = flightProvider.flightsByIds("1,2");
		for(Flight flt : flightList) {
			System.out.println(flt.getCarrierName());
		}		
		assertNotNull(flightList);
	}
	
	/*@Requires({ "x > 0", "y > 0" })
	@Ensures({ "result != 0", 
		       "old(x) % result == 0", 
		       "old(y) % result == 0" })
	public static int gcd(int x, int y) {
		while (x != 0 && y != 0) {
			if (x > y) {
				x = x - y;
			} else {
				y = y - x;
			}
		}
		return (x != 0) ? x : y;
	}*/

}
