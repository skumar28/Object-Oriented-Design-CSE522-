package holidays.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import holidays.components.Hotel;
import holidays.components.Room;

public class HotelsProvider {

	Map<Integer, Hotel> hotelDataMap = new HashMap<>();

	public HotelsProvider() {
		loadHotelData();
	}

	private void loadHotelData() {
		// TODO Auto-generated method stub
		try {
			File hotelData = new File("src/holidays/datacontents/file/HotelsData.txt");
			FileReader fileReader = new FileReader(hotelData);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				Hotel hotel = populateHotel(line);

				hotelDataMap.put(hotel.getId(), hotel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//// 1#TestHotel#This is test hotel #Buffalo#USA#12 PM #11 AM #Room Delux
	//// #Breakfast Included #100
	private Hotel populateHotel(String line) {
		String hoteldetails[] = line.split("#");

		Hotel hotel = new Hotel();
		Room room = new Room();
		hotel.setId(Integer.parseInt(hoteldetails[0]));
		hotel.setName(hoteldetails[1]);
		hotel.setDescription(hoteldetails[2]);
		hotel.setCityName(hoteldetails[3]);
		hotel.setCountry(hoteldetails[4]);
		hotel.setCheckinTime(hoteldetails[5]);
		hotel.setCheckoutTime(hoteldetails[6]);
		room.setCategory(hoteldetails[7]);
		room.setDescription(hoteldetails[8]);
		hotel.setRoomInfo(room);
		hotel.setPrice(Double.parseDouble(hoteldetails[9]));

		return hotel;
	}

	/*@Requires({"ids != null" , "ids.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<Hotel> hotelsByIds(String ids) {
		List<Hotel> hotelList = new ArrayList<>();
		String id[] = ids.split(",");

		for (String strId : id) {
			Hotel hotel = hotelDataMap.get(Integer.parseInt(strId));
			if (hotel != null)
				hotelList.add(hotel);
		}
		return hotelList;
	}
}
