package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Design.Label;
import Server.Server;

public class ViewOrder extends JPanel {
	DefaultTableModel OrderModel[];

	public ViewOrder() {

		setBackground(Color.white);
		setLayout(new BorderLayout());

		JLabel title = new Label("All Orders Detail", Color.black, 26);
		JLabel lblToday=new Label("Today Orders",Color.blue,26);
		JLabel lblOlder = new Label("Older Orders", Color.darkGray, 26);

		JPanel orderPanel []= new JPanel[2];
		OrderModel=new DefaultTableModel[2];
		JTable[] orderTable=new JTable[2];
		JScrollPane orderPane[]=new JScrollPane[2];
		
		for(int i=0;i<OrderModel.length;i++) {
			OrderModel[i]=new DefaultTableModel();
			OrderModel[i].addColumn("NO.");
			OrderModel[i].addColumn("Name");
			OrderModel[i].addColumn("Ph No");
			OrderModel[i].addColumn("Address");
			OrderModel[i].addColumn("Order_Item");
			OrderModel[i].addColumn("Order_Cost");
			OrderModel[i].addColumn("Deli_Cost");
			OrderModel[i].addColumn("Total_Cost");
			OrderModel[i].addColumn("Order_Date & Time");
			orderTable[i]=new JTable(OrderModel[i]);
			orderTable[i].setFont(new Font("Times New Roman", Font.BOLD, 12));
			orderTable[i].setGridColor(new Color(81, 194, 86));
			orderTable[i].setRowHeight(50);
			orderTable[i].getColumnModel().getColumn(0).setPreferredWidth(7);
			orderTable[i].getColumnModel().getColumn(1).setPreferredWidth(150);
			orderTable[i].getColumnModel().getColumn(2).setPreferredWidth(50);
			orderTable[i].getColumnModel().getColumn(3).setPreferredWidth(100);
			orderTable[i].getColumnModel().getColumn(4).setPreferredWidth(500);
			orderTable[i].getColumnModel().getColumn(5).setPreferredWidth(30);
			orderTable[i].getColumnModel().getColumn(6).setPreferredWidth(30);
			orderTable[i].getColumnModel().getColumn(7).setPreferredWidth(30);
			orderTable[i].getColumnModel().getColumn(8).setPreferredWidth(100);
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			for(int j=0;j<9;j++)
				orderTable[i].getColumnModel().getColumn(j).setCellRenderer(center);

			orderPane[i] = new JScrollPane(orderTable[i]);
			orderPane[i].getViewport().setBackground(Color.WHITE);
			orderPanel[i]=new JPanel();
			orderPanel[i].setBackground(Color.white);
			orderPanel[i].setLayout(new BorderLayout());
		}

		addOrder();
		orderPanel[0].add(lblToday,BorderLayout.NORTH);
		orderPanel[0].add(orderPane[0], BorderLayout.CENTER);
		orderPanel[1].add(lblOlder, BorderLayout.NORTH);
		orderPanel[1].add(orderPane[1], BorderLayout.CENTER);
		
		add(title, BorderLayout.NORTH);
		add(orderPanel[0],BorderLayout.CENTER);
		add(orderPanel[1], BorderLayout.SOUTH);

	}

	private void addOrder() {
		Server server = new Server();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd");
		LocalDate Now= LocalDate.now();
		Now.format(dtf);
		server.connectionCreate();
		String sqlforToday = "SELECT customer_name,phno,deli_address,order_item,order_cost,deli_cost,total_cost,date FROM customer_order where date > '"+Now
				+ "' ORDER BY date DESC";
		
		String sqlforOlder = "SELECT customer_name,phno,deli_address,order_item,order_cost,deli_cost,total_cost,date FROM customer_order where date < '"+Now
				+ "' ORDER BY date DESC";
		try {
			ResultSet todaydata = server.select(sqlforToday);

			int i = 1;
			while (todaydata.next()) {
				OrderModel[0].addRow(new Object[] { i++, todaydata.getString("customer_name"), todaydata.getString("phno"),
						todaydata.getString("deli_address"), todaydata.getString("order_item"), todaydata.getString("order_cost"),
						todaydata.getString("deli_cost"), todaydata.getString("total_cost"), todaydata.getString("date") });

			}
			
		} catch (Exception e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}

		try {
			ResultSet olderData = server.select(sqlforOlder);

			int i = 1;
			while (olderData.next()) {
				OrderModel[1].addRow(new Object[] { i++, olderData.getString("customer_name"), olderData.getString("phno"),
						olderData.getString("deli_address"), olderData.getString("order_item"), olderData.getString("order_cost"),
						olderData.getString("deli_cost"), olderData.getString("total_cost"), olderData.getString("date") });

			}
			
		} catch (Exception e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
		
		server.connectionClose();

	}

}
