package Common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.Server;

/**
 * @author NaingNaingOo
 *This class is to get quarter and delicost from delivery table and promotion price from admin table
 */
public class QuarterAndPromo {
	
	Server server=new Server();
	
	public ArrayList<Integer> GetQuarterID() {//a function to return arrayList that contain Quarter ID
		ArrayList<Integer> id=new ArrayList<Integer>();
		try {
			server.connectionCreate();
			ResultSet getID = server
					.select("select id from delivery ORDER BY Quarter ASC");//select all quarter from delivery
																					 
			while (getID.next()) {
				id.add(getID.getInt("id"));// add all quarter name from database to array list quarter
			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}//end of GetQuarterID function
	
	public ArrayList<String> GetQuarterFromDB() {//a function to return arrayList that contain Quarter name
		ArrayList<String> quar = new ArrayList<String>();
		try {
			server.connectionCreate();
			ResultSet quarter = server
					.select("select Quarter from delivery ORDER BY Quarter ASC");
			while (quarter.next()) {
				quar.add(quarter.getString("Quarter"));// add all quarter name from database to array list quarter
			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quar;
	}//end of GetQuarterFromDB function
	
	public String[] GetQuarter() {//a function to return array that contain Quarter name
		String Quarter[];
		// string array List to add quarter form database

		ArrayList<String> quar=GetQuarterFromDB();
		Quarter = new String[quar.size()];// create String array with size +1 of quar array list
		for (int i = 0; i < quar.size(); i++) {
			Quarter[i] = quar.get(i);// add all quarter name of array list quar to Quarter String array
		}
		return Quarter;
	}//end of GetQuarter function
	
	public String[] GetQuarterWithHeading() {//a function to return array that contain Quarter name with heading
		String Quarter[];

		ArrayList<String> quar=GetQuarterFromDB();
		Quarter = new String[quar.size()+1];// create String array with size +1 of quar array list
		Quarter[0] = "---Select Quarter---";// set string array Quarter first string as "-----Select Quarter-----"
		for (int i = 1; i < quar.size()+1; i++) {
			Quarter[i] = quar.get(i-1);// add all quarter name of array list quar to Quarter String array
		}
		return Quarter;
	}//end of GetQuarterWithHeading function
	
	public String[] GetDeliCost() {//a function to return array that contain deli_cost
		String cost[];
		ArrayList <String>Cost=new ArrayList<String>();
		try {
			server.connectionCreate();
			ResultSet delcost = server
					.select("select deli_cost from delivery ORDER BY Quarter ASC");//get all deli_cost form delivery table
			while (delcost.next()) {
				Cost.add(delcost.getString("deli_cost"));// add all quarter name from database to array list quarter
			}
			server.connectionClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cost=new String[Cost.size()];
		for(int i=0;i<Cost.size();i++) {
			cost[i]=Cost.get(i);
		}
		return cost;
	}//end of GetDeliCost function
	
	public int GetPromo() {//a function to return integer that contain promotion price
		 int promovalue=0;
		try {
			server.connectionCreate();
			ResultSet promotion = server.select("select Promo from admin");
			while (promotion.next()) {
				promovalue= promotion.getInt("Promo");
			}
			server.connectionClose();
			return promovalue;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	
	}//end of GetPromo function
}//end of class