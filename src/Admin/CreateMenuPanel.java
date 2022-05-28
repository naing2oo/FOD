package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Common.Menu;
import Design.ComponentCellEditor;
import Design.ComponentRenderer;
import Design.Imagelabel;
import Design.Label;
import Design.PlaneButton;
import Server.Server;

/**
 * @author NaingNaingOo 
 * This class is for showing menu item in Admin side to be managed by admin.
 *	So this class includes addItem button to add new item to database, edit and delete for existing menu
 */
public class CreateMenuPanel extends JPanel implements ActionListener {

	JButton addItem,buttonEdit[],buttonDelete[];//button to add new menu ,edit and delete existing menu

	
	ArrayList<Integer> itemID;// to get menu id 
	
	String subItemName="";//set empty string from subItemName

	public CreateMenuPanel(String subitemname) {
		
		subItemName=subitemname; 
		itemID = new Menu().Get_Item_ID(subItemName);//get menu id from db with Get_Item_ID function of Menu class

		ArrayList<String> itemName = new Menu().Get_Item_Name(subItemName);//get menu name from db with Get_Item_Name function of Menu class
		ArrayList<Image> itemImage = new Menu().Get_Item_Image(subItemName);//get menu image from db with Get_Item_Image function of Menu class
		ArrayList<String> itemPrice = new Menu().Get_Item_Price(subItemName);//get menu price from db with Get_Item_Price function of Menu class

		int count = itemName.size();//get how many menu item exist
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		JPanel upPanel=new JPanel(new BorderLayout());
		upPanel.setBackground(Color.WHITE);
		//addItem button
		addItem = new PlaneButton("<html><p style='border:1px solid black;'><i>&nbsp;Add " + subitemname + "&nbsp;</i></p></html>", 24);
		addItem.setHorizontalAlignment(SwingConstants.RIGHT);
		addItem.setForeground(Color.BLACK);
		addItem.addActionListener(this);
		//set button in upPanel 
		upPanel.add(addItem,BorderLayout.EAST);
		//table to show menu to manage
		DefaultTableModel itemModel = new DefaultTableModel();
		itemModel.addColumn("No");
		itemModel.addColumn("Name");
		itemModel.addColumn("Image");
		itemModel.addColumn("Price");
		itemModel.addColumn("Edit Option");
		itemModel.addColumn("Delete Option");

		JTable itemTable = new JTable(itemModel);
		itemTable.setRowHeight(150);

		for (int i = 0; i < 6; i++)
			itemTable.getColumnModel().getColumn(i).setCellRenderer(new ComponentRenderer());//set table cell renderer with ComponentRenderer, design package
		//set cell editor with ComponentCellEditor, design package
		itemTable.getColumnModel().getColumn(4).setCellEditor(new ComponentCellEditor(new JCheckBox()));
		itemTable.getColumnModel().getColumn(5).setCellEditor(new ComponentCellEditor(new JCheckBox()));

		itemTable.setBackground(Color.WHITE);
		itemTable.setGridColor(Color.WHITE);

//		itemTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		itemTable.getColumnModel().getColumn(1).setPreferredWidth(300);

		JLabel lbname[] = new JLabel[count];
		JLabel lbimage[] = new JLabel[count];
		JLabel lbprice[] = new JLabel[count];
		buttonEdit = new JButton[count];
		buttonDelete = new JButton[count];
		int y = 0;
		//adding menu and associated edit and delete button to table 
		for (int i = 0; i < count; i++) {
			y = i + 1;
			JLabel no = new Label("" + y, Color.black, 20);
			lbname[i] = new Label(itemName.get(i), Color.black, 20);
			lbimage[i] = new Imagelabel("", new ImageIcon(itemImage.get(i)));
			lbprice[i] = new Label("" + itemPrice.get(i) + " MMK", Color.green, 20);

			buttonEdit[i] = new PlaneButton("<html><u>Edit</u></html>", 20);
			buttonDelete[i] = new PlaneButton("<html><u>Delete</u></html>", 20);

			no.setHorizontalAlignment(SwingConstants.CENTER);
			lbname[i].setHorizontalAlignment(SwingConstants.LEFT);
			lbimage[i].setHorizontalAlignment(SwingConstants.LEFT);
			lbprice[i].setHorizontalAlignment(SwingConstants.LEFT);
			buttonEdit[i].setForeground(Color.BLACK);
			buttonDelete[i].setForeground(Color.BLACK);

			itemModel.addRow(new Object[] { no, lbname[i], lbimage[i], lbprice[i], buttonEdit[i], buttonDelete[i] });
			buttonEdit[i].addActionListener(this);
			buttonDelete[i].addActionListener(this);

		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(itemTable, BorderLayout.CENTER);
		JScrollPane pane = new JScrollPane();
		pane.setBorder(new LineBorder(Color.WHITE));
		pane.setViewportView(panel);
		this.add(pane, BorderLayout.CENTER);
		this.add(upPanel, BorderLayout.NORTH);
	}//end of constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addItem) {//if addItem button is clicked
			new AddItemFrame(subItemName);//call AddItemFrame to add new menu
		}
		for (int i = 0; i < itemID.size(); i++) {
			if (e.getSource() == buttonEdit[i]) {//if edit button of one's menu is clicked
				new EditItemFrame(itemID.get(i));
			} else if (e.getSource() == buttonDelete[i]) {
//				System.out.println(itemID.get(i));
				deleteItem(itemID.get(i));
			}
		}
	}//end of action performed 

	private void deleteItem(int itemid) {// a function to delete menu
		Server server = new Server();
		server.connectionCreate();
		String sql = "Delete From menu where id=" + itemid;//sql statement to delete menu

		Object[] options = { "Yes!", "No!!!" };
		int n = JOptionPane.showOptionDialog(this, "Are you sure to delete?", "Confirm Deletion",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				options, null);
		if (n == 0) {
			if (server.Delete(sql)) {
				JOptionPane.showMessageDialog(null, "Successfully Deleted!", "Delete Complete",
						JOptionPane.PLAIN_MESSAGE);
			}
			server.connectionClose();
		}
	}//end of deleteItem function
}//end of class