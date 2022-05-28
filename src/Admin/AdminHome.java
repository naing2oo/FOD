package Admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

import Common.Menu;
import Design.Label;
import Design.PlaneButton;

/**
 * @author NaingNaingOo this class is for Admin Home Screen
 */
public class AdminHome extends JFrame implements ActionListener {

	JSplitPane mainPane, managePane;
	JButton Manage, viewUser, viewOrder, updateProfile, Logout;
	JMenu Food, Dessert, Drink;
	JMenuItem delivery;

	AdminUpdate adminUpdate = new AdminUpdate();

	Font font = new Font("Times New Roman", Font.BOLD, 14);
	
	public AdminHome() {
		Dimension size = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());// get screen size

		this.setTitle("Admin Home");

		mainPane = new JSplitPane();
		// panel to add Manage,viewUser,viewOrder,updateProfile,LogOut buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 1));
		buttonPanel.setBackground(Color.white);
		// a panel for right hand side
		JPanel side = new JPanel();
		side.setLayout(new FlowLayout());
		side.setBackground(Color.white);
		// a label to add in side Panel
		JLabel opening = new Label("You can manage the whole system here!", Color.DARK_GRAY, 32);
		side.add(opening);
		// setting mainPane
		mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainPane.setLeftComponent(buttonPanel);
		mainPane.setRightComponent(side);
		mainPane.setDividerLocation(100);
		mainPane.setDividerSize(1);
		// creating buttons
		Manage = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp; Manage &nbsp;</p></html>", 16);
		viewUser = new PlaneButton("<html><p style='border:1px solid black;',align='center'>&nbsp;View User&nbsp;</p></html>", 16);
		viewOrder = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp; View Order&nbsp;</p>", 16);
		updateProfile = new PlaneButton("<html><p style='border:1px solid black;',align='center'>&nbsp;Update Profile&nbsp;</p>", 16);
		Logout = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp; Log out&nbsp;</p>", 16);
		// adding buttons to buttonPanel, left hand size of maninPane
		buttonPanel.add(Manage);
		buttonPanel.add(viewUser);
		buttonPanel.add(viewOrder);
		buttonPanel.add(updateProfile);
		buttonPanel.add(Logout);
		// add button actionListener
		Manage.addActionListener(this);
		viewUser.addActionListener(this);
		viewOrder.addActionListener(this);
		updateProfile.addActionListener(this);
		Logout.addActionListener(this);
		adminUpdate.update.addActionListener(this);

