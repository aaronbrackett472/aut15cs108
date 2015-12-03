package administration;

import database.DatabaseConnection;
import utilities.Utilities;

public class Announcement {
	
	private int id;
	private String author;
	private String header;
	private String body;
	private String createdAt;
	
	public static void createAnnouncement(String author, String header, String body) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("INSERT INTO Announcements (author, header, body) VALUES("
				+ author + ", "
				+ header + ", "
				+ body + ");");
		connection.close();
	}
	
	public Announcement(int id, String author, String header, String body, String createdAt) {
		this.id = id;
		this.author = author; 
		this.header = header;
		this.body = body;
		this.createdAt = createdAt;
	}
}
