package holidays.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import holidays.components.Activity;
import holidays.components.Flight;
import holidays.components.Hotel;
import holidays.components.Transport;
import holidays.customer.CustomerInfo;
import holidays.providers.ActivityProviders;
import holidays.providers.FlightsProvider;
import holidays.providers.HotelsProvider;
import holidays.providers.TransportProvider;

public class ServiceProvider {

	Map<Integer, HolidayPackage> packageDataMap = new HashMap<>();

	public ServiceProvider() {
		loadServiceData();
	}

	private void loadServiceData() {
		// TODO Auto-generated method stub
		try {
			File serviceData = new File("src/holidays/datacontents/file/HolidayPacakges.txt");
			FileReader fileReader = new FileReader(serviceData);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				HolidayPackage holidayPackage = populateService(line);

				packageDataMap.put(holidayPackage.getId(), holidayPackage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 1#Best of Western New York#This is value for money package#1#4 Days 3
	// Nights#Buffalo#noImage#1,2#2,3#1,2#1
	private HolidayPackage populateService(String line) {
		String servicedetails[] = line.split("#");
		HotelsProvider hp = new HotelsProvider();
		HolidayPackage holidayPackage = new HolidayPackage();

		ActivityProviders ap = new ActivityProviders();
		FlightsProvider fp = new FlightsProvider();
		TransportProvider tp = new TransportProvider();

		holidayPackage.setId(Integer.parseInt(servicedetails[0]));
		holidayPackage.setName(servicedetails[1]);
		holidayPackage.setDescription(servicedetails[2]);
		holidayPackage.setType(PackageType.getById(Integer.parseInt(servicedetails[3])));
		holidayPackage.setDuration(servicedetails[4]);
		holidayPackage.setFromCity(servicedetails[5]);
		holidayPackage.setImgsrc(servicedetails[6]);
		holidayPackage.setHotels(hp.hotelsByIds(servicedetails[7]));

		holidayPackage.setFlights(fp.flightsByIds(servicedetails[8]));
		holidayPackage.setActivities(ap.activityByIds(servicedetails[9]));
		holidayPackage.setTransport(tp.transportByIds(servicedetails[10]));

		return holidayPackage;
	}

	/*@Requires({"ids != null" , "ids.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<HolidayPackage> packageByIds(String ids) {
		List<HolidayPackage> packageList = new ArrayList<>();
		String id[] = ids.split(",");

		for (String strId : id) {
			HolidayPackage pkg = packageDataMap.get(Integer.parseInt(strId));
			if (pkg != null) {
				packageList.add(pkg);
			}
		}
		return packageList;
	}

	/*@Requires({"searchcriteria != null" , "searchcriteria.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<HolidayPackage> searchPackage(String searchcriteria) {

		List<HolidayPackage> packageList = new ArrayList<>();
		for (HolidayPackage hp : packageDataMap.values()) {
			if (hp.getFromCity().toLowerCase().contains(searchcriteria.toLowerCase())
					|| hp.getName().toLowerCase().contains(searchcriteria.toLowerCase())) {
				packageList.add(hp);
			}
		}

		return packageList;
	}

	/*@Requires({"listPackages != null", "listPackages.size() > 0"})*/
	public void ListPackages(List<HolidayPackage> listPackages) {

		List<Hotel> hotelList = new ArrayList<>();
		List<Activity> activityList = new ArrayList<>();
		List<Flight> flightList = new ArrayList<>();
		List<Transport> transportList = new ArrayList<>();

		for (HolidayPackage hp : listPackages) {

			hotelList = hp.getHotels();
			activityList = hp.getActivities();
			flightList = hp.getFlights();
			transportList = hp.getTransport();

			System.out.println("**************************************************************");
			System.out.println("ID :" + hp.getId() + "   Package Name: " + hp.getName());
			System.out.println("**************************************************************");
			System.out.println("Description :" + hp.getDescription());
			System.out.println("Type :" + hp.getType());
			System.out.println("Duration :" + hp.getDuration());
			System.out.println("From City :" + hp.getFromCity());

			if (!flightList.isEmpty()) {
				System.out.println("-----------------");
				System.out.println("Flight Details");
				System.out.println("-----------------");
				for (Flight flight : flightList) {
					System.out.println("Flight Name: " + flight.getCarrierName() + ", " + flight.getFlightName());
					System.out.println(
							"Departure Airport: " + flight.getFromCity() + "        Time: " + flight.getStartTime());
					System.out
							.println("Arrival Airport: " + flight.getToCity() + "       Time: " + flight.getEndTime());

					System.out.println();
				}
			}

			if (!hotelList.isEmpty()) {
				System.out.println("---------------");
				System.out.println("Hotel Details");
				System.out.println("---------------");
				for (Hotel htl : hotelList) {
					System.out.println("Hotel Name: " + htl.getName() + "    City:" + htl.getCityName());
					System.out.println("Description: " + htl.getDescription());
					System.out.println(
							"Checkin Time: " + htl.getCheckinTime() + "      Checkout Time: " + htl.getCheckoutTime());
					System.out.println("Room: " + htl.getRoomInfo().getCategory() + "     Meal :"
							+ htl.getRoomInfo().getDescription());
					System.out.println();
				}
			}

			if (!activityList.isEmpty()) {
				System.out.println("-------------------");
				System.out.println("Activities Details");
				System.out.println("-------------------");
				for (Activity act : activityList) {
					System.out.println("Activity Name: " + act.getName() + "     Duration: " + act.getDuration());
					System.out.println("Description: " + act.getDescription());
					System.out.println();
				}
			}

			if (!transportList.isEmpty()) {
				System.out.println("------------------");
				System.out.println("Transport Details");
				System.out.println("------------------");
				for (Transport trp : transportList) {
					System.out.println("Transport Name: " + trp.getName());
					System.out.println("Description: " + trp.getDescription());
				}
			}

			System.out.println();
			System.out.println();
		}
	}

/*	@Requires({"hpList != null" , "hpList.size() > 0"})
	@Ensures({"result != null",
		"result.getId() == old(id)"})*/
	public HolidayPackage selectPackage(int id, List<HolidayPackage> hpList) {

		for (HolidayPackage hpPkg : hpList) {
			if (hpPkg.getId() == id) {				
				return hpPkg;
			}
		}
		return null;
	}

	public HolidayPackage getPackageById(int id) {
		HolidayPackage hp = packageDataMap.get(id);
		
		return hp;
	}
	
	/*@Requires({"hp != null"})
	@Ensures({"result != null",
		"validatePrice(result)"})*/
	public HolidayPackage bookPackage(HolidayPackage hp) {
		System.out.println("**************************************************************");
		System.out.println("ID :" + hp.getId() + "   Package Name: " + hp.getName());
		System.out.println("**************************************************************");
		System.out.println("Description :" + hp.getDescription());
		System.out.println("Type :" + hp.getType());
		System.out.println("Duration :" + hp.getDuration());
		System.out.println("From City :" + hp.getFromCity());
		System.out.println("Flight Included :" + !hp.getFlights().isEmpty());
		System.out.println("Hotel Included :" + !hp.getHotels().isEmpty());
		System.out.println("Activity Included :" + !hp.getActivities().isEmpty());
		System.out.println("Transport Included :" + !hp.getTransport().isEmpty());
		Double totalPrice = getTotalPrice(hp);
		System.out.println("--------------------------------------------------------------");
		System.out.println("            Total Package Price : " + totalPrice +"$");
		System.out.println("--------------------------------------------------------------");
		hp.setTotalPrice(totalPrice);
		
		return hp;
	}

	public String makePayment(int packageId, CustomerInfo customer) {
		Boolean savePkgInfo = true;
		HolidayPackage hp= packageDataMap.get(packageId);
		savePkgInfo = savePackage(hp, customer);
		
		if(savePkgInfo) {
			return sendConfirmation("SUCCESS");
		}
		else {
			return sendConfirmation("FAILURE");
		}		
	}

	public Boolean savePackage(HolidayPackage hp, CustomerInfo customer) {
		// Write pacakge imp info and customer info into a new file called..
		// bookedpackagesinfo.txt

		BufferedWriter writetoFile = null;

		try {
			writetoFile = new BufferedWriter(
					new FileWriter("src/holidays/datacontents/file/bookedpackagesinfo.txt", true));
			writetoFile.write("\n" + customer.getUsername() + "#" + customer.getEmail() + "#" + hp.getId() + "#"
					+ hp.getName() + "#" + hp.getFromCity());
			// writetoFile.write("\n");
			return true;

		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;

		} finally {
			try {
				writetoFile.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}

	}

	public String sendConfirmation(String status) {

		if(status.equalsIgnoreCase("SUCCESS")) {
			return "Congratulations !!! You have Successfully Booked The Package. ";
		}
		
		if(status.equalsIgnoreCase("FAILURE")) {
			return "Sorry !!! There is error in Booking Package Please Start Again Or Contact our Customer Support";
		}
		
		return null;
	}

	public boolean cancelPackage(Integer id, String userName) 
	{
		BufferedReader readfromFile=null;
		BufferedWriter writetoFile = null;
		int flag=-1;
		
		try {						
			readfromFile = new BufferedReader(new FileReader("src/holidays/datacontents/file/bookedpackagesinfo.txt"));
			//writetoFile = new BufferedWriter(new FileWriter("/Users/akshaychopra/Documents/oodproject/HolidaysBookingSystem/holidayssystem/src/main/java/holidays/datacontents/file/bookedpackagesinfo.txt"));
			
			String sLine = "";
			String sData="";
			
				while ((sLine = readfromFile.readLine()) != null)
				{
					String parts[] = sLine.split("#");
					if(parts[0].equals(userName) && parts[2].equals(id.toString()))
					{
								flag=1;			
					}
					else
					{
						sData+=sLine;
						sData+="\n";
					}
					
					
				}
				writetoFile = new BufferedWriter(new FileWriter("src/holidays/datacontents/file/bookedpackagesinfo.txt"));
				writetoFile.write(sData);
				
				if (flag==1)
					return true;
				else
					return false;
			}
		
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
			
		}
		finally {
			try {
				readfromFile.close();
				writetoFile.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}	
	}
	
	public double getTotalPrice(HolidayPackage hp) {
		
		Double totalPrice = 0d;
		if(!hp.getFlights().isEmpty()) {
			for (Flight flight : hp.getFlights()) {
				totalPrice += flight.getPrice();	
			}
		}
		if(!hp.getHotels().isEmpty()) {
			for (Hotel htl : hp.getHotels()) {
				totalPrice += htl.getPrice();	
			}
		}
		if(!hp.getActivities().isEmpty()) {
			for (Activity act : hp.getActivities()) {
				totalPrice += act.getPrice();	
			}
		}
		if(!hp.getTransport().isEmpty()) {
			for (Transport transport : hp.getTransport()) {
				totalPrice += transport.getPrice();	
			}
		}
		return totalPrice;
	}
	
	/**
	 * Contract validation for price checking 
	 * @param hp
	 * @return
	 */
	public boolean validatePrice(HolidayPackage hp) {
		double calculatedPrice = hp.getTotalPrice();
		Double totalPrice = 0d;
		if (!hp.getFlights().isEmpty()) {
			for (Flight flight : hp.getFlights()) {
				totalPrice += flight.getPrice();
			}
		}
		if (!hp.getHotels().isEmpty()) {
			for (Hotel htl : hp.getHotels()) {
				totalPrice += htl.getPrice();
			}
		}
		if (!hp.getActivities().isEmpty()) {
			for (Activity act : hp.getActivities()) {
				totalPrice += act.getPrice();
			}
		}
		if (!hp.getTransport().isEmpty()) {
			for (Transport transport : hp.getTransport()) {
				totalPrice += transport.getPrice();
			}
		}
		if (totalPrice == calculatedPrice)
			return true;
		else
			return false;
	}
}
