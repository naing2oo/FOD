package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author NaingNaingOo
 *This class creates to perform database connection, and other functions
 */
public class Server {

	String db = "jdbc:mysql://localhost/foodorderinganddelivering";// create db string with database location
	String user = "root";// create user string with database user name
	String password = "";// create password string with database password (here no password for database
							// cause I use xampp server)
	String driver = "com.mysql.cj.jdbc.Driver";// create driver string with driver
	public Connection con;// create database Connection object

	public void connectionCreate() {// a function to create connection with database
		try {
			Class.forName(driver);// declare driver
			con = DriverManager.getConnection(db, user, password);// get connection
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}//end of connectionCreate function

	public ResultSet select(String prep) {// a function to extract row from database and return resultSet
		PreparedStatement sql;// create a PreparedStatement
		ResultSet data = null;// create a resultSet with null
		try {
			sql = (PreparedStatement) con.prepareStatement(prep);// set PreparedStatement with function argument prep
			data = sql.executeQuery();// query execute and assign resultSet to data

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;// return data, resultSet
	}//end of select function

	public boolean InsertIntoOrder(PreparedStatement sql) {// a function to add row into customer_order table in db

		try {
			sql.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end of InsertIntoOrder function

	public String UpdatetoUniquevaluetable(PreparedStatement sql) {// a function to update or add new row to table with unique value
																	
		String Duplicate = "";// a string Duplicate is created with "" to catch phone number duplicate error
		try {
			sql.executeUpdate();// query execute to update

		} catch (SQLException e) {
			if (e.getMessage().contains("Duplicate")) {// if error message contains Duplicate word
				Duplicate = "Duplicate";// set Duplicate string value with Duplicate
			} else
				e.printStackTrace();
		}
		return Duplicate;// return Duplicate
	}//end of UpdatetoUniquevaluetable function

	public boolean Update(String prep) {// a function to update row in table without unique value
		PreparedStatement sql;// create a PreparedStatement
		try {
			sql = (PreparedStatement) con.prepareStatement(prep);// set PreparedStatement with function argument prep
			sql.executeUpdate();// query execute 
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end of Update function

	public boolean Delete(String prep) {//a function to delete a row from table
		PreparedStatement sql;

		try {
			sql = (PreparedStatement) con.prepareStatement(prep);// set PreparedStatement with function argument prep
			sql.executeUpdate();// query execute 
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end of Delete function

	public void connectionClose() {// a function to close db connection
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//end of connectionClose function
}