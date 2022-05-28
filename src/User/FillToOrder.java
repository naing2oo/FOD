package User;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import Common.QuarterAndPromo;
import Design.Imagelabel;
import Design.Label;
import Design.RoundedRectangleButton;
import Server.Server;

/**
 * @author NaingNaingOo This class is for order confirm frame and to get
 *         information for delivery
 */
public class FillToOrder extends JFrame implements ActionListener {

	JLabel lblReqName, lblReqPhone, lblReqStreet, lblReqQuarter, message, totalCost, deliCost;
	JTextField receiverName, receiverPhone, street;
	JComboBox quarter;
	JButton Back, Order;
	String Item = "";
	ArrayList<String> quar;
	int ordercost, promo, deli_cost, Totalcost;
	String fontname = "Times New Roman";
	String imgpath = "C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";// set a string with path for adding image
	Server server = new Server();
	String username = "";
	int userId = 0;
	int fontsize = 20;
	Font font = new Font(fontname, Font.PLAIN, fontsize);

	public FillToOrder(int ID, String name, JTable table, int cost) {

		super("Order Form");
		userId = ID;
		username = name;
		ordercost = cost;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);

		JLabel lblTitle = new Label("Please Fill in Details", Color.blue, fontsize);
		lblTitle.setBounds(179, 23, 179, 50);
		panel.add(lblTitle);

		receiverName = new JTextField();
		receiverName.setFont(font);
		receiverPhone = new JTextField();
		receiverPhone.setFont(font);
		street = new JTextField();
		street.setFont(font);
		quarter = new JComboBox(new QuarterAndPromo().GetQuarterWithHeading());
		quarter.setFont(font);
		quarter.setBackground(Color.white);
		quarter.setFocusable(false);
		quarter.setMaximumRowCount(5);
		String isReceiver = name.contains("'s") ? "Receiver's " : "Your ";
		JLabel lblName = new Label(isReceiver + "Name", Color.BLACK, fontsize);
		JLabel lblPH = new Label(isReceiver + "Phone", Color.BLACK, fontsize);
		JLabel lblAddress = new Label(isReceiver + "Address", Color.BLACK, fontsize);
		JLabel lb1 = new Label(">>   Street", Color.BLACK, fontsize);
		JLabel lb2 = new Label(">>  Quarter", Color.BLACK, fontsize);
		JLabel lb3 = new Label(">>   City", Color.BLACK, fontsize);
		deliCost = new Label("", Color.BLACK, fontsize);
		message = new Label("", new Color(102, 0, 102), fontsize);
		totalCost = new Label("" + cost + " MMK", Color.BLACK, fontsize);
		JTextField city = new JTextField("Monywa");
		city.setEditable(false);
		city.setFont(font);
		city.setForeground(Color.BLACK);
		city.setBackground(Color.white);
		String include = username.contains("'s") ? "" : "'s";
		JLabel lb4 = new Label(username + include + " Order", new Color(255, 128, 0), fontsize);
		JTable showOrder = table;
		showOrder.setFont(font);
		showOrder.setGridColor(Color.white);
		showOrder.getTableHeader().setFont(font);
		showOrder.setRowHeight(20);
		TableColumnModel cmodel = showOrder.getColumnModel();
		cmodel.getColumn(0).setPreferredWidth(230);
		cmodel.getColumn(1).setPreferredWidth(15);
		cmodel.getColumn(2).setPreferredWidth(50);
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(SwingConstants.RIGHT);
		cmodel.getColumn(1).setCellRenderer(right);
		cmodel.getColumn(2).setCellRenderer(right);
		JScrollPane showOrderPane = new JScrollPane(showOrder);
		showOrderPane.getViewport().setBackground(Color.white);
		int loop = showOrder.getRowCount();
		for (int i = 0; i < loop; i++) {
			Item += showOrder.getValueAt(i, 0) + "(" + showOrder.getValueAt(i, 1) + ") ";
		}

		JLabel order = new Label("Order Cost", Color.black, fontsize);
		JLabel orderCost = new Label("" + cost + " MMK", Color.green, fontsize);
		orderCost.setForeground(Color.BLACK);
		JLabel deli = new Label("Delivery Cost", Color.black, fontsize);
		JLabel total = new Label("Total cost", Color.black, fontsize);
		Back = new RoundedRectangleButton("Back", Color.red);
		Order = new RoundedRectangleButton("Order", Color.green);
		orderCost.setAlignmentX(LEFT_ALIGNMENT);
		deliCost.setAlignmentX(LEFT_ALIGNMENT);
		totalCost.setAlignmentX(LEFT_ALIGNMENT);
		JLabel piclabel = new Imagelabel("", new ImageIcon(imgpath + "orderformpic.jpg"));

