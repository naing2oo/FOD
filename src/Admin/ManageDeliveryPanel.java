package Admin;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import Common.QuarterAndPromo;
import Design.Button;
import Design.ComponentCellEditor;
import Design.ComponentRenderer;
import Design.Label;
import Design.PlaneButton;
import Server.Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author NaingNaingOo
 * This class is to edit existing quarter or promotion price and to add new quarter
 *
 */
public class ManageDeliveryPanel extends JPanel implements ActionListener {

	JTextField promo, addQuartername, addQuarterDeliCost, quarterName[], deliCost[];
	Font font = new Font("Times New Roman", Font.BOLD, 20);
	JButton promoUpdate, addQuarter, editQuarter[], deleteQuarter[];
	ArrayList<Integer> QuarterID;
	Server server = new Server();

	public ManageDeliveryPanel() {
		
		setBackground(Color.WHITE);
		setLayout(null);
		//creating components
		JLabel Heading = new Label("<html><b>Edit and click ok</b></html>", new Color(147, 112, 219), 28);
		JLabel promoHeading = new Label("Edit Promotion cost", new Color(147, 112, 219), 26);
		JLabel lblPromo = new Label("Promotation", Color.black, 20);
		promo = new JTextField("" + new QuarterAndPromo().GetPromo());
		promo.setFont(font);
		promo.setHorizontalAlignment(JTextField.RIGHT);
		promoUpdate = new Button("OK");
		JLabel addQuarterHeading = new Label("Add Quarter", new Color(147, 112, 219), 26);
		JLabel lbladdQuratername = new Label("Quarter Name: ", Color.BLACK, 20);
		JLabel lbladdDeliCost = new Label("Deli Cost: ", Color.BLACK, 20);
		addQuartername = new JTextField();
		addQuartername.setFont(font);
		addQuarterDeliCost = new JTextField();
		addQuarterDeliCost.setFont(font);
		addQuarter = new Button("Add Quarter");
		//set size and location of components
		Heading.setBounds(1, 1, 237, 51);
		promoHeading.setBounds(882, 11, 237, 51);
		lblPromo.setBounds(918, 65, 111, 44);
		promo.setBounds(1031, 70, 105, 35);
		promoUpdate.setBounds(1156, 73, 50, 30);
		addQuarterHeading.setBounds(882, 120, 237, 51);
		lbladdQuratername.setBounds(918, 188, 122, 44);
		lbladdDeliCost.setBounds(918, 264, 122, 44);
		addQuartername.setBounds(1050, 193, 150, 35);
		addQuarterDeliCost.setBounds(1050, 269, 105, 35);
		addQuarter.setBounds(1116, 333, 90, 30);
		//create table for quarter manage
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("No");
		model.addColumn("Name");
		model.addColumn("DeliCost");
		model.addColumn("EditOption");
		model.addColumn("DeleteOption");

		JTable deliTable = new JTable(model);
		deliTable.setRowHeight(60);
		deliTable.setBackground(Color.WHITE);
		deliTable.setGridColor(Color.WHITE);

		for (int i = 0; i < 5; i++) {
			deliTable.getColumnModel().getColumn(i).setCellRenderer(new ComponentRenderer());//set table cell renderer with ComponentRenderer, design package
			deliTable.getColumnModel().getColumn(i).setCellEditor(new ComponentCellEditor(new JCheckBox()));//set cell editor with ComponentCellEditor, design package
		}

		deliTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		deliTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		deliTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		deliTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		deliTable.getColumnModel().getColumn(4).setPreferredWidth(100);

		model.addRow(new Object[] { new Label("No", Color.BLUE, 20), new Label("Quarter Name", Color.BLUE, 20),
				new Label("Deli Cost", Color.BLUE, 20) });//add heading row
		QuarterID = new QuarterAndPromo().GetQuarterID();
		String[] quartername = new QuarterAndPromo().GetQuarter();
		String[] delicost = new QuarterAndPromo().GetDeliCost();

		quarterName = new JTextField[quartername.length];
		deliCost = new JTextField[delicost.length];
		editQuarter = new PlaneButton[quartername.length];
		deleteQuarter = new PlaneButton[quartername.length];

		for (int i = 0; i < quartername.length; i++) {
			int y = i + 1;
			quarterName[i] = new JTextField(quartername[i]);
			quarterName[i].setFont(font);
			quarterName[i].setSize(80,30);
//			quarterName[i].setBackground(new Color(224, 255, 255));
//			quarterName[i].setBorder(null);

			deliCost[i] = new JTextField(delicost[i]);
			deliCost[i].setFont(font);
//			deliCost[i].setBackground(new Color(224, 255, 255));
//			deliCost[i].setBorder(null);

			editQuarter[i] = new PlaneButton("<html><u>OK</u><html>", 20);
			deleteQuarter[i] = new PlaneButton("<html><u>Delete</u></html>", 20);

			editQuarter[i].addActionListener(this);
			deleteQuarter[i].addActionListener(this);

			model.addRow(new Object[] { new Label("" + y, Color.BLUE, 20), quarterName[i], deliCost[i], editQuarter[i],
					deleteQuarter[i] });//add quarter to table
		}

		JPanel deliPanel = new JPanel();
		deliPanel.setBackground(Color.WHITE);
		deliPanel.add(deliTable);
		JScrollPane deliPane = new JScrollPane();
		deliPane.setViewportView(deliPanel);
		deliPane.setBorder(new LineBorder(Color.WHITE));
		deliPane.setBounds(215, 36, 630, 580);
		//add button action 
		promoUpdate.addActionListener(this);
		addQuarter.addActionListener(this);
		//add component to panel
		add(Heading);
		add(promoHeading);
		add(lblPromo);
		add(promo);
		add(promoUpdate);
		add(addQuarterHeading);
		add(lbladdQuratername);
		add(lbladdDeliCost);
		add(addQuartername);
		add(addQuarterDeliCost);
		add(addQuarter);
		add(deliPane);
	}//end of constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == promoUpdate) {
			PromoUpdate();
		} else if (e.getSource() == addQuarter) {
			AddQuarter();
		}
		for (int i = 0; i < quarterName.length; i++) {
			if (e.getSource() == editQuarter[i]) {
				UpdateDeli(quarterName[i].getText(), deliCost[i].getText(), QuarterID.get(i));
			} else if (e.getSource() == deleteQuarter[i]) {
				DeleteQuarter(QuarterID.get(i));
			}
		}
	}//end of action performed

	private void PromoUpdate() {
		int promocost = (promo.getText().equals("")) ? 0 : Integer.parseInt(promo.getText());

		String sql = "Update admin SET promo=" + promocost;// sql statement to update promo price
		server.connectionCreate();
		if (server.Update(sql))

			JOptionPane.showMessageDialog(null, "Successfully Update Promo!!\n ", "Update Complete",
					JOptionPane.PLAIN_MESSAGE);
		server.connectionClose();

	}// end of PromoUpdate function
	private void AddQuarter() {
		try {
			server.connectionCreate();
			PreparedStatement sql = (PreparedStatement) server.con
					.prepareStatement("INSERT INTO delivery (Quarter,deli_cost) values(?,?)");//sql statement to add new quarter

			sql.setString(1, addQuartername.getText());
			sql.setInt(2, Integer.parseInt(addQuarterDeliCost.getText()));
			String Duplicate = server.UpdatetoUniquevaluetable(sql);
			server.connectionClose();
			if (Duplicate.equals("")) {// if Duplicate equals ""(empty string)

				// give a Information Message Dialog

				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
				UIManager.put("Panel.background", new ColorUIResource(Color.white));
				JOptionPane.showMessageDialog(null, "Successuflly Added", "Quarter Added",
						JOptionPane.INFORMATION_MESSAGE);

			} else {// if Duplicate has string value
				// give warning message
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				JOptionPane.showMessageDialog(null, "This Quarter name already exits.", "Quarter exits",
						JOptionPane.WARNING_MESSAGE);

			}

		} catch (SQLException e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
	}// end of AddQuarter function
	private void UpdateDeli(String quName, String quCost, int id) {

		try {
			server.connectionCreate();
			PreparedStatement pst = (PreparedStatement) server.con
					.prepareStatement("Update delivery set Quarter=?,deli_cost=? where id=?");//update sql statement
			pst.setString(1, quName);
			pst.setInt(2, Integer.parseInt(quCost));
			pst.setInt(3, id);
			String Duplicate = server.UpdatetoUniquevaluetable(pst);
			if (Duplicate.equals("")) {

				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
				UIManager.put("Panel.background", new ColorUIResource(Color.white));
				JOptionPane.showMessageDialog(null, "Successfully Updated", "Delivery Option Updated",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				// if Duplicate has string value
				// give warning message
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				JOptionPane.showMessageDialog(null, "This Quarter name already exits.", "Quarter exits",
						JOptionPane.WARNING_MESSAGE);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end of UpdateDeli function
	private void DeleteQuarter(int id) {
		server.connectionCreate();
		String sql = "Delete From delivery where id=" + id;//sql statement to delete quarter from db

		Object[] options = { "Yes!", "No!!!" };
		int n = JOptionPane.showOptionDialog(this, "Are you sure to delete?", "Confirm Deletion",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
				options, null);
		if (n == 0) {
			if (server.Delete(sql)) {
				JOptionPane.showMessageDialog(null, "Successfully Deleted!", "Delete Complete",
						JOptionPane.PLAIN_MESSAGE);
			}
			server.connectionClose();
		}
	}//end of DeleteQuarter function
}//end of class
