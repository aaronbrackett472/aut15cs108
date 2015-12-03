package account;

import database.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Performs all history item manipulations and stores the history items for the web app.
 * @author Musyoka
 *
 */

public class History {
	//Instance variables
	DatabaseConnection connection;
	
	//Constants
	public static String HISTORY_TABLE = "History";

	
	/**
	 * Constructor, requires a database connection
	 */
	public History(DatabaseConnection connection) {
		this.connection =  connection;
	}
	
	/**
	 * Stores a given HitoryItem item into the database
	 * @param item history item
	 */
	public void storeItem(HistoryItem item) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(item.getTime());
		
		String query = "INSERT INTO "+ HISTORY_TABLE + " (username, score, maxScore, quizId, dateTaken) " + 
						"VALUES('" + item.getUserName() + "', '" + item.getScore()+ "', '" + item.getMaxScore()+ "', '" + item.getQuizId()+ "', '" + dateString + "')" ;
		System.out.println(query);
		connection.executeUpdate(query);
	}
	
	/**
	 * Gets all history items from the database
	 * @return history  all history items
	 */
	public ArrayList<HistoryItem> getAllHistory() {
		ArrayList<HistoryItem> history =  new ArrayList<HistoryItem>();
		String querry = "SELECT * FROM " + HISTORY_TABLE;
		try{
			ResultSet rs = connection.executeQuery(querry);	
			while(rs.next()) {
				String username = rs.getString("username");
				int score  =  rs.getInt("score");
				int maxScore =  rs.getInt("maxScore");
				int quizId = rs.getInt("quizId");
				Date time = rs.getDate("dateTaken");
				HistoryItem item = new HistoryItem(username, score, maxScore, quizId, time);
				history.add(item);		
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return history;
	}
	
	/**
	 * Gets history items given a fieldname as a criteria
	 * @param fieldName any one of the HISTORY_TABLE fields
	 * @param fieldValue value of the field
	 * @return history history items for the given field
	 */
	public ArrayList<HistoryItem> getHistoryByField(String fieldName, String fieldValue) {
		ArrayList<HistoryItem> history =  new ArrayList<HistoryItem>();
		String querry = "SELECT * FROM " + HISTORY_TABLE + " WHERE " + fieldName + "='"+ fieldValue + "';";
		try{
			ResultSet rs = connection.executeQuery(querry);	
			while(rs.next()) {
				String username = rs.getString("username");
				int score  =  rs.getInt("score");
				int maxScore =  rs.getInt("maxScore");
				int quizId = rs.getInt("quizId");
				Date time = rs.getDate("dateTaken");
				HistoryItem item = new HistoryItem(username, score, maxScore, quizId, time);
				history.add(item);		
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return history;
		
	}
	
	/** 
	 *  Gets history items given a username as a criteria
	 * @param username any one of the HISTORY_TABLE fields
	 * @return history history items for the given username
	 */
	public ArrayList<HistoryItem> getHistoryByUsername(String username) {
		return getHistoryByField("username", username);
	}
	
	/** 
	 *  Gets history items given a quizId as a criteria
	 * @param quizId any one of the HISTORY_TABLE fields
	 * @return history history items for the given quizId
	 */
	public ArrayList<HistoryItem> getHistoryByQuizId(String quizId) {
		return getHistoryByField("quizId", quizId);
	}
}
