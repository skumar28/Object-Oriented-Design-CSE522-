package holidays.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import holidays.components.Activity;

public class ActivityProviders {



	Map<Integer, Activity> activityDataMap = new HashMap<>();
	public ActivityProviders() {
		loadActivityData();
	}
	
	private void loadActivityData() {
		// TODO Auto-generated method stub
		try {
			File activityData = new File(
					"src/holidays/datacontents/file/ActivityData.txt");
			FileReader fileReader = new FileReader(activityData);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				Activity activity = populateActivity(line);
				
				activityDataMap.put(activity.getId(), activity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//1#New York City Tour#Visit the famous places of New York City#8 Hours#20
	private Activity populateActivity(String line) {
		String activitydetails[] = line.split("#");
		
		Activity activity = new Activity();
		activity.setId(Integer.parseInt(activitydetails[0]));
		activity.setName(activitydetails[1]);
		activity.setDescription(activitydetails[2]);
		activity.setDuration(activitydetails[3]);
		activity.setPrice(Double.parseDouble(activitydetails[4]));			
		
		return activity;
	}

/*	@Requires({"ids != null" , "ids.length() > 0"})
	@Ensures({"result != null",
		"result.size() > 0"})*/
	public List<Activity> activityByIds(String ids){
		List<Activity> activityList = new ArrayList<>();
		String id[] = ids.split(","); 
		
		for(String strId : id) {
			
			Activity activity = activityDataMap.get(Integer.parseInt(strId));
			if (activity != null)
				activityList.add(activity);
		}
		return activityList;
	}


}
