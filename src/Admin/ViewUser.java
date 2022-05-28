package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Design.Label;
import Server.Server;

/**
 * @author NaingNaingOo
 * This class is for a panel to show customers' detail information without password
 * no action include in this panel, just to show
 */
public class ViewUser extends JPanel {
	DefaultTableModel userModel;

	public ViewUser() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JLabel title = new Label("All Customers Are Here!", Color.BLACK, 26);

		//create a table to show customer info
		userModel = new DefaultTableModel();
		userModel.addColumn("NO.");
		userModel.addColumn("Name");
		userModel.addColumn("Ph No");
		userModel.addColumn("Gender");
		userModel.addColumn("Street");
		userModel.addColumn("Quarter");
		userModel.addColumn("Joined Date");

		JTable usertable = new JTable(userModel);
		usertable.setFont(new Font("Times New Roman", Font.BOLD, 12));
		usertable.setGridColor(new Color(255, 204, 229));
		usertable.setRowHeight(50);

		TableColumnModel cmodel = usertable.getColumnModel();
		cmodel.getColumn(0).setPreferredWidth(20);
		cmodel.getColumn(1).setPreferredWidth(100);
		cmodel.getColumn(2).setPreferredWidth(100);
		cmodel.getColumn(3).setPreferredWidth(30);
		cmodel.getColumn(4).setPreferredWidth(150);
		cmodel.getColumn(5).setPreferredWidth(150);
		cmodel.getColumn(6).setPreferredWidth(150);

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < 7; i++) {
			cmodel.getColumn(i).setCellRenderer(center);
		}

		addUser();//call function to add customer 

		JScrollPane pane = new JScrollPane(usertable);
		
		pane.getViewport().setBackground(Color.WHITE);
		add(title,BorderLayout.NORTH);
		add(pane,BorderLayout.CENTER);
	}//end of constructor

	private void addUser() {// a function to add customer info to table
		
		try {
			Server server=new Server();
			server.connectionCreate();
			String sql="select Name,Phno,Gender,Street,Quarter,Date from customer ORDER BY Date ASC";// sql statement to select customer detail
		
			ResultSet data=server.select(sql);
		
			int i=1;
			while(data.next())
				userModel.addRow(new Object[]{i++,data.getString("Name"),data.getString("Phno")
						,data.getString("Gender"),data.getString("Street"),data.getString("Quarter"),data.getString("Date")});//add data to table
				
			server.connectionClose();	
		} catch (Exception e) {
			System.out.print("Do not connect to DB-Error:"+e);
		}
	
	}//end of addUser function
}//end of class
