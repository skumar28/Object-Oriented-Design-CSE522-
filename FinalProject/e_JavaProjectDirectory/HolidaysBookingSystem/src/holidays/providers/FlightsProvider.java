package holidays.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import holidays.components.Flight;


public class FlightsProvider {


	Map<Integer, Flight> flightDataMap = new HashMap<>();
	public FlightsProvider() {
		loadFlightData();
	}
	
	private void loadFlightData() {
		// TODO Auto-generated method stub
		try {
			File flightData = new File(
					"src/holidays/datacontents/file/FlightsData.txt");
			FileReader fileReader = new FileReader(flightData);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				Flight flight = populateFlight(line);
				
				flightDataMap.put(flight.getId(), flight);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//1#Emirates#E-515#Buffalo#New York#10.00AM#12.00PM#80
	private Flight populateFlight(String line) {
		String flightdetails[] = line.split("#");
		 
		Flight flight = new Flight();
		flight.setId(Integer.parseInt(flightdetails[0]));
		flight.setCarrierName(flightdetails[1]);
		flight.setFlightName(flightdetails[2]);
		flight.setFromCity(flightdetails[3]);
		flight.setToCity(flightdetails[4]);
		flight.setStartTime(flightdetails[5]);
		flight.setEndTime(flightdetails[6]);
		flight.setPrice(Double.parseDouble(flightdetails[7]));			
		
		return flight;
	}
	/*@Requires({"ids != null" , "ids.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<Flight> flightsByIds(String ids){
		List<Flight> flightList = new ArrayList<>();
		String id[] = ids.split(","); 
		
		for(String strId : id) {
			
			Flight flight = flightDataMap.get(Integer.parseInt(strId));
			if (flight != null)
				flightList.add(flight);
		}
		return flightList;
	}

}
