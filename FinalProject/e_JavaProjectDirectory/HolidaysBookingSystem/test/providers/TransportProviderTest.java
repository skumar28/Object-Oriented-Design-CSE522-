package holidays.providers;

import java.util.List;


import holidays.components.Transport;
import junit.framework.TestCase;

public class TransportProviderTest extends TestCase {


	TransportProvider transportProvider;
	
	public void setUp() {
		transportProvider = new TransportProvider();
	}

	public void testTransportProvider()
    {	
		List<Transport> transportList = transportProvider.transportByIds("1");
		
		assertEquals(1, transportList.size());
    }
	
	public void testNoTransportFound() {
		List<Transport> transportList = transportProvider.transportByIds("100");
		assertTrue(transportList.isEmpty());
	}
}
