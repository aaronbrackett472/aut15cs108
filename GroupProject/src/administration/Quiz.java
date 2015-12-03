package administration;

import database.DatabaseConnection;

public class Quiz {
	public final int id;
	public final String name;
	public final String author;
	public final String description;
	public final String createdAt;
	
	public Quiz(int id, String name, String author, String description, String createdAt) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.createdAt = createdAt;
	}
	
	public static void deleteQuiz(int id) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("DELETE FROM Quizzes WHERE id = " + id + ";");
		connection.close();
	}
}
