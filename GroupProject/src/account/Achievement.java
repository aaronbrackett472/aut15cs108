package account;

import database.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Avails access and manipulation of achievement items
 * @author Musyoka
 */
public class Achievement {
	//Instance variables
	DatabaseConnection connection;
	
	//Constants
	public static String ACHIEVEMENT_TABLE = "Achievement";
	
	/**
	 * Constructor
	 * @param connection open database connection
	 */
	public Achievement(DatabaseConnection connection) {
		this.connection =  connection;
	}
	
	/** 
	 * Adds an AchievementItem into the database
	 * @param  item the achievement item
	 */
	public void storeAchievementItem(AchievementItem item) {
		String querry = "INSERT INTO "+ ACHIEVEMENT_TABLE + " (username, achievementName, achievementDescription, image, dateAcquired) " + 
						"VALUES('" + item.getUserName() + "', '" + item.getAchievementName() + "', '" + item.getDescription() + "', '"+ item.getImageLink()+ "', '" + item.getDateAcquired()+ "')" ;
		connection.executeUpdate(querry);
	}
	
	/**
	 * Gets all achievements by in the database by all users
	 * @return achievements all achievements
	 */
	public ArrayList<AchievementItem> getAllAchievements() {
		String querry = "SELECT * FROM " + ACHIEVEMENT_TABLE;
		return getAchievementsByQuery(querry);
	}
	
	/**
	 * Get achievementItems by username
	 * @param username the user's name
	 * @return achievements all achievements by the user
	 *
	 */
	public ArrayList<AchievementItem> getAchievementByUser(String user) {
		String querry = "SELECT * FROM " + ACHIEVEMENT_TABLE + " WHERE username='" + user + "'";
		return getAchievementsByQuery(querry);
	}
	
	/**
	 * A helper method, gets achievements given a query string
	 * @param querry query string
	 * @return achievements achievement items according to the query
	 * 
	 */
	private ArrayList<AchievementItem> getAchievementsByQuery(String querry) {
		ArrayList<AchievementItem> achievements =  new ArrayList<AchievementItem>();
		try{
			ResultSet rs = connection.executeQuery(querry);	
			while(rs.next()) {
				String username = rs.getString(1);
				String achievementName = rs.getString(2);
				String achievementDescription = rs.getString(3);
				String imageLink =  rs.getString(4);
				String dateAcquired =  rs.getString(5);
				AchievementItem item = new AchievementItem(username, achievementName, achievementDescription, imageLink, dateAcquired);
				achievements.add(item);		
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return achievements;
	}
	
	
	
}
