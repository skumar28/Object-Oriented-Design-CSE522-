package holidays.services;

import java.util.List;
import java.util.Scanner;

import holidays.LoginDetails.Login;
import holidays.customer.CustomerInfo;
import holidays.customer.LoginInfo;

public class HolidaysBookingSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("*****Welcome To Holiday Package Booking System*****");
		Login login = new Login();
		LoginInfo userInfo = new LoginInfo();

		boolean userStatus = userLogin(login, userInfo);
		if (userStatus) {
			System.out.println("Welcome " + userInfo.getUserName());
		}
		ServiceProvider serviceProvider = new ServiceProvider();
		List<HolidayPackage> hpList = null;
		HolidayPackage hpPackage = null;
		while(true) {			
		
		System.out.println("1. Search Package");
		System.out.println("2. Select Package");
		System.out.println("3. Book Package");
		System.out.println("4. Make Payment");
		System.out.println("5. Exit");
		System.out.println("---------------------------");
		System.out.println("Please Enter Your Choice:");
		Scanner userInput = new Scanner(System.in);
		int choice = userInput.nextInt();
		
		switch(choice) {
		case 1:
			hpList = searchPackage(serviceProvider);
			break;
		
		case 2:
			hpPackage = selectPacakge(hpList, serviceProvider);		
			System.out.println("You have Selcted Package : " + hpPackage.getName());
			System.out.println("Select Book Option to See Price and Book This Package");
			break;
			
		case 3:
			bookPakcage(hpPackage, userInfo, serviceProvider);
			break;
		case 4:
			userInput = new Scanner(System.in);
			System.out.println("Enter Full Name: ");			
			String fullName = userInput.next();
			System.out.println("Enter Email: ");			
			String email = userInput.next();
			System.out.println("Enter Phone No: ");			
			String phone = userInput.next();
			System.out.println("Enter Crad Detail: ");			
			String cardDetail = userInput.next();
			
			CustomerInfo custInfo = new CustomerInfo();
			custInfo.setUsername(userInfo.getUserName());
			custInfo.setFullName(fullName);
			custInfo.setEmail(email);
			custInfo.setContactNum(phone);
			custInfo.setCardDetail(cardDetail);
			String confirmationMsg = serviceProvider.makePayment(hpPackage.getId(), custInfo);
			System.out.println(confirmationMsg);
			break;
		default:
			System.out.println("Please enter correct Choice");
		}
		
		if(choice == 5)
			break;
		}
		
		System.out.println("*********************Thank You*********************");
	}

	private static void bookPakcage(HolidayPackage hpPackage, LoginInfo userInfo, ServiceProvider serviceProvider) {
		if (hpPackage == null) {
			System.out.println("No Package is selected for Booking! Please Search and Select");
		} else {
			System.out.println("Hi " + userInfo.getUserName() + ", You have Selecte below Pakckage.");
			serviceProvider.bookPackage(hpPackage);
			System.out.println("Select Make Payment Option To Confirm Your Booking");
		}
	}

	private static HolidayPackage selectPacakge(List<HolidayPackage> hpList, ServiceProvider serviceProvider) {
		HolidayPackage hpPackage = null;
		if(hpList == null || hpList.isEmpty()) {			
			System.out.println("No Package Found for Selection!!! Please search again.");
		}
		else {			
			Scanner userInput = new Scanner(System.in);
			System.out.println("Select Package ID: ");
			int packageId = userInput.nextInt();
			hpPackage = serviceProvider.selectPackage(packageId,hpList);
			
		}
		
		return hpPackage;
	}

	public static List<HolidayPackage> searchPackage(ServiceProvider serviceProvider) {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter City Name Or Package Name: ");
		String searchCriteria = userInput.next();
		List<HolidayPackage> hpList = serviceProvider.searchPackage(searchCriteria);

		if (!hpList.isEmpty()) {
			serviceProvider.ListPackages(hpList);
		} else {
			System.out.println("Opps!!! No Package Found for Given Criteria.");
		}
		
		return hpList;
	}

	private static boolean userLogin(Login login, LoginInfo userInfo) {
		Scanner userInput = new Scanner(System.in);

		System.out.println("1. Sign In");
		System.out.println("2. Sign Up");
		System.out.println("3. Exit");
		System.out.println("----------------------------");
		System.out.println("Please Enter Your Choice:");

		// get user input
		int choice = userInput.nextInt();

		// switch the choice from user
		switch (choice) {
		case 1:
			// Sign In
			System.out.println("Enter UserName: ");
			userInfo.setUserName(userInput.next());
			System.out.println("Enter Passowrd");
			userInfo.setPassword(userInput.next());

			boolean check = login.validateUser(userInfo);
			if (check == false) {
				System.out.println("Invalid Username or Password");
				userLogin(login, userInfo);
			} else {
				System.out.println("You have successfully loged in");
				return true;
			}

			break;

		case 2:
			// Sign Up
			System.out.println("Enter UserName: ");
			userInfo.setUserName(userInput.next());
			System.out.println("Enter Password");
			userInfo.setPassword(userInput.next());

			check = login.registerNewUser(userInfo);
			if (check == false) {
				System.out.println("Username already exist!!! Try a new Username");
				userLogin(login, userInfo);
			} else {
				System.out.println("New User successfully created!");
				return true;
			}

			break;
		case 3:

			System.out.println("You have exited successfully!");
			return false;

		default:
			System.out.println("You entered an invalid choice");
			userLogin(login, userInfo);
		}
		return true;
	}

}