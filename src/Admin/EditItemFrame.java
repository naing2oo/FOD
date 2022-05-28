package Admin;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import Design.Button;
import Design.Imagelabel;
import Design.Label;
import Server.Server;

import java.awt.Color;
/**
 * @author NaingNaingOo This class is for showing Frame to edit existing menu from
 *         database. Two text filed is added to enter name and price of menu
 *         browse button is to select picture from local pc, ok button is to update
 *         menu to database and cancel is to disappear frame.
 * 
 */
public class EditItemFrame extends JFrame implements ActionListener {

	JTextField Name, Price;
	Box showimage;
	JButton browse, ok, cancel;
	String path, mpath = "", subItemName;
	JFileChooser choose;
	byte[] image;
	JLabel addimage;
	Server server = new Server();
	int itemID = 0;

	public EditItemFrame(int ID) {

		itemID = ID;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		//creating components 
		JLabel Heading = new Label("You can edit all.", Color.BLACK, 24);
		JLabel lblName = new Label("Name", Color.BLACK, 20);
		JLabel lblImage = new Label("Image", Color.BLACK, 20);
		JLabel lblPrice = new Label("Price", Color.BLACK, 20);
		Name = new JTextField();
		Name.setBorder(new LineBorder(new Color(0, 255, 255), 2));
		showimage = Box.createVerticalBox();
		addimage = new Imagelabel("", null);
		showimage.add(addimage);
		browse = new Button("Browse");
		Price = new JTextField();
		Price.setBorder(new LineBorder(Color.CYAN, 2));
		cancel = new Button("Cancel");
		ok = new Button("OK");
		fillItemDetail();//call function to fill item detail
		//set size and location of components
		Heading.setBounds(20, 5, 158, 29);
		lblName.setBounds(28, 77, 58, 30);
		lblImage.setBounds(28, 168, 58, 30);
		lblPrice.setBounds(28, 291, 58, 30);
		Name.setBounds(100, 76, 200, 40);
		showimage.setBounds(100, 130, 161, 146);
		browse.setBounds(271, 168, 69, 31);
		Price.setBounds(100, 287, 200, 40);
		cancel.setBounds(50, 380, 100, 30);
		ok.setBounds(200, 380, 100, 30);
		//add button action listener
		browse.addActionListener(this);
		cancel.addActionListener(this);
		ok.addActionListener(this);
		//add components to panel
		panel.add(Heading);
		panel.add(lblName);
		panel.add(lblImage);
		panel.add(lblPrice);
		panel.add(Name);
		panel.add(showimage);
		panel.add(browse);
		panel.add(Price);
		panel.add(cancel);
		panel.add(ok);
		//add panel to frame
		getContentPane().add(panel);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(365, 465);
		setLocationRelativeTo(null);
	}//end of constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == browse) {

			choose = new JFileChooser(new File("C:\\Users\\user\\Desktop"));
			int s = choose.showSaveDialog(null);
			if (s == JFileChooser.APPROVE_OPTION) {
				path = "" + choose.getSelectedFile().getAbsolutePath();
				mpath = path.replace("\\", "\\\\");
				addimage.setIcon(new ImageIcon(mpath));
			}
		} else if (e.getSource() == cancel) {
			dispose();
		}
		else {
			if (checkTextField()) 
				if(updateItem())
					dispose();
		}
	}//end of action performed
	private void fillItemDetail() {
		server.connectionCreate();
		String sql = "SELECT name,image,price FROM menu WHERE id=" + itemID;//sql statement to get detail of menu
		byte images[];

		try {
			ResultSet data = server.select(sql);
			while (data.next()) {
				Name.setText(data.getString(1));//set Name with name from db
				images = data.getBytes("Image");//get image bytes and
				Image image = Toolkit.getDefaultToolkit().createImage(images);//convert byte to image
				addimage.setIcon(new ImageIcon(image));//add image to show
				Price.setText(data.getString(3));//set Price with price from db
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		server.connectionClose();
	}//end of fillItemDetail function
	private boolean checkTextField() {// a function to check and give warning message for text field
		if (Name.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Name", "Waring!", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (Price.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Price", "Waring!", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}//end of checkTextField function
	private boolean updateItem() {

		File f = null;
		FileInputStream FS=null;
		byte[]image = null;
		String sql="";
		PreparedStatement pst = null;

		if (mpath.equals("")) {//if image doesn't change
			sql="UPDATE menu SET Name='"+Name.getText()+"',Price='"+Price.getText()+"' WHERE id="+itemID;//update name and price
		}
		else {
			
			try {//change image to byte code
				f = new File(mpath);
				FS = new FileInputStream(f);
				image = new byte[(int) f.length()];
				FS.read(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			server.connectionCreate();
			if(!sql.equals(""))//if image doesn't change
			 pst = (PreparedStatement) server.con.prepareStatement(sql);
			else
			{
				pst = (PreparedStatement) server.con.prepareStatement("UPDATE menu SET Name=?,Image=?,Price=? WHERE id=?");
				pst.setString(1,Name.getText());
				pst.setBytes(2,image);
				pst.setString(3,Price.getText());
				pst.setInt(4, itemID);
			}
			
			String Duplicate=server.UpdatetoUniquevaluetable(pst);
			server.connectionClose();
			if(Duplicate.equals("")) {
				// if Duplicate equals ""(empty string),give a Information Message Dialog

				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
				UIManager.put("Panel.background", new ColorUIResource(Color.white));
				JOptionPane.showMessageDialog(null, "Successuflly Updated", "Menu Updated", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			else {
				// if Duplicate has string value, give warning message
				UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
				JOptionPane.showMessageDialog(null, "This menu name already exits.", "Menu exits",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end of updateItem function
}//end of class