package holidays.services;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import holidays.components.Activity;
import holidays.components.Flight;
import holidays.components.Hotel;
import holidays.components.Transport;
import holidays.customer.CustomerInfo;

public class HolidaySearchFrame implements ActionListener {

	String userName;

	JButton bookPackage;
	JButton makePayment;
	JButton submitPayment;
	JButton cancelPacakge;
	JButton customerSupport;
	JButton cancelPayment;

	JFrame frame = new JFrame();
	JPanel topPanel = new JPanel();
	JPanel botPanel = new JPanel();

	JPanel searchPanel = new JPanel();
	JPanel resultPanel = new JPanel();
	ServiceProvider serviceProvider = new ServiceProvider();
	CustomerInfo custInfo;
	JButton searchButton;
	JTextField searchTextField;
	JLabel lable = new JLabel();

	JTextField textField_0 = new JTextField();
	JTextField textField_1 = new JTextField();
	JTextField textField_2 = new JTextField();
	JTextField textField_3 = new JTextField();
	JTextField textField_4 = new JTextField();

	public HolidaySearchFrame(String usrName) {

		this.userName = usrName;

		frame.setBounds(200, 100, 1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout gLayout = new GridLayout(2, 1);
		topPanel.setLayout(gLayout);

		JLabel applicationName = new JLabel();
		applicationName.setText("<html><h2>Holidays Booking Management System</h2></html>");
		applicationName.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel label = new JLabel();
		label.setText("Enter Package Name Or City Name:");
		searchTextField = new JTextField(40);
		searchTextField.setToolTipText("Enter Package Name Or City Name");
		searchPanel.add(label);
		searchPanel.add(searchTextField);

		searchButton = new JButton("Search");
		searchButton.setActionCommand("search");
		searchButton.addActionListener(this);

		cancelPacakge = new JButton("Cancel Package");
		cancelPacakge.setActionCommand("Cancel Package");
		cancelPacakge.addActionListener(this);

		customerSupport = new JButton("Customer Support");
		customerSupport.setActionCommand("support");
		customerSupport.addActionListener(this);

		botPanel.add(cancelPacakge);
		botPanel.add(customerSupport);

		JScrollPane scrPane = new JScrollPane(resultPanel);
		Container contentPane = frame.getContentPane();
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(scrPane, BorderLayout.CENTER);
		contentPane.add(botPanel, BorderLayout.SOUTH);

		scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		searchPanel.add(searchButton);
		topPanel.add(applicationName);
		topPanel.add(searchPanel);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resultPanel.removeAll();

		JPanel thePanel = new JPanel();

		if (e.getActionCommand().equalsIgnoreCase("search")) {

			String searchCriteria = searchTextField.getText();

			List<HolidayPackage> hpList = serviceProvider.searchPackage(searchCriteria);

			if (!hpList.isEmpty()) {

				thePanel.setLayout(new GridLayout(hpList.size(), 0, 20, 20));
				int count = 0;
				for (HolidayPackage hp : hpList) {
					List<Hotel> hotelList = hp.getHotels();
					List<Activity> activityList = hp.getActivities();
					List<Flight> flightList = hp.getFlights();
					List<Transport> transportList = hp.getTransport();

					Box theBox = Box.createVerticalBox();

					JLabel nameLabel = new JLabel("Package Name: " + hp.getName());
					Font nameFont = new Font("Helvetica", Font.BOLD, 16);
					nameLabel.setFont(nameFont);
					theBox.add(nameLabel);
					// addComp(thePanel, nameLabel, 0, count++, 1, 1, GridBagConstraints.EAST,
					// GridBagConstraints.NONE);

					JLabel descLabel = new JLabel("Description: " + hp.getDescription());
					theBox.add(descLabel);
					JLabel type = new JLabel("Type: " + hp.getType());
					theBox.add(type);
					JLabel duration = new JLabel("Duration: " + hp.getDuration());
					theBox.add(duration);
					JLabel fromCity = new JLabel("From City: " + hp.getFromCity());
					theBox.add(fromCity);

					Font font = new Font("Helvetica", Font.BOLD, 14);

					if (!flightList.isEmpty()) {
						JLabel fDetail = new JLabel("Flight Details");
						fDetail.setFont(font);
						theBox.add(fDetail);

						for (Flight flight : flightList) {
							Box flightBox = Box.createVerticalBox();
							JLabel flightName = new JLabel(
									"Flight Name: " + flight.getFlightName() + ", " + flight.getCarrierName());
							flightBox.add(flightName);
							JLabel departureAirport = new JLabel(
									"Departure Airport: " + flight.getFromCity() + " Time: " + flight.getStartTime());
							flightBox.add(departureAirport);
							JLabel arrivalAirport = new JLabel(
									"Arrival Airport: " + flight.getToCity() + " Time: " + flight.getEndTime());
							flightBox.add(arrivalAirport);
							JLabel seprator = new JLabel("                                       ");
							flightBox.add(seprator);
							theBox.add(flightBox);
						}
					}

					if (!hotelList.isEmpty()) {
						JLabel hDetail = new JLabel("Hotel Details");
						hDetail.setFont(font);
						theBox.add(hDetail);

						for (Hotel htl : hotelList) {
							Box hotelBox = Box.createVerticalBox();
							JLabel hotelName = new JLabel(
									"Hotel Name: " + htl.getName() + " City: " + htl.getCityName());
							hotelBox.add(hotelName);
							JLabel description = new JLabel("Description: " + htl.getDescription());
							hotelBox.add(description);
							JLabel hoteltime = new JLabel("Checkin Time: " + htl.getCheckinTime() + " Checkout Time: "
									+ htl.getCheckoutTime());
							hotelBox.add(hoteltime);
							JLabel hotelroom = new JLabel("Room: " + htl.getRoomInfo().getDescription() + " Meal: "
									+ htl.getRoomInfo().getCategory());
							hotelBox.add(hotelroom);
							JLabel seprator = new JLabel("                                       ");
							hotelBox.add(seprator);
							theBox.add(hotelBox);

						}
					}

					if (!activityList.isEmpty()) {
						JLabel aDetail = new JLabel("Activity Details");
						aDetail.setFont(font);
						theBox.add(aDetail);
						for (Activity act : activityList) {

							Box activityBox = Box.createVerticalBox();
							JLabel activityName = new JLabel(
									"Activity Name:" + act.getName() + " Duration: " + act.getDuration());
							activityBox.add(activityName);
							JLabel activityDescription = new JLabel("Description: " + act.getDescription());
							activityBox.add(activityDescription);
							JLabel seprator = new JLabel("                                       ");
							activityBox.add(seprator);
							theBox.add(activityBox);

						}
					}
					JLabel tDetail = new JLabel("Transport Details");
					tDetail.setFont(font);
					theBox.add(tDetail);
					if (!transportList.isEmpty()) {
						for (Transport trp : transportList) {
							Box transportBox = Box.createVerticalBox();

							JLabel transportName = new JLabel("Transport Name: " + trp.getName());
							transportBox.add(transportName);
							JLabel transportDescription = new JLabel("Description: " + trp.getDescription());
							transportBox.add(transportDescription);
							JLabel seprator = new JLabel("                                       ");
							transportBox.add(seprator);
							theBox.add(transportBox);

						}
					}
					bookPackage = new JButton("Book Package");
					bookPackage.setActionCommand("Book Package #" + hp.getId());
					bookPackage.addActionListener(this);
					theBox.add(bookPackage);

					thePanel.add(theBox);

				}

				serviceProvider.ListPackages(hpList);
				System.out.println("Pacakge found: " + hpList.size());
			} else {
				thePanel.add(new JLabel("Opps!!! No Package Found for Given Criteria."));
			}
			resultPanel.add(thePanel);

			SwingUtilities.updateComponentTreeUI(frame);
		}

		if (e.getActionCommand().contains("Book Package")) {
			System.out.println("Book Package called" + e.getActionCommand());
			String packInfo[] = e.getActionCommand().split("#");
			int packageId = Integer.parseInt(packInfo[1]);

			HolidayPackage hp = serviceProvider.getPackageById(packageId);

			List<Hotel> hotelList = hp.getHotels();
			List<Activity> activityList = hp.getActivities();
			List<Flight> flightList = hp.getFlights();
			List<Transport> transportList = hp.getTransport();

			Box theBox = Box.createVerticalBox();
			JLabel msg = new JLabel("You Have Selected Below Pakckage");
			Font nameFont1 = new Font("Helvetica", Font.BOLD, 17);
			msg.setFont(nameFont1);
			theBox.add(msg);
			JLabel seprator = new JLabel("                                       ");
			theBox.add(seprator);
			JLabel nameLabel = new JLabel("Package Name: " + hp.getName());
			Font nameFont = new Font("Helvetica", Font.BOLD, 16);
			nameLabel.setFont(nameFont);
			theBox.add(nameLabel);

			JLabel descLabel = new JLabel("Description: " + hp.getDescription());
			theBox.add(descLabel);
			JLabel type = new JLabel("Type: " + hp.getType());
			theBox.add(type);
			JLabel duration = new JLabel("Duration: " + hp.getDuration());
			theBox.add(duration);
			JLabel fromCity = new JLabel("From City: " + hp.getFromCity());
			theBox.add(fromCity);

			Font font = new Font("Helvetica", Font.BOLD, 14);

			if (!flightList.isEmpty()) {
				JLabel fDetail = new JLabel("Flight Details");
				fDetail.setFont(font);
				theBox.add(fDetail);

				for (Flight flight : flightList) {
					Box flightBox = Box.createVerticalBox();
					JLabel flightName = new JLabel(
							"Flight Name: " + flight.getFlightName() + ", " + flight.getCarrierName());
					flightBox.add(flightName);
					JLabel departureAirport = new JLabel(
							"Departure Airport: " + flight.getFromCity() + " Time: " + flight.getStartTime());
					flightBox.add(departureAirport);
					JLabel arrivalAirport = new JLabel(
							"Arrival Airport: " + flight.getToCity() + " Time: " + flight.getEndTime());
					flightBox.add(arrivalAirport);
					seprator = new JLabel("                                       ");
					flightBox.add(seprator);
					theBox.add(flightBox);

				}
			}

			if (!hotelList.isEmpty()) {
				JLabel hDetail = new JLabel("Hotel Details");
				hDetail.setFont(font);
				theBox.add(hDetail);

				for (Hotel htl : hotelList) {
					Box hotelBox = Box.createVerticalBox();
					JLabel hotelName = new JLabel("Hotel Name: " + htl.getName() + " City: " + htl.getCityName());
					hotelBox.add(hotelName);
					JLabel description = new JLabel("Description: " + htl.getDescription());
					hotelBox.add(description);
					JLabel hoteltime = new JLabel(
							"Checkin Time: " + htl.getCheckinTime() + " Checkout Time: " + htl.getCheckoutTime());
					hotelBox.add(hoteltime);
					JLabel hotelroom = new JLabel("Room: " + htl.getRoomInfo().getDescription() + " Meal: "
							+ htl.getRoomInfo().getCategory());
					hotelBox.add(hotelroom);
					seprator = new JLabel("                                       ");
					hotelBox.add(seprator);
					theBox.add(hotelBox);

				}
			}

			if (!activityList.isEmpty()) {
				JLabel aDetail = new JLabel("Activity Details");
				aDetail.setFont(font);
				theBox.add(aDetail);
				for (Activity act : activityList) {

					Box activityBox = Box.createVerticalBox();
					JLabel activityName = new JLabel(
							"Activity Name:" + act.getName() + " Duration: " + act.getDuration());
					activityBox.add(activityName);
					JLabel activityDescription = new JLabel("Description: " + act.getDescription());
					activityBox.add(activityDescription);
					seprator = new JLabel("                                       ");
					activityBox.add(seprator);
					theBox.add(activityBox);
				}
			}
			JLabel tDetail = new JLabel("Transport Details");
			tDetail.setFont(font);
			theBox.add(tDetail);
			if (!transportList.isEmpty()) {
				for (Transport trp : transportList) {
					Box transportBox = Box.createVerticalBox();

					JLabel transportName = new JLabel("Transport Name: " + trp.getName());
					transportBox.add(transportName);
					JLabel transportDescription = new JLabel("Description: " + trp.getDescription());
					transportBox.add(transportDescription);
					seprator = new JLabel("                                       ");
					transportBox.add(seprator);
					theBox.add(transportBox);
				}
			}

			seprator = new JLabel("                                       ");
			theBox.add(seprator);
			Double totalPrice = serviceProvider.getTotalPrice(hp);
			hp.setTotalPrice(totalPrice);

			JLabel price = new JLabel("Total Pacakge Price: " + totalPrice + "$");
			nameFont1 = new Font("Helvetica", Font.BOLD, 17);
			price.setFont(nameFont1);
			theBox.add(price);

			makePayment = new JButton("Make Payment");
			makePayment.setActionCommand("Make Payment #" + hp.getId());
			makePayment.addActionListener(this);
			theBox.add(makePayment);

			thePanel.add(theBox);

			resultPanel.add(thePanel);
			SwingUtilities.updateComponentTreeUI(frame);
		}

		if (e.getActionCommand().contains("Make Payment")) {
			System.out.println("Make Payment called" + e.getActionCommand());
			String packInfo[] = e.getActionCommand().split("#");
			int packageId = Integer.parseInt(packInfo[1]);

			HolidayPackage hp = serviceProvider.getPackageById(packageId);
			Box theBoxV = Box.createVerticalBox();
			JPanel tempPanel = new JPanel();

			JLabel msg = new JLabel("Enter the Below Information");
			Font nameFont1 = new Font("Helvetica", Font.BOLD, 17);
			msg.setFont(nameFont1);
			theBoxV.add(msg);

			JLabel seprator = new JLabel("                                       ");
			theBoxV.add(seprator);

			JLabel lblName = new JLabel("Name");
			lblName.setBounds(65, 68, 46, 14);
			tempPanel.add(lblName);

			textField_0.setBounds(128, 65, 150, 20);
			tempPanel.add(textField_0);
			textField_0.setColumns(10);
			theBoxV.add(tempPanel);

			tempPanel = new JPanel();
			JLabel lblPhone = new JLabel("Phone");
			lblPhone.setBounds(65, 68, 46, 14);
			tempPanel.add(lblPhone);

			textField_1.setBounds(128, 65, 150, 20);
			tempPanel.add(textField_1);
			textField_1.setColumns(10);
			theBoxV.add(tempPanel);

			tempPanel = new JPanel();
			JLabel lblEmailId = new JLabel("Email Id");
			lblEmailId.setBounds(65, 115, 46, 14);
			tempPanel.add(lblEmailId);

			textField_2.setBounds(128, 112, 150, 20);
			tempPanel.add(textField_2);
			textField_2.setColumns(10);
			theBoxV.add(tempPanel);

			tempPanel = new JPanel();
			JLabel lblCard = new JLabel("Card Details");
			lblCard.setBounds(65, 162, 46, 14);
			tempPanel.add(lblCard);

			textField_3.setBounds(128, 112, 150, 20);
			tempPanel.add(textField_3);
			textField_3.setColumns(10);
			theBoxV.add(tempPanel);

			submitPayment = new JButton("Pay");
			submitPayment.setActionCommand("Submit Payment #" + hp.getId());
			submitPayment.addActionListener(this);
			theBoxV.add(submitPayment);

			thePanel.add(theBoxV);

			resultPanel.add(thePanel);
			SwingUtilities.updateComponentTreeUI(frame);
		}

		if (e.getActionCommand().contains("Submit Payment")) {
			System.out.println("Submit Payment called" + e.getActionCommand());
			String packInfo[] = e.getActionCommand().split("#");
			int packageId = Integer.parseInt(packInfo[1]);

			custInfo = new CustomerInfo();
			custInfo.setUsername(userName);
			custInfo.setEmail(textField_2.getText());
			custInfo.setContactNum(textField_1.getText());
			custInfo.setCardDetail(textField_3.getText());

			HolidayPackage hp = serviceProvider.getPackageById(packageId);
			String confirmationMessage = serviceProvider.makePayment(packageId, custInfo);
			JLabel nameLabel = new JLabel(confirmationMessage + "ID :" + packageId + " " + hp.getName());
			Font nameFont = new Font("Helvetica", Font.BOLD, 16);
			nameLabel.setFont(nameFont);
			thePanel.add(nameLabel);
			resultPanel.add(thePanel);
			SwingUtilities.updateComponentTreeUI(frame);
		}

		if (e.getActionCommand().contains("Cancel Package")) {
			System.out.println("Cancel Package called" + e.getActionCommand());

			Box theBox = Box.createVerticalBox();
			JPanel tempPanl = new JPanel();

			JLabel lblID = new JLabel("Package ID");
			lblID.setBounds(65, 68, 46, 14);
			tempPanl.add(lblID);

			textField_4.setBounds(128, 65, 150, 20);
			textField_4.setColumns(10);
			tempPanl.add(textField_4);

			theBox.add(tempPanl);

			cancelPayment = new JButton("Cancel Order");
			cancelPayment.setActionCommand("Cancel Order #");
			cancelPayment.addActionListener(this);
			theBox.add(cancelPayment);

			thePanel.add(theBox);

			resultPanel.add(thePanel);
			SwingUtilities.updateComponentTreeUI(frame);

		}

		if (e.getActionCommand().contains("Cancel Order")) {
			JLabel nameLabel;
			Font nameFont;
			System.out.println("Cancel Button called" + e.getActionCommand());
			String idInfo[] = e.getActionCommand().split("#");
			int packageID = Integer.parseInt(textField_4.getText());

			ServiceProvider service = new ServiceProvider();
			boolean check = service.cancelPackage(packageID, this.userName);

			if (check == true) {
				nameLabel = new JLabel("Package successfully cancelled!!");
				nameFont = new Font("Helvetica", Font.BOLD, 16);
			} else {
				nameLabel = new JLabel("Could not find the package with given id : " + packageID);
				nameFont = new Font("Helvetica", Font.BOLD, 16);
			}
			nameLabel.setFont(nameFont);
			thePanel.add(nameLabel);
			resultPanel.add(thePanel);
			SwingUtilities.updateComponentTreeUI(frame);

		}

		if (e.getActionCommand().contains("support")) {
			System.out.println("Customer Support called" + e.getActionCommand());
			Box theBox = Box.createVerticalBox();

			JPanel tempPanel = new JPanel();

			JLabel lblName = new JLabel("Phone Number: +1 716 563 0152 ");
			lblName.setBounds(65, 68, 46, 14);
			tempPanel.add(lblName);
			theBox.add(tempPanel);
			tempPanel = new JPanel();
			JLabel lblPhone = new JLabel("Address: 165 Niagara Falls Boulevard, Buffalo NY - 14226");
			lblPhone.setBounds(65, 68, 46, 14);
			tempPanel.add(lblPhone);
			theBox.add(tempPanel);

			tempPanel = new JPanel();
			JLabel lblEmailId = new JLabel("Email Id: skumar28@buffalu.edu, achopra6@buffalo.edu");
			lblEmailId.setBounds(65, 115, 46, 14);
			tempPanel.add(lblEmailId);
			theBox.add(tempPanel);
			thePanel.add(theBox);
			resultPanel.add(thePanel);

			SwingUtilities.updateComponentTreeUI(frame);

		}
	}

}
