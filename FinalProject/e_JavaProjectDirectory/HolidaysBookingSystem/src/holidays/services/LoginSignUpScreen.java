package holidays.services;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import holidays.LoginDetails.Login;
import holidays.customer.LoginInfo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;

public class LoginSignUpScreen implements ActionListener {

	JTextField unameField;
	JPasswordField passWord;

	JFrame frame = new JFrame();

	Container contentPane = frame.getContentPane();
	JPanel panel = new JPanel();

	JButton signUpButton;

	LoginSignUpScreen() {

		contentPane.add(panel, BorderLayout.CENTER);

		Box theBoxV = Box.createVerticalBox();
		JPanel tempPanel = new JPanel();

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(65, 68, 46, 14);
		tempPanel.add(lblName);
		unameField = new JTextField();
		unameField.setBounds(128, 65, 150, 20);
		tempPanel.add(unameField);
		unameField.setColumns(10);
		theBoxV.add(tempPanel);

		tempPanel = new JPanel();
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(65, 68, 46, 14);
		tempPanel.add(lblPhone);
		passWord = new JPasswordField();
		passWord.setBounds(128, 65, 150, 20);
		tempPanel.add(passWord);
		passWord.setColumns(10);
		theBoxV.add(tempPanel);

		signUpButton = new JButton("Signup");
		signUpButton.addActionListener(this);
		theBoxV.add(signUpButton);

		panel.add(theBoxV);

		frame.setBounds(200, 100, 1400, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {

		Login login = new Login();
		LoginInfo userInfo = new LoginInfo();
		boolean userStatus = userSignUp(login, userInfo);

		if (userStatus == false) {
			JOptionPane.showMessageDialog(null, "Username already exists!!");

			frame.setVisible(false);
			frame.dispose();
			new LoginScreen();
		} else {
			frame.setVisible(false);
			frame.dispose();
			new HolidaySearchFrame(unameField.getText());
			// search, select, book screen call
		}
	}

	private boolean userSignUp(Login login, LoginInfo userInfo) {

		userInfo.setUserName(unameField.getText());
		userInfo.setPassword(passWord.getText());
		if (userInfo.getUserName() == "" || userInfo.getPassword() == "")
			return false;
		boolean check = login.registerNewUser(userInfo);
		return check;

	}

}