		lblReqName = new Label("Please Enter Name", Color.RED, 16);
		lblReqPhone = new Label("Please Enter Valid Number", Color.RED, 16);
		lblReqStreet = new Label("Please Enter Street", Color.RED, 16);
		lblReqQuarter = new Label("Please select Quarter", Color.RED, 16);

		receiverName.setBounds(303, 100, 168, 30);
		receiverPhone.setBounds(303, 165, 168, 30);
		street.setBounds(303, 228, 168, 30);
		quarter.setBounds(303, 299, 168, 30);
		lblName.setBounds(21, 100, 179, 30);
		lblPH.setBounds(21, 165, 162, 30);
		lblAddress.setBounds(21, 228, 162, 30);
		lb1.setBounds(193, 228, 100, 30);
		lb2.setBounds(193, 299, 100, 30);
		lb3.setBounds(207, 370, 86, 30);
		city.setBounds(303, 370, 168, 30);
		lb4.setBounds(605, 20, 246, 30);
		showOrderPane.setBounds(514, 65, 350, 350);
		order.setBounds(524, 430, 150, 30);
		orderCost.setBounds(772, 430, 150, 30);
		deli.setBounds(524, 480, 150, 30);
		deliCost.setBounds(772, 480, 150, 30);
		message.setBounds(36, 430, 435, 220);
		total.setBounds(524, 530, 150, 30);
		totalCost.setBounds(772, 530, 150, 30);
		Back.setBounds(632, 600, 100, 50);
		Order.setBounds(756, 600, 108, 50);
		piclabel.setBounds(874, 20, 100, 136);
		lblReqName.setBounds(303, 68, 168, 30);
		lblReqPhone.setBounds(303, 138, 179, 30);
		lblReqStreet.setBounds(303, 198, 133, 30);
		lblReqQuarter.setBounds(303, 269, 168, 30);

		lblReqName.setVisible(false);
		lblReqPhone.setVisible(false);
		lblReqStreet.setVisible(false);
		lblReqQuarter.setVisible(false);

		panel.add(receiverName);
		panel.add(receiverPhone);
		panel.add(street);
		panel.add(quarter);
		panel.add(lblName);
		panel.add(lblPH);
		panel.add(lblAddress);
		panel.add(lb1);
		panel.add(lb2);
		panel.add(lb3);
		panel.add(city);
		panel.add(lb4);
		panel.add(showOrderPane);
		panel.add(order);
		panel.add(orderCost);
		panel.add(deli);
		panel.add(deliCost);
		panel.add(message);
		panel.add(total);
		panel.add(totalCost);
		panel.add(piclabel);
		panel.add(Back);
		panel.add(Order);
		panel.add(lblReqName);
		panel.add(lblReqPhone);
		panel.add(lblReqStreet);
		panel.add(lblReqQuarter);

		promo = new QuarterAndPromo().GetPromo();// get promotion price form db
		// set user information if order is for user their self
		if (!name.contains("'s")) {

			GetAndSetUserInfo();
			GetDeliCost();
			// set message and deliCost if order cost is over promotion cost or promotion
			// equals to 0
			if (promo == 0 || ordercost < promo) {

				deliCost.setText("" + deli_cost);
				message.setText("");
				Totalcost = ordercost + deli_cost;
				totalCost.setText("" + Totalcost);

			} else if (ordercost >= promo) {

				deliCost.setText("free");
				message.setText("<html>Your order cost is more than or equal " + promo
						+ " MMK,<br/> so delivery cost is free.<br/> Thank You</html>");

				totalCost.setText("" + ordercost);

			}

		} else {// set message and deliCost for gift
			if (promo == 0) {

			} else if (ordercost >= promo) {

				deliCost.setText("free");
				message.setText("<html>Your order cost is more than or equal " + promo
						+ " MMK,<br/> so delivery cost is free.<br/> Thank You</html>");

				totalCost.setText("" + ordercost);

			}
			receiverPhone.setText("09");
		}

		Back.addActionListener(this);
		Order.addActionListener(this);
		quarter.addActionListener(this);
		// adding keyListener for receiverPhone text field
		receiverPhone.addKeyListener(new KeyListener() {

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

		});
		getContentPane().add(panel);

