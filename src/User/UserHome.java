package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Common.Menu;
import Design.ComponentCellEditor;
import Design.ComponentRenderer;
import Design.Imagelabel;
import Design.Label;
import Design.PlaneButton;
import Design.PlaneCheckBox;
import Design.RoundedRectangleButton;
/**
 * @author NaingNaingOo
 *This class,UserHome, to show Home screen for user
 */
public class UserHome extends JFrame implements ActionListener {

	JSplitPane orderandbillpane;
	JPanel menu, side, orderpanel, bill;
	JButton foodButton, dessertButton, drinkButton, UpdateProfileButton, LogoutButton, Cancel, Order;
	JTextField totalCost;
	JTable table;
	DefaultTableModel model;
	ArrayList<JCheckBox[]> allCheckBox = new ArrayList<>();
	ArrayList<JSpinner[]> allSpinner = new ArrayList<>();

	Menu menufromDB = new Menu();
	UserUpdate UserUpdate;
	String font = "Times New Roman";

	int ID = 0;

	public UserHome(int id) {

		Dimension size = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		ID = id;
		this.setTitle("Home Page");
		UserUpdate = new UserUpdate(ID);

		JSplitPane mainpane = new JSplitPane();
		orderandbillpane = new JSplitPane();

		menu = new JPanel();
		menu.setLayout(new GridLayout(5, 1));
		menu.setBackground(Color.white);
		side = new JPanel();
		orderpanel = new JPanel();
		orderpanel.setBackground(Color.white);
		JPanel orderpanel = new JPanel();
		orderpanel.setBackground(Color.white);

		mainpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		mainpane.setLeftComponent(menu);
		mainpane.setRightComponent(side);
		mainpane.setDividerLocation(100);
		mainpane.setDividerSize(0);

		foodButton = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp;Foods &nbsp;</p></html>", 16);
		dessertButton = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp;Desserts&nbsp;</p></html>", 16);
		drinkButton = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp;Drinks&nbsp;</p></html>", 16);
		UpdateProfileButton = new PlaneButton("<html><p align='center' style='border:1px solid black;'>&nbsp;Update Profile&nbsp;</p></html>", 16);
		LogoutButton = new PlaneButton("<html><p style='border:1px solid black;'>&nbsp;Logout&nbsp;</p></html>", 16);

		menu.add(foodButton);
		menu.add(dessertButton);
		menu.add(drinkButton);
		menu.add(UpdateProfileButton);
		menu.add(LogoutButton);

		JLabel text = new Label("Choose and Order what you want", Color.black, 34);
		orderpanel.add(text);
		foodButton.addActionListener(this);
		dessertButton.addActionListener(this);
		drinkButton.addActionListener(this);
		UpdateProfileButton.addActionListener(this);
		LogoutButton.addActionListener(this);

		billPage();

		side.add(orderandbillpane);
		side.setLayout(new GridLayout());
		orderandbillpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		orderandbillpane.setLeftComponent(orderpanel);
		orderandbillpane.setRightComponent(bill);
		orderandbillpane.setDividerLocation(900);
		orderandbillpane.setDividerSize(0);

		getContentPane().add(mainpane);
		setSize(size.width, size.height);
		this.setVisible(true);// set frame visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end of constructor

	private void billPage() {// a function for showing order and calculating bill

		bill = new JPanel();
		bill.setBackground(new Color(255, 229, 204));
		bill.setLayout(null);
		bill.setVisible(false);

		JLabel heading = new Label(UserUpdate.userName + "'s Order", Color.black, 18);
		heading.setBounds(0, 0, 400, 50);

		model = new DefaultTableModel();
		model.addColumn("Item");
		model.addColumn("Qty");
		model.addColumn("Price");

		table = new JTable(model);
		table.setFont(new Font(font, Font.BOLD, 12));
		table.setGridColor(Color.white);
		table.setRowHeight(20);
		table.getTableHeader().setFont(new Font(font,Font.PLAIN,18));
		table.setFont(new Font(font,Font.PLAIN,18));
		TableColumnModel cmodel = table.getColumnModel();
		cmodel.getColumn(0).setPreferredWidth(240);
		cmodel.getColumn(1).setPreferredWidth(15);
		cmodel.getColumn(2).setPreferredWidth(50);

		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		cmodel.getColumn(2).setCellRenderer(right);

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		cmodel.getColumn(1).setCellRenderer(center);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(5, 50, 350, 500);
		pane.getViewport().setBackground(Color.white);

		JLabel total = new Label("Total Cost", Color.black, 18);
		total.setBounds(30, 560, 200, 50);

		totalCost = new JTextField(20);
		totalCost.setHorizontalAlignment(JTextField.RIGHT);
		totalCost.setFont(new Font(font, Font.BOLD, 18));
		totalCost.setEditable(false);
		totalCost.setBackground(Color.white);
		totalCost.setBounds(120, 570, 150, 30);
		
		JLabel lblkyats=new Label("MMK",Color.BLACK,18);
		lblkyats.setBounds(280, 560, 80, 50);
		
		Cancel = new RoundedRectangleButton("Cancel", Color.red);
		Cancel.setBounds(30, 630, 150, 50);

		Order = new RoundedRectangleButton("Order", Color.green);
		Order.setBounds(200, 630, 150, 50);

		Cancel.addActionListener(this);
		Order.addActionListener(this);

		bill.add(heading);
		bill.add(pane);
		bill.add(total);
		bill.add(totalCost);
		bill.add(lblkyats);
		bill.add(Cancel);
		bill.add(Order);

	}//end of billPage function

	private String[][] getSelectedCheckBox() {//get selected menu item form order table
		int count = model.getRowCount();
		String selectedNameandQty[][] = new String[count][2];
		for (int i = 0; i < count; i++) {
			selectedNameandQty[i][0] = (String) table.getModel().getValueAt(i, 0);
			selectedNameandQty[i][1] = "" + table.getModel().getValueAt(i, 1);
		}

		return selectedNameandQty;
	}//end of getSelectedCheckBox function

	private JPanel showMenuPanel(String Item_type) {// a function to show panel for Menu item
		JPanel showMenu = new JPanel();
		showMenu.setLayout(new GridLayout());
		showMenu.setBackground(new Color(255, 229, 204));

		ArrayList<String> getMenu = menufromDB.Get_Item_Sub_Type(Item_type);
		JTabbedPane menuTabbedPane = new JTabbedPane();

		for (String subitemname : getMenu) {
			menuTabbedPane.addTab(subitemname, createMenuPanel(subitemname));
		}
		menuTabbedPane.setBackground(Color.white);
		menuTabbedPane.setForeground(new Color(255, 153, 204));
		menuTabbedPane.setFont(new Font(font, Font.BOLD, 16));
		showMenu.add(menuTabbedPane);

		return showMenu;
	}//end of showMenuPanel function

	private JScrollPane createMenuPanel(String subItemName) {

		ArrayList<String> itemName = menufromDB.Get_Item_Name(subItemName);
		ArrayList<Image> itemImage = menufromDB.Get_Item_Image(subItemName);
		ArrayList<String> itemPrice = menufromDB.Get_Item_Price(subItemName);

		JPanel p = new JPanel();
		p.setBackground(Color.white);

		p.setLayout(new GridLayout());
		JScrollPane pane = new JScrollPane();

		DefaultTableModel[] menuTableModel = new DefaultTableModel[3];
		JTable menuTable[] = new JTable[3];
		for (int i = 0; i < 3; i++) {
			menuTableModel[i] = new DefaultTableModel();
			menuTableModel[i].addColumn("box");
			menuTableModel[i].addColumn("label");
			menuTable[i] = new JTable(menuTableModel[i]);
			menuTable[i].setGridColor(Color.white);
			menuTable[i].setRowHeight(250);
			for (int j = 0; j < 2; j++)
				menuTable[i].getColumnModel().getColumn(j).setCellRenderer(new ComponentRenderer());

			menuTable[i].getColumnModel().getColumn(0).setCellEditor(new ComponentCellEditor(new JCheckBox()));
			pane.add(menuTable[i]);
		}

		JCheckBox[] checkbox = new JCheckBox[itemName.size()];
		JSpinner[] spinner = new JSpinner[itemName.size()];
		JLabel[] lblname = new JLabel[itemName.size()];
		String[][] selectedcheckbox = getSelectedCheckBox();

		for (int i = 0; i < itemName.size(); i++) {

			checkbox[i] = new PlaneCheckBox();
			checkbox[i].setActionCommand(itemName.get(i));

			spinner[i] = new JSpinner();
			((SpinnerNumberModel) spinner[i].getModel()).setMinimum(1);
			spinner[i].setValue(1);
			spinner[i].setMaximumSize(new Dimension(50, 20));
			spinner[i].setVisible(false);

			for (int j = 0; j < selectedcheckbox.length; j++) {
				if (selectedcheckbox[j][0].equals(checkbox[i].getActionCommand())) {
					checkbox[i].setSelected(true);
					spinner[i].setVisible(true);
					spinner[i].setValue(Integer.parseInt(selectedcheckbox[j][1]));
				}
			}

			lblname[i] = new Imagelabel("<html>" + itemName.get(i) + "<br/><font color='#33FF33'>" + itemPrice.get(i)
					+ " MMK</font> </html>", new ImageIcon(itemImage.get(i)));

			checkbox[i].setAlignmentX(RIGHT_ALIGNMENT);
			spinner[i].setAlignmentX(RIGHT_ALIGNMENT);

			if (i % 3 == 0) {
				Box box = Box.createVerticalBox();
				box.add(Box.createRigidArea(new Dimension(20, 100)));
				box.add(checkbox[i]);
				box.add(spinner[i]);
				menuTableModel[0].addRow(new Object[] { box, lblname[i] });

			} else if (i % 3 == 1) {
				Box box = Box.createVerticalBox();
				box.add(Box.createRigidArea(new Dimension(20, 100)));
				box.add(checkbox[i]);
				box.add(spinner[i]);
				menuTableModel[1].addRow(new Object[] { box, lblname[i] });

			} else {
				Box box = Box.createVerticalBox();
				box.add(Box.createRigidArea(new Dimension(20, 100)));
				box.add(checkbox[i]);
				box.add(spinner[i]);
				menuTableModel[2].addRow(new Object[] { box, lblname[i] });
			}

			checkbox[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < itemName.size(); i++) {
						if (checkbox[i].isSelected()) {
							spinner[i].setVisible(true);
							addItem(checkbox[i].getActionCommand(), spinner[i].getValue(), itemPrice.get(i));
							totalCost.setText(totalCost());
						} else {
							spinner[i].setVisible(false);
							spinner[i].setValue(1);
							remove(checkbox[i].getActionCommand());
							totalCost.setText(totalCost());
						}
					}
				}
			});
			spinner[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					for (int i = 0; i < itemName.size(); i++) {
						updateQtyPrice(checkbox[i].getActionCommand(), spinner[i].getValue(), itemPrice.get(i));
						totalCost.setText(totalCost());
					}
				}
			});
		}
		allCheckBox.add(checkbox);
		allSpinner.add(spinner);
		for (int i = 0; i < 3; i++) 
			p.add(menuTable[i]);

		pane.getViewport().add(p);
		return pane;
	}//end of createMenuPanel function

	private void addItem(String item, Object qty, String price) {//adding item to order table
		boolean exist = false;
		String pp = null;
		int q = (int) qty;
		int cost = Integer.parseInt(price) * q;
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			pp = table.getValueAt(i, 0).toString().trim();
			if (item.equals(pp)) {
				exist = true;
				break;
			} else
				exist = false;
		}
		if (exist == false)
			model.addRow(new Object[] { item, qty, cost });
	}//end of addItem function

	private void remove(String item) {//a function to remove item form order table
		String q;
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			q = (String) table.getModel().getValueAt(i, 0);
			if (q.equals(item)) {
				model.removeRow(i);
				break;
			}
		}
	}//end of remove

	private String totalCost() {//a function to add all order cost
		int total = 0;
		int getprice = 0;
		String totalcost = null;

		for (int i = 0; i < model.getRowCount(); i++) {
			getprice = (int) table.getModel().getValueAt(i, 2);
			total += getprice;
		}
		totalcost = "" + total;
		return totalcost;
	}//end of totalCost

	private void updateQtyPrice(String item, Object qty, String price) {// a function to update Qty and price in order table
		String p;
		int q = (int) qty;
		int cost = Integer.parseInt(price) * q;
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			p = (String) table.getModel().getValueAt(i, 0);
			if (p.equals(item)) {
				table.getModel().setValueAt(qty, i, 1);
				table.getModel().setValueAt(cost, i, 2);
				break;
			}
		}
	}//end of updateQtyPrice function

	private void clear() {// a function to clear all of selection
		int count = model.getRowCount();
		if (count > 0)
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
		for (JCheckBox[] checkBox : allCheckBox) {
			for (JCheckBox cb : checkBox) {
				cb.setSelected(false);
			}
		}
		for (JSpinner[] Spinner : allSpinner) {
			for (JSpinner spinner : Spinner) {
				spinner.setVisible(false);
				spinner.setValue(1);
			}
		}

		totalCost.setText("");
	}//end of clear function

	private void order() {// a function to go fillToOrder frame

		DefaultTableModel mmodel = new DefaultTableModel();
		mmodel = model;
		JTable tran = new JTable(mmodel);

		for (int i = 0; i < model.getRowCount(); i++) {
			tran.setValueAt(table.getValueAt(i, 0), i, 0);
			tran.setValueAt(table.getValueAt(i, 1), i, 1);
			tran.setValueAt(table.getValueAt(i, 2), i, 2);
		}
		if (!totalCost.getText().equals("")) {

			Object[] options = { "ME", "GIFT" };
			String Question = "<html><font face='" + font
					+ "' size=5>Is this order for yourself?  OR<br/>Surprise Gift for Someone?</font><font face='Segoe UI Emoji' size=5>👀👀</font></html>";
			int n = JOptionPane.showOptionDialog(this, Question, "Confirm Order", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
					options, null);// the titles of buttons // default button title
			if (n == 0) {
				new FillToOrder(ID, UserUpdate.userName, tran, Integer.parseInt(totalCost.getText()));
			} else if (n == 1)
				new FillToOrder(ID, UserUpdate.userName + "'s", tran, Integer.parseInt(totalCost.getText()));
		}
	}//end of order function

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == foodButton) {
			bill.setVisible(true);
			foodButton.setForeground(Color.green);
			orderandbillpane.setLeftComponent(showMenuPanel("food"));

			orderandbillpane.setDividerLocation(900);
		} else
			foodButton.setForeground(Color.black);

		if (e.getSource() == drinkButton) {
			bill.setVisible(true);
			drinkButton.setForeground(Color.green);
			orderandbillpane.setLeftComponent(showMenuPanel("drink"));

			orderandbillpane.setDividerLocation(900);
		} else
			drinkButton.setForeground(Color.black);

		if (e.getSource() == dessertButton) {
			bill.setVisible(true);
			dessertButton.setForeground(Color.green);
			orderandbillpane.setLeftComponent(showMenuPanel("dessert"));

			orderandbillpane.setDividerLocation(900);
		} else
			dessertButton.setForeground(Color.black);

		if (e.getSource() == UpdateProfileButton) {

			UpdateProfileButton.setForeground(Color.green);
			bill.setVisible(false);
			orderandbillpane.setLeftComponent(UserUpdate);
			orderandbillpane.setDividerLocation(800);

		} else
			UpdateProfileButton.setForeground(Color.black);
		if (e.getSource() == LogoutButton) {
			int exit = JOptionPane.showConfirmDialog(this, "Do you really want to logout?", "Confirm Logout",
					JOptionPane.YES_NO_OPTION);
			if (exit == 0) {
				dispose();
				new UserLogin();
			}
		}
		if (e.getSource() == Cancel)
			clear();
		if (e.getSource() == Order)
			order();
	}//end of actionPerformed
}//end of class