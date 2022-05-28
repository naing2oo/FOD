package User;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import Common.QuarterAndPromo;
import Design.Button;
import Design.ImageButton;
import Design.Label;
import Design.RadioButton;
import Server.Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author NaingNaingOo This panel is to show User update Profile.
 *
 */
public class UserUpdate extends JPanel implements ActionListener, KeyListener {
	int sh_status = 0;// set a status for showing or hiding password character
	JTextField Name, Phone, Street;// text field to enter name, phone number, street and show city
	JPasswordField Password;// a password field to enter password
	JRadioButton Male, Female;// two radio button for gender
	ButtonGroup BG;// button group for gender
	JComboBox Quarter;// a ComboBox for Quarter
	String userName, userPs, userPhno, userGender, userstreet, userQuarter;// string to set value from database
	String[] quart;// a string array to set quarter from database
	JButton Update;// a button to perform update function
	JLabel lblReqName, lblReqPhone, lblReqPass, lblReqStreet;
	Server server = new Server();// create server object to connect database
	String imgpath = "C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image
	int userid = 0;// to set user login id

	public UserUpdate(int id) {
		userid = id;// set user id with login user id
		setBackground(Color.WHITE);// set panel background color white
		setLayout(null);// set panel layout null

		Color labelcolor = new Color(204, 0, 102);// create a color for label
		// create labels to show which text or password filed is for what
		JLabel lblname = new Label("Your Name ", labelcolor, 20);
		JLabel lblpassword = new Label("Your Password", labelcolor, 20);
		JLabel lblphno = new Label("Your ph number", labelcolor, 20);
		JLabel lblgender = new Label("Your gender", labelcolor, 20);
		JLabel lbladdress = new Label("Your address", labelcolor, 20);
		JLabel lblStreet = new Label(">>  Street", labelcolor, 20);
		JLabel lblQuarter = new Label(">>  Quarter", labelcolor, 20);
		JLabel lblCity = new Label(">>  City", labelcolor, 20);
		lblReqName = new Label("Please Enter Name", Color.RED, 18);
		lblReqPhone = new Label("Please Enter Valid Phone Number", Color.RED, 18);
		lblReqPass = new Label("Please Enter Password", Color.RED, 18);
		lblReqStreet = new Label("Please Enter Street", Color.RED, 18);

		UserInfo();// set user information by calling UserInfo() function
		Name = new JTextField();
		Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Name.setText(userName);
		Password = new JPasswordField();
		Password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Password.setEchoChar('*');
		Password.setText(userPs);
		Phone = new JTextField();
		Phone.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Phone.setText(userPhno);
		Street = new JTextField();
		Street.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Street.setText(userstreet);

		Name.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Password.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Phone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Street.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		Male = new RadioButton("Male", 20);// create radio button for gender male
		Female = new RadioButton("Female", 20);// create radio button for gender female
		BG = new ButtonGroup();// create button group for two gender radio button
		BG.add(Male);
		BG.add(Female);// add two gender radio button to button group
		if (userGender.equals("Male")) {
			Male.setSelected(true);
		} else {
			Female.setSelected(true);
		}

		Quarter = new JComboBox(new QuarterAndPromo().GetQuarter());
		Quarter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Quarter.setBackground(Color.white);
		Quarter.setFocusable(false);
		Quarter.setMaximumRowCount(5);
		Quarter.setSelectedItem(userQuarter);

		JTextField city = new JTextField("Monywa");
		city.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		city.setEditable(false);
		city.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		city.setBackground(Color.white);
		city.setForeground(labelcolor);

		/*
		 * create a button for showing or hiding password field character it is created
		 * with a picture,show.png and all of design are changed. add action listener
		 * with inner function that perform when sh_status is 0, the character of
		 * password field is set to normal character and set picture of button hide.png
		 * and change the status of sh_status to 1 when sh_status is 1 the character of
		 * password filed is set to * and set picture of button show.png and change the
		 * status of sh_status to 0
		 */
		JButton showorhide = new ImageButton("show");
		showorhide.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (sh_status == 0) {
					Password.setEchoChar((char) 0);
					showorhide.setIcon(new ImageIcon(imgpath + "hide.png"));
					sh_status = 1;

				} else {
					Password.setEchoChar('*');
					showorhide.setIcon(new ImageIcon(imgpath + "show.png"));
					sh_status = 0;
				}
			}
		});
		Update = new Button("Update");
		Update.addActionListener(this);
		// set size and location of components
		lblname.setBounds(100, 55, 150, 50);
		Name.setBounds(330, 60, 170, 40);
		lblpassword.setBounds(100, 124, 150, 50);
		Password.setBounds(330, 129, 170, 40);
		showorhide.setBounds(510, 137, 40, 32);
		lblphno.setBounds(100, 199, 133, 40);
		Phone.setBounds(330, 199, 170, 40);
		lblgender.setBounds(100, 260, 108, 50);
		Male.setBounds(330, 268, 67, 35);
		Female.setBounds(399, 268, 87, 35);
		lbladdress.setBounds(100, 321, 120, 50);
		lblStreet.setBounds(230, 329, 94, 35);
		Street.setBounds(330, 329, 170, 40);
		lblQuarter.setBounds(230, 384, 108, 50);
		Quarter.setBounds(330, 395, 170, 40);
		lblCity.setBounds(230, 445, 87, 50);
		city.setBounds(330, 455, 170, 30);
		lblReqName.setBounds(330, 28, 170, 30);
		lblReqPhone.setBounds(330, 171, 270, 30);
		lblReqPass.setBounds(330, 100, 170, 30);
		lblReqStreet.setBounds(330, 298, 170, 30);
		Update.setBounds(506, 522, 94, 50);
		// add components to panel
		add(lblname);
		add(Name);
		add(lblphno);
		add(Phone);
		add(lblgender);
		add(lblpassword);
		add(Password);
		add(showorhide);
		add(Male);
		add(Female);
		add(lbladdress);
		add(lblStreet);
		add(Street);
		add(lblQuarter);
		add(Quarter);
		add(lblCity);
		add(city);
		add(lblReqName);
		add(lblReqPhone);
		add(lblReqPass);
		add(lblReqStreet);
		add(Update);
		Phone.addKeyListener(this);
		lblReqName.setVisible(false);
		lblReqPass.setVisible(false);
		lblReqPhone.setVisible(false);
		lblReqStreet.setVisible(false);
		setVisible(true);
	}// end of constructor

	private void UserInfo() {// a function to get user information form db

		try {
			server.connectionCreate();
			String sql = "select Name,Phno,Password,Gender,Street,Quarter from customer WHERE id=" + userid;
			ResultSet info = server.select(sql);

			while (info.next()) {

				userName = info.getString("Name");
				userPs = info.getString("Password");
				userPhno = info.getString("Phno");
				userGender = info.getString("Gender");
				userstreet = info.getString("Street");
				userQuarter = info.getString("Quarter");
			}
			server.connectionClose();
		} catch (Exception e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
	}// end of UserInfo function

	@Override
	public void actionPerformed(ActionEvent e) {
		if (checkRequiredField()) {
			String password = JOptionPane.showInputDialog(this, "Enter Current Password", "Require Password",
					JOptionPane.PLAIN_MESSAGE);
			if (password.equals(userPs))
				updateUser();
			else
				JOptionPane.showMessageDialog(null, "Wrong Password", "Error!", JOptionPane.ERROR_MESSAGE);
		}

	}// end of actionPerformed

	private Boolean checkRequiredField() {// a function to check required field

		Boolean isFilled = false;// create a boolean variable to return and set false
		String phno = Phone.getText();// set phno string with text in phone text filed
		String foramt="\\d{11}|\\d{9}";
		boolean truephoneformat = Pattern.matches(foramt, phno);																
		if (Name.getText().equals("") || truephoneformat ==false || Password.getText().equals("") || Street.getText().equals(""))
			isFilled = false;
		else
			isFilled = true;
		if (Name.getText().equals("")) {// if name text field is blank
			lblReqName.setVisible(true);
		} else
			lblReqName.setVisible(false);
		if (truephoneformat ==false) {
			lblReqPhone.setVisible(true);
		} else
			lblReqPhone.setVisible(false);
		if (Password.getText().equals("")) {
			lblReqPass.setVisible(true);
		} else
			lblReqPass.setVisible(false);
		if (Street.getText().equals("")) {
			lblReqStreet.setVisible(true);
		} else
			lblReqStreet.setVisible(false);
		return isFilled;
	}// end of checkRequiredField function

	private void updateUser() {
		String gender = (Male.isSelected()) ? "Male" : "Female";// set gender String as Male if Male radioButton is
																// selected else Female
		PreparedStatement sql = null;
		String Duplicate = "";// set Duplicate String "",this string is to use phone number is Duplicate
		try {
			server.connectionCreate();
			sql = (PreparedStatement) server.con.prepareStatement(
					"UPDATE customer SET Name=?,Password=?,Phno=?,Gender=?,Street=?,Quarter=? WHERE id=?");
			sql.setString(1, Name.getText());
			sql.setString(2, Password.getText());
			sql.setString(3, Phone.getText());
			sql.setString(4, gender);
			sql.setString(5, Street.getText());
			sql.setString(6, (String) Quarter.getSelectedItem());
			sql.setInt(7, userid);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Duplicate = server.UpdatetoUniquevaluetable(sql);
		server.connectionClose();
		if (Duplicate.equals("")) {// if Duplicate equals ""(empty string)
			// give a Information Message Dialog
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
			UIManager.put("Panel.background", new ColorUIResource(Color.white));
			JOptionPane.showMessageDialog(null, "Successfully update", "Update", JOptionPane.INFORMATION_MESSAGE);
		} else {// if Duplicate has string value
			// give warning message
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			JOptionPane.showMessageDialog(null, "This number is already regristered!", "Used Ph number",
					JOptionPane.WARNING_MESSAGE);
		}
	}// end of updateUser function

	@Override
	public void keyTyped(KeyEvent e) {
		// allow only number input for phone text filed
		char input = e.getKeyChar();
		if (((input < '0') || (input > '9')) && (input != '\b')) {
			JOptionPane.showMessageDialog(null, "Number Only!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			e.consume();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}// end of class