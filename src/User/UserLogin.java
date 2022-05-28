package User;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import Design.Button;
import Design.ImageButton;
import Design.Label;
import Server.Server;

import java.awt.Color;

/**
 * @author NaingNaingOo This class is to show user login page.
 *
 */
public class UserLogin extends JFrame implements ActionListener {
	JTextField phone;// text field to enter user registered phone number
	JPasswordField password;// password field to enter user's password
	Button Login, Cancel, Register;// 3 Buttons for login function , Register function or Cancel
	int sh_status = 0;// set a status for showing or hiding password character
	int ID = 0;// to set user ID
	String imgpath="C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image

	public UserLogin() {

		this.setTitle("Login");// title of frame

		JPanel contentPane = new JPanel();// a panel is created to add component
		contentPane.setBackground(Color.WHITE);// set panel color to white
		contentPane.setLayout(null);// set null layout
		// creating labels to show which text or password filed is for what and give
		// instruction when user has no account
		Label lb1 = new Label("Password", Color.BLACK, 18);
		Label lb2 = new Label("Phone Number", Color.BLACK, 18);
		Label lb3 = new Label("If you don’t have account, please register!!", Color.red, 20);

		phone = new JTextField("09");// create text field to enter phone number
		password = new JPasswordField(50);// create password field to enter password
		password.setEchoChar('*');// set password field character as *

		// set border of two field for design purpose
		phone.setBorder(BorderFactory.createLineBorder(Color.blue));
		password.setBorder(BorderFactory.createLineBorder(Color.blue));

		// create 3 buttons with meanig_full text
		Login = new Button("Login");
		Cancel = new Button("Cancel");
		Register = new Button("Register");
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
					password.setEchoChar((char) 0);
					showorhide.setIcon(new ImageIcon(imgpath+"hide.png"));
					sh_status = 1;

				} else {
					password.setEchoChar('*');
					showorhide.setIcon(new ImageIcon(imgpath+"show.png"));
					sh_status = 0;
				}
			}
		});
		// set location and size of all components
		lb1.setBounds(20, 70, 100, 30);
		lb2.setBounds(20, 20, 130, 30);
		lb3.setBounds(20, 165, 368, 30);
		phone.setBounds(170, 20, 130, 30);
		password.setBounds(170, 70, 130, 36);
		showorhide.setBounds(310, 71, 30, 30);
		Login.setBounds(60, 120, 78, 36);
		Cancel.setBounds(240, 120, 78, 36);
		Register.setBounds(140, 206, 94, 44);
		// add button's action listener
		Login.addActionListener(this);
		Cancel.addActionListener(this);
		Register.addActionListener(this);
		
		phone.addKeyListener(new KeyListener() {

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
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		// add all components to panel
		contentPane.add(lb1);
		contentPane.add(lb2);
		contentPane.add(lb3);
		contentPane.add(showorhide);
		contentPane.add(phone);
		contentPane.add(password);
		contentPane.add(Login);
		contentPane.add(Cancel);
		contentPane.add(Register);

		getContentPane().add(contentPane);// add panel to frame
		this.setVisible(true);// set frame visible
		this.setSize(390, 300);// set size of frame
		this.setLocationRelativeTo(null);// set frame's location to center of screen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end of constructor
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Cancel) {// if Cancel button is clicked
			phone.setText("09");// set text filed for phone empty
			password.setText("");// set password field empty
		} else if (e.getSource() == Login) {// if Login button is clicked
			if (checkPhoneFill()) {
				if (checkUserLogin()) {
					dispose();// close the frame
					new UserHome(ID);// go to userHome frame with login user ID
				}
			}
		} else {// if Register button is clicked
			dispose();//close the frame
			new UserRegister();// got to Register frame
		}
	}// end of actionPerformed

	private Boolean checkPhoneFill() {// this function is to check phone number is empty or not
		Boolean checked = false;// a boolean to set true if phone number is filled, false if phone number is empty
		checked = (!phone.getText().equals("")) ? true : false;// set checked true or false
		if (!checked) {// if checked is false
			// show a message dialog to inform user
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			JOptionPane.showMessageDialog(null, "Please Enter Phone number !", "Blank ", JOptionPane.WARNING_MESSAGE);
		}
		return checked;// return checked
	}//end of checkPhoneFill function

	private Boolean checkUserLogin() {// this function is to check phone number and password user filled
		Server server = new Server();// create an object of server class to perform database connection and operation
		Boolean phnoexit = false, // set a boolean phonexit,to check user input phno is exit in db or not, false
				toretrun = false;// set a boolean toreturn,to return true or false form function, false
		String pas = "";// set pas empty string, this string is to set password from database
		try {
			server.connectionCreate();
			ResultSet phno = server.select("select Phno from customer WHERE Phno="+phone.getText());
			phnoexit=(phno.next())?true:false;//set phnoexit true if phone number exit in database else false
			

			if (!phnoexit) {// if phone number doesn't exit
				// show a message dialog
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				JOptionPane.showMessageDialog(null, "This number is not registered !", "Unknown Ph number",
						JOptionPane.WARNING_MESSAGE);
				toretrun = false;// set toreturn false
				return toretrun;// return the boolean,toreturn
			} else {// if phone number exits
				 
				ResultSet data = server.select("select id,Password  from customer WHERE Phno="+phone.getText());
				
				while (data.next()) {
					ID = data.getInt("id");//set ID with user id from database
					pas = data.getString("Password");//set pas with password form database 
				}
				server.connectionClose();
				if (pas.equals(password.getText())) {//if password is true					
					ImageIcon icon = new ImageIcon(imgpath+"welcome.png");//set Image with welcome.png
					
					UIManager.put("OptionPane.messageFont",
							new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));//change dialog box's font 
					UIManager.put("OptionPane.background", new ColorUIResource(Color.white));// change option  pane color of dialog box 
					UIManager.put("Panel.background", new ColorUIResource(Color.white));//change panel background color
					
					JOptionPane.showMessageDialog(null, "Welcome !!", "You\'re welcome",
							JOptionPane.INFORMATION_MESSAGE, icon);//show message dialog with image

					toretrun = true;//set toreturn true
					return toretrun;//return the boolean,toreturn
				} else {//if password is false
					//show message dialog
					UIManager.put("OptionPane.messageFont",
							new FontUIResource(new Font("Adobe Garamond Pro Bold", Font.BOLD, 18)));
					JOptionPane.showMessageDialog(null, "Incorrect Password ! ", "Wrong Password",
							JOptionPane.WARNING_MESSAGE);

					toretrun = false;//set toreturn false
					return toretrun;//return the boolean,toreturn
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toretrun;//return the boolean,toreturn
	}//end of checkUserLogin function
}//end of class