		this.add(mainPane);// add mainPane to frame
		setVisible(true);// set frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// exit when closed
		setSize(size.width, size.height);// set size with screen size
	}// end of constructor

	private JSplitPane managePane() {// a function for managePane
		managePane = new JSplitPane();
		// a panel for upper side of managePane
		JPanel up = new JPanel();
		up.setLayout(new GridLayout());
		up.setBackground(Color.white);
		// create JMenu
		Food = new JMenu("Food");
		Dessert = new JMenu("Dessert");
		Drink = new JMenu("Drink");
		JMenu other = new JMenu("Other");
		// set font for menu
		Food.setFont(font);
		Dessert.setFont(font);
		Drink.setFont(font);
		other.setFont(font);
		// set MenuBar item with setMenuBarItem function
		setMenuBarItem(Food, "food");
		setMenuBarItem(Dessert, "dessert");
		setMenuBarItem(Drink, "Drink");
		// a JMenuItem for other JMenu
		delivery = new JMenuItem("Delivery and Promo");
		delivery.setFont(font);
		delivery.addActionListener(this);
		other.add(delivery);
		// a JMenuBar to add JMenu
		JMenuBar bar = new JMenuBar();
		bar.setBackground(Color.white);
		// add Menu to MenuBar
		bar.add(Food);
		bar.add(Dessert);
		bar.add(Drink);
		bar.add(other);
		bar.setBorder(new LineBorder(Color.CYAN));// set border to bar
		up.add(bar);// add bar to up Panel
		// a panel for down side of managePane
		JPanel down = new JPanel();
		down.setBackground(Color.white);
		// setting managePane
		managePane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		managePane.setTopComponent(up);
		managePane.setBottomComponent(down);
		managePane.setDividerLocation(60);
		managePane.setDividerSize(-1);
		return managePane;
	}// end of managePane function

	private void setMenuBarItem(JMenu itemType, String itemTypeName) {// a function to set JMenuItem to JMenu
		// get subItemName value from menu_categories table form database with Get_Item_Sub_Type function of  Menu class in Common Package
		ArrayList<String> getMenu = new Menu().Get_Item_Sub_Type(itemTypeName);

		for (String subitemname : getMenu) {
			JMenuItem menuItem = new JMenuItem(subitemname);// create JMenuItem
			itemType.add(menuItem);// add JMenuItem to JMenu
			menuItem.setBackground(Color.WHITE);
			menuItem.setFont(font);
			menuItem.addActionListener(new ActionListener() {// if JMenuItem is clicked
				@Override
				public void actionPerformed(ActionEvent e) {
					managePane.setBottomComponent(new CreateMenuPanel(subitemname));// set managePane's bottom side with
																					// a panel associated with JMenuItem
				}
			});
		}
	}// end of setMenuBarItem function

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Manage) {// if Manage button is clicked
			Manage.setForeground(Color.pink);// set button color
			mainPane.setRightComponent(managePane());// set right hand side of mainPane with panel return from
														// managePane function

		} else// if manage button is not clicked
			Manage.setForeground(Color.black);// set button color
		if (e.getSource() == delivery)// if delivery JMenuItem is clicked
			managePane.setBottomComponent(new ManageDeliveryPanel());// set down side of managePane with panel from
																		// ManageDeliveryPanel class

		if (e.getSource() == viewUser) {// if veiwUser button is clicked
			viewUser.setForeground(Color.pink);// set button color
			mainPane.setRightComponent(new ViewUser());// set right side of mainPane with panel from ViewUser class

		} else// if viewUser button is not clicked
			viewUser.setForeground(Color.black);// set button color

		if (e.getSource() == viewOrder) {// if veiwOrder button is clicked
			viewOrder.setForeground(Color.pink);// set button color
			mainPane.setRightComponent(new ViewOrder());// set right side of mainPane with panel from ViewOrder class
		} else// if viewOrder button is not clicked
			viewOrder.setForeground(Color.black);// set button color

		if (e.getSource() == updateProfile) {// if updateProfile button is clicked
			updateProfile.setForeground(Color.pink);// set button color
			mainPane.setRightComponent(adminUpdate);// set right side of mainPane with panel from adminUpdate class
			
		} else// if updateProfile button is not clicked
			updateProfile.setForeground(Color.black);// set button color
		if (e.getSource() == adminUpdate.update) {//if update button form adminUpdate class is clicked

			if (adminUpdate.checkPassword())//if checkPassword function from adminUpdate return true
				if (adminUpdate.checkEqualPassword())//if checkEqualPassword function from adminUpdate return true
					if (adminUpdate.update()) {//if update function from adminUpdate return true
						dispose();//disappear the frame 
						new AdminLogin();//call AdminLogin frame
					}
		}

		if (e.getSource() == Logout) {// if Logout button is clicked
			Logout.setForeground(Color.PINK);// set button color

			Object[] options = { "Logout", "Exit" };//option to show in option dialog

			int n = JOptionPane.showOptionDialog(this, "Logout Or Exit??", "Option", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, 
					options, null);
			if (n == 0) {//if Logout is choose
				dispose();//disappear the frame
				new AdminLogin();//call AdminLogin frame
			} else if (n == 1)//if exit is choose
				System.exit(0);//exist from system
		} else// if logout button is not clicked
			Logout.setForeground(Color.BLACK);// set button color
	}// end of actionPerformed

}// end of class
