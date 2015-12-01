//package QuizProject;
package account;

import database.*;
import java.sql.*;

/**
 * Keeps track of all user accounts for the quiz project.
 * Supports registration, access and verification of account user information
 *
 */

public class AccountManager{
	//Instance variables
	private DatabaseConnection connection;
	private static String accountsTable = "Accounts";
	

	/**
	 * Constructor
	 * Requires an open database connection
	 */
	public AccountManager(DatabaseConnection connection){
		this.connection =  connection;	
		
	}

	/**
	 * Adds a new user to the database.
	 * The  database does not allow resistration of a username more than once. 
	 * The use this method should use checkAccountExists method defined in 
	 * AccountManager class before adding a user
	 * @param username the user name
	 * @param password the password
	 */
	public void registerUser(String username, String password) {
		Statement statement =  connection.getStatement() ;
		String checkQuerry = "SELECT * FROM " + accountsTable + " WHERE username='"+ username + "'";
		try{
			ResultSet rs =  statement.executeQuery(checkQuerry);
			if(rs.next()) {
				//the record already exists
				return ;
			} else {
				//The username is not used
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
	 * Given username and password, verifies whether the username and password matches
	 * the username and password registered.
	 * @param username the username
	 * @param password the password
	 * @return status true/false
	 */
	public boolean verifyUser(String username, String password) {
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
	
}
