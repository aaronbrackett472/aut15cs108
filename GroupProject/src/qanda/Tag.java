package qanda;

import database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Tag {

	public static void saveToDatabase(String tag, int quizID){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO Tags (tag, quizID) VALUES(\"" +
			 tag + "\", " + quizID + ");";
		connection.executeUpdate(query);
		connection.close();
	}
	
	public static ArrayList<Integer> searchByTag(String tag){
		ArrayList<Integer> returnIDs = new ArrayList<Integer>();
		String query = "SELECT * FROM Tags WHERE tag =\"" + tag + "\";";
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet rs = connection.executeQuery(query);
		try {
			while (rs.next()){
				int newID = rs.getInt("quizID");
				returnIDs.add(newID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnIDs;
	}
	
	
}
