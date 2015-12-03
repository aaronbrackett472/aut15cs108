package account;

import java.util.*;
import java.util.Date;

import database.DatabaseConnection;
import java.sql.*;

/**
 * Represent a single user of the quiz
 * @author Musyoka
 */
public class User{
	//Instance variables
	private String username;
	private DatabaseConnection connection;
	private Statement statement;
	private AccountManager accounts;
	private boolean isAdmin, isSuspended;
	private Date suspensionEnd;
	
	//Constant
    
	private static String friendshipTable = "Friendship";
	private static String achievementsTable = "Achievement";
	private static String historyTable = "History";
	private static String accountTable = "Accounts";

	public static String FRIENDSHIP_TABLE = "Friendship";

	
	/**
	 * Constructor
	 */
	public User(String username, DatabaseConnection connection){

		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + accountTable + " WHERE username = '" + username + "';");
		try {
			
			if(resultSet.next()){
				this.username = username;
				this.connection = connection;
				this.statement = connection.getStatement();
				this.accounts =  new AccountManager(connection);
			
				resultSet.first();
				this.isAdmin = resultSet.getBoolean("isAdmin");
				this.isSuspended = resultSet.getBoolean("suspended");

				this.suspensionEnd = resultSet.getDate("suspensionEnd");
				
			} else {
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Adds a friend to this user
	 * If the given friend is not a registered user, then the friend is not added
	 * Frienship is bidirectional relationship
	 * @param friendName name of the friend
	 */
	public void addFriend(String friendName) {
		if(!accounts.checkAccountExists(friendName)) return;
		//Ignore if the frindName is  already a friend to this user
		if(checkFriend(friendName)) return;
		try{
			String addQuerry = "INSERT INTO "+ FRIENDSHIP_TABLE + " (username1, username2) " +
					"VALUES('" + this.username + "', '" + friendName + "')" ;
			
			statement.executeUpdate(addQuerry);		
		} catch(SQLException e){
			e.printStackTrace();
		}
			
	}

	/**
	 * Checks whether this user has the given username as one of
	 * this user's friend
	 *
	 */
	public boolean checkFriend(String username) {
		String checkQuerry = "SELECT * FROM " + FRIENDSHIP_TABLE + " WHERE username1='"+ this.username + "'" +
							 "AND username2='" + username + "'";
		try{
			ResultSet rs = statement.executeQuery(checkQuerry);	
			if(rs.next()) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns performance history items of this user
	 * @return history performance history
	 */
	public ArrayList<HistoryItem> getPerfomanceHistory() {
		History history = new History(connection);
		return history.getHistoryByUsername(username);
	}

	/**
	 * Returns a set of all the friends of this user
	 * @return friends the fiends of this user
	 */
	public Set<String> getAllFriends() {
		Set<String> friends =  new HashSet<String>();
		String querry = "SELECT * FROM " + FRIENDSHIP_TABLE + " WHERE username1='"+ username + "'";
		try{
			ResultSet rs = statement.executeQuery(querry);
			while(rs.next()) {
				String friend  = rs.getString(2);
				friends.add(friend);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return friends;
	}

	/**
	 * Removes the given friends from the friends of this user
	 */
	public void removeFriend(String friendName) {
		String querry = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE username1='"+ this.username + "'" +
					    "AND username2='" + friendName + "'";
		try{
			statement.executeUpdate(querry);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the a list of achievement titles held by this user
	 * @return achievements
	 */	

//	public ArrayList<Achievement> getAchievements() {
//		ArrayList<Achievement> achievements = new ArrayList<Achievement>();
//		String query = "SELECT * FROM " + achievementsTable + " WHERE username='"+ username + "'";
//		try{
//			ResultSet rs = statement.executeQuery(query);	
//			while(rs.next()) {
//				String name = rs.getString(2);
//				String time = rs.getString(3);
//				Achievement item = new Achievement(name, time);
//				achievements.add(item);		
//			}
//		} catch(SQLException e) {
//			e.printStackTrace();
//		}				
//		return achievements;

	public ArrayList<AchievementItem> getAchievements() {
		Achievement achievements = new Achievement(connection);
		return achievements.getAchievementByUser(username);

	}
	
	public boolean isAdmin(){
		return this.isAdmin;
	}
	
	public boolean isSuspended() {
		if(this.isSuspended) {
			Date curDate = new Date();
			if(this.suspensionEnd.after(curDate)) {
				return true;
			} else return false;
		} else return false;
	}

}