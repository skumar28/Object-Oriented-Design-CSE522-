package holidays.services;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen implements ActionListener {

	JTextField userNameTextField;
	JPasswordField passwordPasswordField;
	JFrame frame = new JFrame();
	Container contentPane = frame.getContentPane();
	JPanel panel = new JPanel();
	JButton signInButton;
	JButton signUpButton;

	LoginScreen() {

		contentPane.add(panel, BorderLayout.CENTER);
		
		Box theBox = Box.createVerticalBox();
		JPanel tempPanel = new JPanel();

		JLabel applicationName = new JLabel();
		applicationName.setText("<html><h2>Holidays Booking Management System</h2></html>");
		// applicationName.setHorizontalAlignment(SwingConstants.CENTER);
		tempPanel.add(applicationName);
		theBox.add(tempPanel);
		 tempPanel = new JPanel();
		JLabel selectLabel = new JLabel();
		selectLabel.setText("<html><h3>If you are new user, select SIGNUP else select SIGNIN</h3></html>");
		// selectLabel.setHorizontalAlignment(SwingConstants.CENTER);			
		tempPanel.add(selectLabel);
		theBox.add(tempPanel);
		tempPanel = new JPanel();
		Box theBox2 = Box.createHorizontalBox();
		signInButton = new JButton("SIGNIN");
		signUpButton = new JButton("SIGNUP");
		theBox2.add(signInButton);
		theBox2.add(signUpButton);
		tempPanel.add(theBox2);
		theBox.add(tempPanel);
		
		signUpButton.setActionCommand("1");
		signInButton.setActionCommand("2");

		signUpButton.addActionListener(this);
		signInButton.addActionListener(this);
		//theBox.add(theBox2);
		panel.add(theBox);

		
		frame.setBounds(200, 100, 1400, 800);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {

		int action = Integer.parseInt(e.getActionCommand());

		switch (action) {
		case 1:

			frame.setVisible(false);
			frame.dispose();
			new LoginSignUpScreen();
			break;

		case 2:
			// doSomething;
			frame.setVisible(false);
			frame.dispose();
			new LoginSignInScreen();
			break;
		}
	}
}
