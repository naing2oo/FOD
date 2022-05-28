package Admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
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

import Design.Button;
import Design.Imagelabel;
import Design.Label;
import Server.Server;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

/**
 * @author NaingNaingOo This class is for showing Frame to add new menu to
 *         database. Two text filed is added to enter name and price of menu
 *         browse button is to select picture from local pc, ok button is to add
 *         menu to database and cancel is to disappear frame.
 * 
 */
public class AddItemFrame extends JFrame implements ActionListener {

	JTextField name, price;
	JButton browse, ok, cancel;
	String imgPath = "", subItemName;
	JLabel addimage, warningLblName, warningLblImage, warningLblPrice;

	public AddItemFrame(String subItemName) {// constructor with one argument

		this.subItemName = subItemName;// set name with argument name

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// show frame as system frame
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JPanel contentPane = new JPanel();// panel to add component
		contentPane.setBackground(Color.white);// panel background color white
		contentPane.setLayout(null);// panel's layout null

		// creating label to show which is for what
		JLabel Heading = new Label("Add one more " + subItemName, Color.BLACK, 24);
		JLabel lblName = new Label("Name", Color.BLACK, 20);
		JLabel lblImage = new Label("Image", Color.BLACK, 20);
		JLabel lblPrice = new Label("Price", Color.BLACK, 20);
		warningLblName = new Label("Please enter name", Color.RED, 18);
		warningLblImage = new Label("Please enter image", Color.RED, 18);
		warningLblPrice = new Label("Please enter price", Color.RED, 18);
		// name text field creation and adding line border
		name = new JTextField();
		name.setBorder(new LineBorder(new Color(0, 255, 255), 2));
		// create a vertical Box to show image and image label for image
		Box showimage = Box.createVerticalBox();
		addimage = new Imagelabel("", null);
		showimage.add(addimage);
		// create browse button for choosing picture form local pc
		browse = new Button("Browse");
		// price text field creation and adding line border
		price = new JTextField();
		price.setBorder(new LineBorder(Color.CYAN, 2));
		// cancel and ok button
		cancel = new Button("Cancel");
		ok = new Button("OK");
		// placing all components
		Heading.setBounds(10, 11, 330, 30);
		lblName.setBounds(28, 83, 58, 30);
		lblImage.setBounds(28, 180, 58, 30);
		lblPrice.setBounds(28, 340, 58, 30);
		warningLblName.setBounds(102, 52, 137, 25);
		name.setBounds(100, 80, 200, 40);
		warningLblImage.setBounds(100, 125, 135, 25);
		showimage.setBounds(100, 150, 125, 146);
		browse.setBounds(271, 168, 69, 31);
		warningLblPrice.setBounds(100, 310, 135, 25);
		price.setBounds(100, 340, 200, 40);
		cancel.setBounds(50, 410, 100, 30);
		ok.setBounds(200, 410, 100, 30);
		// add action listener for buttons
		browse.addActionListener(this);
		cancel.addActionListener(this);
		ok.addActionListener(this);
		// add all components to panel
		contentPane.add(Heading);
		contentPane.add(lblName);
		contentPane.add(name);
		contentPane.add(warningLblName);
		contentPane.add(lblImage);
		contentPane.add(warningLblImage);
		contentPane.add(showimage);
		contentPane.add(browse);
		contentPane.add(lblPrice);
		contentPane.add(warningLblPrice);
		contentPane.add(price);
		contentPane.add(cancel);
		contentPane.add(ok);
		warningLblName.setVisible(false);
		warningLblImage.setVisible(false);
		warningLblPrice.setVisible(false);
		getContentPane().add(contentPane);// add panel to frame
		setSize(365, 487);// set frame size
		setLocationRelativeTo(null);// set frame's location to center of screen
		setVisible(true);// set frame visible
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// disappear on close
	}// end of constructor

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == browse) {// action when browse button is clicked

			JFileChooser choose = new JFileChooser(new File("C:\\Users\\user\\Desktop"));// a file chooser to choose
																							// picture
			int save = choose.showSaveDialog(null);
			if (save == JFileChooser.APPROVE_OPTION) {// if something is choose
				String path = "" + choose.getSelectedFile().getAbsolutePath();// get the path
				imgPath = path.replace("\\", "\\\\");// modify the path
				addimage.setIcon(new ImageIcon(imgPath));// add picture to addimage to show on frame
			}
		} else if (e.getSource() == cancel)
			dispose();// if cancel button is clicked,disappear the frame
		else {// if ok button is clicked
			if (checkRequired())// if all field is filled
				if (addMenu())// if adding to database is ok
					dispose();// disappear frame
		}
	}// end of actionPerformed

	private boolean checkRequired() {// function to check required field
		boolean isFill = false;
		if (name.getText().equals("")) // if Name is blank
			warningLblName.setVisible(true);
		else
			warningLblName.setVisible(false);
		if (imgPath.equals("")) // if picture is blank
			warningLblImage.setVisible(true);
		else warningLblImage.setVisible(false);
		if(price.getText().equals("")) // if price is blank
			warningLblPrice.setVisible(true);
		else warningLblPrice.setVisible(false);
		if(name.getText().equals("")||imgPath.equals("")||price.getText().equals(""))
			isFill=false;
		else isFill=true;
		return isFill;
		
	}// end of checkReqired function

	private boolean addMenu() {// a function to add menu into database

		Server server = new Server();// create a server object to perform database operation
		// to read image
		File f = new File(imgPath);
		FileInputStream fs = null;
		byte[] image = new byte[(int) f.length()];

		PreparedStatement sql = null;// a PreparedStatement for dataBase query
		try {
			// reading image
			fs = new FileInputStream(f);
			fs.read(image);
			server.connectionCreate();// create server connection
			sql = (PreparedStatement) server.con
					.prepareStatement("INSERT INTO menu (Name,Image,Price,item_sub_type) values(?,?,?,?)");// modify sql

			sql.setString(1, name.getText());// set first value name
			sql.setBytes(2, image);// set second value image
			sql.setString(3, price.getText());// set third value price
			sql.setString(4, subItemName);// set fourth value subItemName

		} catch (SQLException | IOException e) {
			System.out.print("Do not connect to DB-Error:" + e);
		}
		String Duplicate = server.UpdatetoUniquevaluetable(sql);// insert new menu to database
		server.connectionClose();// connection closed
		if (Duplicate.equals("")) {// if Duplicate equals ""(empty string)

			// give a Information Message Dialog

			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			UIManager.put("OptionPane.background", new ColorUIResource(Color.white));
			UIManager.put("Panel.background", new ColorUIResource(Color.white));
			JOptionPane.showMessageDialog(null, "Successuflly Added", "Menu Added", JOptionPane.INFORMATION_MESSAGE);
			return true;

		} else {// if Duplicate has string value
			// give warning message
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Times New Roman", Font.BOLD, 18)));
			JOptionPane.showMessageDialog(null, "This menu name already exits.", "Menu exits",
					JOptionPane.WARNING_MESSAGE);
			return false;

		}
	}// end of AddMenu function
}// end of class