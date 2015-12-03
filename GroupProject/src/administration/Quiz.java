package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static ArrayList<Quiz> getQuizzes() {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Quizzes;");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				quizzes.add(new Quiz(resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("createdBy"),
						resultSet.getString("description"),
						resultSet.getString("createdDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();
		
		return quizzes;
	}
}
