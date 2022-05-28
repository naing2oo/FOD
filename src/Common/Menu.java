package Common;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.Server;

/**
 * @author NaingNaingOo
 *This class is to get menu detail of menu table and menu categories table
 */
public class Menu {

	Server server = new Server();

	public ArrayList Get_Item_Sub_Type(String item_type) {//a function to return arrayList that contain subItemtype like burger, cake
		String sql = "SELECT Item_sub_type FROM menu_categories WHERE Item_type='" + item_type + "'";
		server.connectionCreate();
		ArrayList<String> itemSubType = new ArrayList<>();
		ResultSet data = server.select(sql);
		try {

			while (data.next())
				itemSubType.add(data.getString("Item_sub_type"));
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemSubType;
	}//end of Get_Item_Sub_Type function

	public ArrayList Get_Item_ID(String itemSubType) {//a function to return arrayList that contain item ID

		String sql = "SELECT id FROM menu WHERE item_sub_type='" + itemSubType + "' ORDER BY name ASC";
		server.connectionCreate();
		ArrayList<Integer> itemID = new ArrayList<>();
		ResultSet data = server.select(sql);
		try {

			while (data.next())
				itemID.add(data.getInt("id"));
			server.connectionClose();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemID;
	}//end of Get_Item_ID function

	public ArrayList Get_Item_Name(String itemSubType) {//a function to return arrayList that contain item Name 

		String sql = "SELECT name FROM menu WHERE item_sub_type='" + itemSubType + "' ORDER BY name ASC";
		server.connectionCreate();
		ArrayList<String> itemName = new ArrayList<>();
		ResultSet data = server.select(sql);
		try {

			while (data.next())
				itemName.add(data.getString("name"));

			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemName;
	}//end of Get_Item_Name function

	public ArrayList Get_Item_Image(String itemSubType) {//a function to return arrayList that contain item image 
		String sql = "SELECT image FROM menu WHERE item_sub_type='" + itemSubType + "' ORDER BY name ASC";
		server.connectionCreate();
		ArrayList<Image> itemImage = new ArrayList<>();
		ResultSet data = server.select(sql);
		byte images[];
		try {

			while (data.next()) {
				images = data.getBytes("Image");
				Image image = Toolkit.getDefaultToolkit().createImage(images);
				itemImage.add(image);

			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemImage;
	}//end of Get_Item_Image function

	public ArrayList Get_Item_Price(String itemSubType) {//a function to return arrayList that contain item price 
		
		String sql = "SELECT price FROM menu WHERE item_sub_type='" + itemSubType + "' ORDER BY name ASC";
		server.connectionCreate();
		ArrayList<String> itemPrice = new ArrayList<>();
		ResultSet data = server.select(sql);
		try {

			while (data.next())
				itemPrice.add(data.getString("price"));

			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemPrice;
	}//end of Get_Item_Price function 
}//end of class