package holidays.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import holidays.components.Transport;

public class TransportProvider {

	Map<Integer, Transport> transportDataMap = new HashMap<>();

	public TransportProvider() {
		loadTransportData();
	}

	private void loadTransportData() {
		// TODO Auto-generated method stub
		try {
			File transportData = new File(
					"src/holidays/datacontents/file/TransportData.txt");
			FileReader fileReader = new FileReader(transportData);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				Transport transport = populateTransport(line);

				transportDataMap.put(transport.getId(), transport);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 1#Sedan Car#Sedan car with capacity of 6 people#20
	private Transport populateTransport(String line) {
		String transportdetails[] = line.split("#");

		Transport transport = new Transport();
		transport.setId(Integer.parseInt(transportdetails[0]));
		transport.setName(transportdetails[1]);
		transport.setDescription(transportdetails[2]);
		transport.setPrice(Double.parseDouble(transportdetails[3]));

		return transport;
	}

	/*@Requires({"ids != null" , "ids.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<Transport> transportByIds(String ids) {
		List<Transport> transportList = new ArrayList<>();
		String id[] = ids.split(",");

		for (String strId : id) {
			
			Transport transport = transportDataMap.get(Integer.parseInt(strId));
			if (transport != null)
				transportList.add(transport);
		}
		return transportList;
	}

}
