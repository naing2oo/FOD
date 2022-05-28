package Admin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Design.Button;
import Design.ImageButton;
import Design.Label;
import Server.Server;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author NaingNaingOo This class is to show admin login screen.
 */
public class AdminLogin extends JFrame implements ActionListener {

	int sh_status = 0;// set a status for showing or hiding password character
	String imgpath = "C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image
	JPasswordField Password;
	JTextField Name;
	JButton showorhide, btnOk;

	public AdminLogin() {

		this.setTitle("Admin Login");
		// a panel to add all component
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		// create components
		JLabel lblName = new Label("Name", Color.black, 20);
		JLabel lblPassword = new Label("Password", Color.black, 20);
		Name = new JTextField();
		Password = new JPasswordField();
		Password.setEchoChar('*');
		showorhide = new ImageButton("show");// create a button with a picture,show.png for showing or hiding password
												// field character
		btnOk = new Button("OK");
		// set location and size of components
		lblName.setBounds(28, 21, 78, 42);
		lblPassword.setBounds(28, 82, 107, 32);
		Name.setBounds(129, 29, 151, 31);
		Password.setBounds(129, 83, 151, 31);
		showorhide.setBounds(280, 82, 40, 32);
		btnOk.setBounds(117, 149, 70, 32);
		// set border for name and password field
		Name.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Password.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		// add button actionListener
		showorhide.addActionListener(this);
		btnOk.addActionListener(this);
		// add component to panel
		panel.add(showorhide);
		panel.setLayout(null);
		panel.add(lblName);
		panel.add(lblPassword);
		panel.add(Name);
		panel.add(Password);
		panel.add(btnOk);
		add(panel);// add panel to frame
		setSize(345, 230);// set frame size
		setVisible(true);// set frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exit when closed
		setLocationRelativeTo(null);// set frame's location to center of screen
	}// end of constructor

	private boolean checkNameandPassword() {// function to check name and password of admin
		String name = "", password = "";
		try {
			Server server = new Server();// server object for database operation
			String sql = "SELECT Name,Password FROM admin";// get name and password from db
			server.connectionCreate();// database connection create
			ResultSet data = server.select(sql);// get data from db
			while (data.next()) {
				name = data.getString("Name");// set Name from db
				password = data.getString("Password");// set password from db
			}
			server.connectionClose();// db connection close
			if (name.equals(Name.getText()) && password.equals(Password.getText())) {
				// if name and password are equal with db data, show message dialog
				JOptionPane.showMessageDialog(null, "Welcome !", "You\'re welcome", JOptionPane.PLAIN_MESSAGE);
				return true;
			} else {
				// show message dialog
				JOptionPane.showMessageDialog(null, "Incorrect Name or Password ! ", "Wrong Information",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}// end of checkNameandPassword function

	public static void main(String[] args) {
		new AdminLogin();// run the frame
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showorhide) {
			/*
			 * when sh_status is 0, the character of password field is set to normal
			 * character and set picture of button hide.png and change the status of
			 * sh_status to 1 when sh_status is 1 the character of password filed is set to
			 * * and set picture of button show.png and change the status of sh_status to 0
			 */
			if (sh_status == 0) {
				Password.setEchoChar((char) 0);
				showorhide.setIcon(new ImageIcon(imgpath + "hide.png"));
				sh_status = 1;

			} else {
				Password.setEchoChar('*');
				showorhide.setIcon(new ImageIcon(imgpath + "show.png"));
				sh_status = 0;

			}

		} else if (e.getSource() == btnOk)
			if (checkNameandPassword()) {
				new AdminHome();
				dispose();
			}
	}// end of actionPerformed
}// end of class