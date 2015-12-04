package administration;

import database.DatabaseConnection;
import utilities.Utilities;

public class User {
	
	public final String username;
	public final boolean isAdmin;
	public final boolean suspended;
	public final String suspensionEnd;
	
	public User(String username, boolean isAdmin, boolean suspended, String suspensionEnd) {
		this.username = username;
		this.isAdmin = isAdmin;
		this.suspended = suspended;
		this.suspensionEnd = suspended ? suspensionEnd : "--";
	}
	
	public static void deleteUser(String username) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("DELETE FROM Accounts WHERE username = \"" + username + "\";");
		connection.close();
	}
	
	public static void promoteUser(String username) {
		updateUserAdminStatus(username, true);
	}
	
	public static void demoteUser(String username) {
		updateUserAdminStatus(username, false);
	}
	
	private static void updateUserAdminStatus(String username, boolean promoted) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("UPDATE Accounts SET isAdmin = " + (promoted ? 1 : 0)
				+ " WHERE username = \"" + username + "\";");
		connection.close();
	}
	
	public static void suspendUser(String username, int days) {
		updateUserSuspension(username, true, days);
	}
	
	public static void unsuspendUser(String username) {
		updateUserSuspension(username, false, 0);
	}
	
	private static void updateUserSuspension(String username, boolean suspended, int days) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("UPDATE Accounts SET suspended = " + (suspended ? 1 : 0)
				+ " WHERE username = \"" + username + "\";");
		connection.executeUpdate("UPDATE Accounts SET suspensionEnd = \""
				+ (suspended ? Utilities.getCurrentDate() : Utilities.getFutureDateInDays(days))
				+ "\" WHERE username = \"" + username + "\";");
		connection.close();
	}
}