		setSize(1000, 700);
		setVisible(true);
		setLocationRelativeTo(null);
	}// end of constructor

	private void GetDeliCost() {// a function to get delivery cost for associated quarter
		String sql = "select deli_cost from delivery WHERE Quarter='" + (String) quarter.getSelectedItem() + "'";
		try {
			server.connectionCreate();
			ResultSet Deli_Cost = server.select(sql);
			while (Deli_Cost.next()) {
				deli_cost = Deli_Cost.getInt("deli_cost");
			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of GetDeliCost function

	private Boolean checkRequiredField() {// a function to check required field and return true if all are filled else
											// return false

		Boolean isFilled = false;// create a boolean variable to return and set false
		String phno = receiverPhone.getText();// set phno string with text in phone text filed
	
		String foramt="\\d{11}|\\d{9}";
		boolean truephoneformat = Pattern.matches(foramt, phno);
		if (receiverName.getText().equals("") || truephoneformat == false || street.getText().equals("")
				|| quarter.getSelectedIndex() == 0)
			isFilled = false;
		else
			isFilled = true;
		if (receiverName.getText().equals("")) {// if name text field is blank
			lblReqName.setVisible(true);

		} else
			lblReqName.setVisible(false);

		if (truephoneformat==false) {
			lblReqPhone.setVisible(true);

		} else
			lblReqPhone.setVisible(false);

		if (street.getText().equals("")) {
			lblReqStreet.setVisible(true);

		} else
			lblReqStreet.setVisible(false);

		if (quarter.getSelectedIndex() == 0) {
			lblReqQuarter.setVisible(true);

		} else
			lblReqQuarter.setVisible(false);
		return isFilled;
	}// end of checkRequiredField function

	private void GetAndSetUserInfo() {// a function to get and set user information

		try {
			String sql = "select name,phno,street,Quarter from customer WHERE id=" + userId;
			server.connectionCreate();
			ResultSet data = server.select(sql);
			while (data.next()) {
				receiverName.setText(data.getString("name"));
				receiverPhone.setText(data.getString("phno"));
				street.setText(data.getString("street"));
				quarter.setSelectedItem(data.getString("Quarter"));
			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of GetAndSetUserInfo function

	private void order() {// a function to add new row into customer_order table

		String address = street.getText() + ", " + (String) quarter.getSelectedItem();
		String receivername = username.contains("'s") ? "(" + username + " order for) " + receiverName.getText()
				: receiverName.getText();
		int deli = deliCost.getText().equals("free") ? 0 : (Integer.parseInt(deliCost.getText()));
		try {
			server.connectionCreate();
			PreparedStatement addorder = (PreparedStatement) server.con.prepareStatement(
					"INSERT INTO customer_order ( customer_id, customer_name, phno, deli_address, order_item, order_cost, deli_cost, total_cost) VALUES (?,?,?,?,?,?,?,?)");
			addorder.setInt(1, userId);
			addorder.setString(2, receivername);
			addorder.setString(3, receiverPhone.getText());
			addorder.setString(4, address);
			addorder.setString(5, Item);
			addorder.setInt(6, ordercost);
			addorder.setInt(7, deli);
			addorder.setInt(8, Integer.parseInt(totalCost.getText()));
			if (server.InsertIntoOrder(addorder)) {

				UIManager.put("OptionPane.messageFont", new FontUIResource(font));
				JOptionPane.showMessageDialog(null,
						"<html>We receive your order.<br/>We will deliver soon.<br/>Thank you for your order</html>",
						"Thank You", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
			server.connectionClose();
		}

		catch (Exception e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
	}// end of order function

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Back) {
			dispose();
		} else if (e.getSource() == Order) {
			if (checkRequiredField())
				order();
		} else if (e.getSource() == quarter) {
			if (ordercost >= promo && promo != 0) {
			} else {
				GetDeliCost();
				if (promo == 0 || deli_cost > 0) {

					deliCost.setText("" + deli_cost);
					message.setText("");
					Totalcost = ordercost + deli_cost;
					totalCost.setText("" + Totalcost);
				} else if (!(ordercost >= promo)) {

					if (deli_cost == 0) {
						deliCost.setText("free");
						message.setText("<html>You are in the same quarter with our restaurant<br/>"
								+ "Therefore delivery cost is free!</html>");
						totalCost.setText("" + ordercost);
					}
				}
			}
		}
	}// end of action performed
}// end of class