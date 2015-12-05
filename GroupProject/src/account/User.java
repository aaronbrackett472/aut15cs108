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
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static int getUnreadMailCount(DatabaseConnection connection, String username){
		ResultSet resultSet = connection.executeQuery("SELECT COUNT(*) FROM messages WHERE Receiver = '" + username + "' AND IsRead=0;");
		try {
			resultSet.first();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public static int getFriendRequestCount(DatabaseConnection connection, String username){
		ResultSet resultSet = connection.executeQuery("SELECT COUNT(*) FROM FriendRequest WHERE Receiver = '" + username + "';");
		try {
			resultSet.first();
			return resultSet.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	/**
	 * Adds a friend to this user
	 * If the given friend is not a registered user, then the friend is not added
	 * Frienship is bidirectional relationship
	 * @param friendName name of the friend
	 */
	public void addFriend(String friendName) {
		System.out.println("friend to be added: " + friendName);
		if(!accounts.checkAccountExists(friendName)) return;
		//Ignore if the frindName is  already a friend to this user
		if(checkFriend(friendName)) return;
		try{
			String addQuerry1 = "INSERT INTO "+ FRIENDSHIP_TABLE + " (username1, username2) " +
					           "VALUES('" + this.username + "', '" + friendName + "')" ;
			String addQuerry2 = "INSERT INTO "+ FRIENDSHIP_TABLE + " (username1, username2) " +
					            "VALUES('" + friendName + "', '" + this.username + "')" ;
			
			statement.executeUpdate(addQuerry1);
			statement.executeUpdate(addQuerry2);	
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
	 * Removes the given friends from the friends of this user.
	 * The username is removed from friendNames list, and friendName
	 * is removed from usernamesList
	 */
	public void removeFriend(String friendName) {
		String querry1 = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE username1='"+ this.username + "'" +
					    "AND username2='" + friendName + "'";
		String querry2 = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE username1='"+ friendName + "'" +
			    		"AND username2='" + this.username + "'";
		try{
			statement.executeUpdate(querry1);
			statement.executeUpdate(querry2);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the a list of achievement titles held by this user
	 * @return achievements
	 */	



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