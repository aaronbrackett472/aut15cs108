package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DatabaseConnection;
import utilities.Utilities;

public class Announcement {
	
	public final int id;
	public final String author;
	public final String header;
	public final String body;
	public final Date createdAt;
	
	public static List<Announcement> getRecentAnnouncements(DatabaseConnection connection, int limit) {
		
		List<Announcement> announcements = new ArrayList<Announcement>(limit);
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Announcements ORDER BY createdAt DESC LIMIT " + limit + ";");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				announcements.add(new Announcement(resultSet.getInt("id"),
						resultSet.getString("author"),
						resultSet.getString("header"),
						resultSet.getString("body"),
						resultSet.getDate("createdAt")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return announcements;
		
	}
	
	public static void createAnnouncement(String author, String header, String body) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("INSERT INTO Announcements (author, header, body) VALUES("
				+ author + ", "
				+ header + ", "
				+ body + ");");
		connection.close();
	}
	
	public static void updateAnnouncement(int id, String header, String body) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("UPDATE Announcements SET header = \"" + header
				+ "\" WHERE id = " + id + ";");
		connection.executeUpdate("UPDATE Announcements SET body = \"" + body
				+ "\" WHERE id = " + id + ";");
		connection.close();
	}
	
	public static void deleteAnnouncement(int id) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("DELETE FROM Announcements WHERE id = " + id + ";");
		connection.close();
	}
	
	public Announcement(int id, String author, String header, String body, Date createdAt) {
		this.id = id;
		this.author = author; 
		this.header = header;
		this.body = body;
		this.createdAt = createdAt;
	}
}
