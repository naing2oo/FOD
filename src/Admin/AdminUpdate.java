package Admin;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Design.Button;
import Design.Label;
import Server.Server;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * @author NaingNaingOo
 * This class is for a panel to show admin update information
 *
 */
public class AdminUpdate extends JPanel {
	int sh_status = 0;//status for password show or hide button
	Server server = new Server();//server object for db operation
	private JTextField Name;//a textfield for name
	private JPasswordField newPassword,reTypePassword;//set three password filed to change new password 
	public JButton update;//a update button for update operation
	String currentPassword="";

	public AdminUpdate() {
		//setting panel
		setLayout(null);
		setBackground(Color.WHITE);
		//creating label to show which text or password filed is for what
		JLabel lblName = new Label("Name", Color.black, 20);
		JLabel lblNewPassword = new Label("New Password", Color.black, 20);
		JLabel lblRetypePassword = new Label("Re-type Password", Color.black, 20);
		
		Name = new JTextField(getNamefromDB());//create name text field with admin name from db
		newPassword = new JPasswordField();// create password filed for new password
		reTypePassword = new JPasswordField();
		//set char of password field
		newPassword.setEchoChar('*');
		reTypePassword.setEchoChar('*');
		//set border of text field and password field
		Name.setBorder(new LineBorder(new Color(255, 255, 0)));
		newPassword.setBorder(new LineBorder(new Color(255, 255, 0)));
		reTypePassword.setBorder(new LineBorder(new Color(255, 255, 0)));
		//a button to show character of password fields
		JButton showPasswords = new Button("Show All Passwords");
		showPasswords.setForeground(Color.black);
		// creating update button, this button's action will be performed in AdminHome class
		update = new Button("Update");
		update.setForeground(Color.black);
		//set size and location of all components
		lblName.setBounds(73, 35, 100, 30);
		lblNewPassword.setBounds(73, 115, 136, 30);
		lblRetypePassword.setBounds(73, 155, 161, 30);
		Name.setBounds(240, 35, 150, 30);
		newPassword.setBounds(240, 115, 150, 30);
		reTypePassword.setBounds(240, 155, 150, 30);
		update.setBounds(284, 235, 106, 30);
		showPasswords.setBounds(101, 235, 148, 30);
		//add all components to panel
		this.add(lblName);
		this.add(lblNewPassword);
		this.add(lblRetypePassword);
		this.add(Name);
		this.add(newPassword);
		this.add(reTypePassword);
		this.add(showPasswords);
		this.add(update);
		
		JLabel lblChangePs = new Label("<html><i>To Change Password</i></html>",Color.BLACK,20);
		lblChangePs.setBounds(73, 76, 209, 28);
		add(lblChangePs);
		//set character of all password field
		showPasswords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (sh_status == 0) {
					newPassword.setEchoChar((char) 0);
					reTypePassword.setEchoChar((char) 0);
					showPasswords.setText("Hide All Passowords");
					sh_status = 1;

				} else {
					newPassword.setEchoChar('*');
					reTypePassword.setEchoChar('*');
					showPasswords.setText("Hide All Passowords");
					sh_status = 0;

				}
			}
		});
	}//end of constructor
	
	private String getNamefromDB() {// a function to get admin name from db
		String getname="";//set a empty string to return
		try {
			server.connectionCreate();//create connection
			String sql="select name from admin";//sql statement to get name from admin
			ResultSet name=server.select(sql);//get result set from db using server class's select function
			while(name.next()) {
				getname=name.getString("name");//set getname variable with name from db
			}
			server.connectionClose();//connection close
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getname;//return name
	}//end of getNamefromDB function

	public boolean checkPassword() {// a function to check user inputed current password is true or false
		String passwordfromdb = "";//set a empty string for password
		currentPassword = JOptionPane.showInputDialog(this,"Enter Current Password", "Require Password", JOptionPane.PLAIN_MESSAGE);
		try {
			server.connectionCreate();
			String sql = "select password from admin";
			ResultSet data = server.select(sql);
			while (data.next())
				passwordfromdb = data.getString("password");
			server.connectionClose();
			if (passwordfromdb.equals(currentPassword)) {// if password form db and user inputed current password are equal
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect Password", "Wrong Current Password!",
						JOptionPane.ERROR_MESSAGE);//show a message
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}// end of checkPassword

	public boolean checkEqualPassword() {//a function to check new password and re type password are equal or not
		if (newPassword.getText().equals(reTypePassword.getText())) {//if passwords are equal
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "New password and Re-type Password do not match!!", "Match Password",
					JOptionPane.WARNING_MESSAGE);//if not equal, give a warning message 
			return false;
		}
	}//end of checkEqualPassword function

	public boolean update() {// a function to update name and password of admin
		String sql;
		if (newPassword.getText().equals(""))//if password does not want to change
			sql = "UPDATE admin SET Name='" + Name.getText() + "' WHERE password='" + currentPassword
					+ "'";
		else
			sql = "UPDATE admin SET Name= '" + Name.getText() + "', Password= '" + newPassword.getText()
					+ "' WHERE password='" + currentPassword+ "'";
		server.connectionCreate();
		if (server.Update(sql)) {//if update is ok
			JOptionPane.showMessageDialog(null, "Successfully Update!!\n \tPlease Login Again with Updated data", "Update Complete",
					JOptionPane.PLAIN_MESSAGE);//show a message
			server.connectionClose();
			return true;
		} else
			return false;
	}//end of update function
}//end of update class