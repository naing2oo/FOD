package User;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
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

/**
 * @author NaingNaingOo This class is to show user Registration page.
 *
 */
public class UserRegister extends JFrame implements ActionListener, KeyListener {
	int sh_status = 0;// set a status for showing or hiding password character
	JTextField Name, Phone, Street;// text field to enter name, phone number, street and show city
	JPasswordField Password;// a password field to enter password
	JRadioButton Male, Female;// two radio button for gender
	ButtonGroup BG;// button group for gender
	JComboBox Quarter;// a ComboBox for Quarter
	JButton Back, Register;// Two button one for to go back and one for registration
	JLabel lblReqName, lblReqPhone, lblReqPass, lblReqGender, lblReqQuarter, lblReqStreet;
	String imgpath = "C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image
	Server server = new Server();// create server object to connect database

	public UserRegister() {

		this.setTitle("Registration Form");// set title of form

		JPanel contentPane = new JPanel();// a panel is created to add component
		contentPane.setBackground(Color.WHITE);// set panel color to white
		contentPane.setLayout(null);// set null layout
		// creating labels to show which text or password filed is for what
		JLabel lblName = new Label("Name", Color.MAGENTA, 20);
		JLabel lblPhone = new Label("Phone", Color.MAGENTA, 20);
		JLabel lblPassword = new Label("Create Password", Color.MAGENTA, 20);
		JLabel lblGender = new Label("Gender", Color.MAGENTA, 20);
		JLabel lblAddress = new Label("Address", Color.MAGENTA, 20);
		JLabel lblStreet = new Label(">>  Street", Color.ORANGE, 20);
		JLabel lblQuarter = new Label(">>  Quarter", Color.ORANGE, 20);
		JLabel lblCity = new Label(">>  City", Color.ORANGE, 20);

		lblReqName = new Label("Please Enter Name", Color.RED, 18);
		lblReqPhone = new Label("Please Enter Valid Number", Color.RED, 18);
		lblReqPass = new Label("Please Enter Password", Color.RED, 18);
		lblReqGender = new Label("Please Choose Gender", Color.RED, 18);
		lblReqQuarter = new Label("Please Select Quarter", Color.RED, 18);
		lblReqStreet = new Label("Please Enter Street", Color.RED, 18);

		Name = new JTextField();// create text field to enter name
		Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Phone = new JTextField("09");// create text field with "09" to enter phone number
		Phone.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Password = new JPasswordField();// create password field to enter password
		Password.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		Password.setEchoChar('*');// set password field character as *
		Street = new JTextField();// create text field to enter Street
		Street.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		JTextField City = new JTextField(" Monywa");// create text field to show city
		City.setEditable(false);// make city text filed can't edit
		City.setBackground(Color.WHITE);// set city text field background color white
		City.setFont(new Font("Times New Roman", Font.ITALIC, 20));// set font of city text Field
		// set border of text field for design purpose
		Name.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Phone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Password.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Street.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		City.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		Male = new RadioButton("Male", 20);// create radio button for gender male
		Female = new RadioButton("Female", 20);// create radio button for gender female
		BG = new ButtonGroup();// create button group for two gender radio button
		BG.add(Male);
		BG.add(Female);// add two gender radio button to button group

		Quarter = new JComboBox(new QuarterAndPromo().GetQuarterWithHeading());// create comboxBox for Quarter with
																				// value return from GetQuarter function
		Quarter.setFont(new Font("Times New Roman", Font.PLAIN, 20));// set font of comboBox
		Quarter.setBackground(Color.white);// set background of comboBox white
		Quarter.setFocusable(false);// set focus of comboBox false
		Quarter.setMaximumRowCount(5);// set maximum row 5

		Back = new ImageButton("back");// a image button to go back
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

		Register = new Button("Register");// a button for register function
		// set location and size of all components
		Back.setBounds(0, 0, 50, 50);
		lblName.setBounds(60, 29, 60, 50);
		lblPhone.setBounds(60, 110, 80, 40);
		lblPassword.setBounds(60, 175, 168, 40);
		lblGender.setBounds(60, 235, 90, 40);
		lblAddress.setBounds(60, 290, 90, 50);
		lblStreet.setBounds(160, 290, 107, 40);
		lblQuarter.setBounds(160, 355, 120, 40);
		lblCity.setBounds(160, 415, 90, 50);
		lblReqName.setBounds(324, 11, 158, 24);
		lblReqPhone.setBounds(321, 76, 211, 40);
		lblReqPass.setBounds(324, 142, 179, 35);
		lblReqGender.setBounds(324, 210, 179, 24);
		lblReqQuarter.setBounds(324, 321, 179, 40);
		lblReqStreet.setBounds(324, 260, 158, 35);
		Name.setBounds(324, 38, 179, 40);
		Phone.setBounds(324, 110, 179, 35);
		Password.setBounds(324, 175, 179, 35);
		Male.setBounds(324, 235, 80, 35);
		Female.setBounds(403, 235, 100, 35);
		Quarter.setBounds(324, 355, 179, 35);
		Street.setBounds(324, 290, 179, 35);
		City.setBounds(324, 425, 179, 35);
		showorhide.setBounds(502, 175, 30, 30);
		Register.setBounds(431, 470, 95, 45);
		// add all components to panel
		contentPane.add(Back);
		contentPane.add(lblName);
		contentPane.add(lblPhone);
		contentPane.add(lblPassword);
		contentPane.add(lblGender);
		contentPane.add(lblAddress);
		contentPane.add(lblStreet);
		contentPane.add(lblReqName);
		contentPane.add(lblReqPhone);
		contentPane.add(lblReqPass);
		contentPane.add(lblReqGender);
		contentPane.add(lblReqQuarter);
		contentPane.add(lblReqStreet);
		contentPane.add(lblQuarter);
		contentPane.add(lblCity);
		contentPane.add(Name);
		contentPane.add(Phone);
		contentPane.add(Password);
		contentPane.add(Male);
		contentPane.add(Female);
		contentPane.add(Quarter);
		contentPane.add(Street);
		contentPane.add(City);
		contentPane.add(showorhide);
		contentPane.add(Register);

		lblReqName.setVisible(false);
		lblReqPhone.setVisible(false);
		lblReqPass.setVisible(false);
		lblReqGender.setVisible(false);
		lblReqQuarter.setVisible(false);
		lblReqStreet.setVisible(false);
		getContentPane().add(contentPane);// add panel to frame
		// add button's action listener
		Back.addActionListener(this);
		Register.addActionListener(this);
		Phone.addKeyListener(this);

		this.setSize(575, 565);// set frame size
		this.setLocationRelativeTo(null);// set frame's location to center of screen
		this.setVisible(true);// set frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// end of constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Back) {// if back button is clicked
			dispose();// close the frame
			new UserLogin();// go to UserLogin Screen
		} else {// if register button is clicked
			if (checkRequiredField()) // if all data are filled
				if (AddUser()) {// if User successfully register
					dispose();// close the frame
					new UserLogin();// go to UserLogin Screen
				}
		}
	}// end of actionPerformed

	private Boolean checkRequiredField() {// a function to check required field

		Boolean isFilled = false;// create a boolean variable to return and set false
		String phno = Phone.getText();// set phno string with text in phone text filed
		
		String foramt="\\d{11}|\\d{9}";
		boolean truephoneformat = Pattern.matches(foramt, phno);															

		if (Name.getText().equals("") || truephoneformat ==false || Password.getText().equals("") || BG.getSelection() == null
				|| Street.getText().equals("") || Quarter.getSelectedIndex() == 0)
			isFilled = false;
		else
			isFilled = true;

		if (Name.getText().equals(""))
			lblReqName.setVisible(true);
		else
			lblReqName.setVisible(false);
		if (truephoneformat ==false)
			lblReqPhone.setVisible(true);
		else
			lblReqPhone.setVisible(false);
		if (Password.getText().equals(""))
			lblReqPass.setVisible(true);
		else
			lblReqPass.setVisible(false);
		if (BG.getSelection() == null)
			lblReqGender.setVisible(true);
		else
			lblReqGender.setVisible(false);
		if (Street.getText().equals(""))
			lblReqStreet.setVisible(true);
		else
			lblReqStreet.setVisible(false);
		if (Quarter.getSelectedIndex() == 0)
			lblReqQuarter.setVisible(true);
		else
			lblReqQuarter.setVisible(false);
		return isFilled;
	}// end of checkRequiredField function

	private Boolean AddUser() {// a function to add user to customer table and return true if adding to table
								// has no error else return false
		String name = Name.getText();// set name String with user filled text in Name text field
		String phno = Phone.getText();// set phone String with user filled text in Phone text field
		String password = Password.getText();// set password String with user filled text in password text field
		String gender = (Male.isSelected()) ? "Male" : "Female";// set gender String as Male if Male radioButton is
																// selected else Female
		String street = Street.getText();// set street String with user filled text in Street text field
		String Qua = (String) Quarter.getSelectedItem();// set Qua String with user selected text in Quarter ComboBox
		String Duplicate = "";// set Duplicate String "",this string is to use phone number is Duplicate
		Boolean ok = false;// create a boolean variable to return and set as false
		PreparedStatement sql = null;// a PreparedStatement for dataBase query
		try {
			server.connectionCreate();
			sql = (PreparedStatement) server.con.prepareStatement(
					"INSERT INTO Customer (Name,Phno,Password,Gender,Street,Quarter) values(?,?,?,?,?,?)");
			sql.setString(1, name);// set first value name
			sql.setString(2, phno);// set second value phno
			sql.setString(3, password);// set third value password
			sql.setString(4, gender);// set fourth value gender
			sql.setString(5, street);// set fifth value street
			sql.setString(6, Qua);// set sixth value Qua

		} catch (SQLException e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
		Duplicate = server.UpdatetoUniquevaluetable(sql);// add data into customer table

		server.connectionClose();
		if (Duplicate.equals("")) {// if Duplicate equals ""(empty string)
			ok = true;// set ok true
			// give a Information Message Dialog
			ImageIcon icon = new ImageIcon(imgpath + "thank you.jpg");
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
			UIManager.put("Panel.background", new ColorUIResource(Color.white));
			JOptionPane.showMessageDialog(null, " May I help you with your meal", "Thank You",
					JOptionPane.INFORMATION_MESSAGE, icon);
			return ok;// return ok
		} else {// if Duplicate has string value
			ok = false;// set ok false
			// give warning message
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			JOptionPane.showMessageDialog(null, "This number is already regristered!", "Used Ph number",
					JOptionPane.WARNING_MESSAGE);
			return ok;// return ok
		}
	}// end of AddUser function

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