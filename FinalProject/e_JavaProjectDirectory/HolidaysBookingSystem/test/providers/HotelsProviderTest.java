package holidays.providers;

import java.util.List;

import holidays.components.Hotel;
import junit.framework.TestCase;

public class HotelsProviderTest extends TestCase {
	
	HotelsProvider hotelProvider;
	
	public void setUp() {
		hotelProvider = new HotelsProvider();
	}

	public void testHotelsProvider()
    {	
		List<Hotel> hotelList = hotelProvider.hotelsByIds("1");
		
		assertEquals(1, hotelList.size());
    }
	
	public void testNoHotelFound() {
		List<Hotel> hotelList = hotelProvider.hotelsByIds("100");
		assertTrue(hotelList.isEmpty());
	}
	
	
}
