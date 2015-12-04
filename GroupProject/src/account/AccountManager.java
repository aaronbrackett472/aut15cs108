//package QuizProject;
package account;

import database.*;
import java.sql.*;
import java.security.*;
import java.util.*;

/**
 * Keeps track of all user accounts for the quiz project.
 * Supports registration, access and verification of account user information
 *
 */
public class AccountManager{
	//Instance variables
	private DatabaseConnection connection;
	private MessageDigest md;
	private static String accountsTable = "Accounts";
	

	/**
	 * Constructor
	 * Requires an open database connection
	 */
	public AccountManager(DatabaseConnection connection){
		this.connection =  connection;
		try{
			md = MessageDigest.getInstance("SHA");
		} catch(NoSuchAlgorithmException excep) {
			excep.printStackTrace();
		}
		
	}

	/**
	 * Adds a new user to the database.
	 * The  database does not allow resistration of a username more than once. 
	 * The use this method should use checkAccountExists method defined in 
	 * AccountManager class before adding a user.
	 * Empty usernames will not be registered
	 * @param username the user name
	 * @param password the password
	 */
	public void registerUser(String username, String password) {
		if(username.isEmpty()) return;
		Statement statement =  connection.getStatement() ;
		String checkQuerry = "SELECT * FROM " + accountsTable + " WHERE username='"+ username + "'";
		try{
			ResultSet rs =  statement.executeQuery(checkQuerry);
			if(rs.next()) {
				//the record already exists
				return ;
			} else {
				//The username is not used
				password = generateHashedPassword(password);
				String addQuerry = "INSERT INTO "+ accountsTable + " (username, password) " +
									"VALUES('" + username + "', '" + password + "')" ;
				statement.executeUpdate(addQuerry);			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
					
	}

	/**
	 * Given username, return the user associated with the password.
	 * The user of this method should first verify that the user exists using 
	 * verifyUser method in this class
	 * @param username the user name
	 * @return user the user object which matches the username
	 */
	public User getUser(String username){	
		return new User(username, connection);
	}
	/**
	 * Return all the username of the users in the database
	 * @return allUsers  ArrayList of all the users
	 */
	public ArrayList<String> getAllUsers() {
		ArrayList<String> allUsers =  new ArrayList<String>();
		String querry = "SELECT username FROM " + accountsTable ;
		ResultSet result = connection.executeQuery(querry);
		try{
			while(result.next()) {
				allUsers.add(result.getString("username"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return allUsers;
		
	}
	
	/*
	 * Return all the users whose names match given search string
	 */
	public List<String>findSearchedUsers(String query) {
		List<String> usernames = new ArrayList<String>();
		String sql = "SELECT username FROM " + accountsTable + " WHERE username LIKE '" + query + "'";
		ResultSet result = connection.executeQuery(sql);
		try{
			while(result.next()) {
				usernames.add(result.getString("username"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return usernames;
	}
	/**
	 * Given username and password, verifies whether the username and password matches
	 * the username and password registered.
	 * @param username the username
	 * @param password the password
	 * @return status true/false
	 */
	public boolean verifyUser(String username, String password) {
		password = generateHashedPassword(password);
		String checkQuerry = "SELECT * FROM " + accountsTable + " WHERE username='"+ username + "'";
		Statement statement =  connection.getStatement();
		try{
			ResultSet rs = statement.executeQuery(checkQuerry);	
			if(rs.next()) {
				String storedUsername = (String)rs.getObject(1);
				String storedPassword = (String)rs.getObject(2);
				if(storedUsername.equals(username) && storedPassword.equals(password)) {
					return true;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	} 

	/**
	 * Checks if the username given matches any other username regitered
	 * in the database
	 *
	 */
	public boolean checkAccountExists(String username){
		String checkQuerry = "SELECT * FROM " + accountsTable + " WHERE username='"+ username + "'";
		Statement statement =  connection.getStatement();
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
	 * Takes in a password and returns out the corresponding
	 * hashed password value
	 *
	 */
	private String generateHashedPassword(String password) {
		String hashedPassword ="";
		md.reset();
		md.update(password.getBytes());
		hashedPassword = hexToString(md.digest());
		return hashedPassword;
	}
	
	/**
	 *Given a byte[] array, produces a hex String,
	 *such as "234a6f". with 2 chars for each byte in the array.
	 * (provided code)
	 */
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

}
