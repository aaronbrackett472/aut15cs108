package qanda;

import database.DatabaseConnection;
import database.DatabaseConnection.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class Question {
	
	//Constants
	public static String questionTable = "Questions";
	
	int id, score;
	String question;
	
	private Statement statement;
	private DatabaseConnection connection;
	
	// This needs to work across multiple questions type
	static int saveToDatabase(int quizID, String type, String question, String correctAnswer, String imageUrl){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO " + questionTable + " (quizID, type, question, correctAnswer, imageUrl) VALUES('" +
			quizID + "', '" + type + "', '" + question + "', '" + correctAnswer + "', '" + imageUrl;
		
		query += "');";
		
		connection.executeUpdate(query);
		int id = -1;
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + ";");
		try {
			resultSet.last();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return id;
	}
	
	// Get all questions given a quiz id
	static ResultSet getQuestionsByQuizId(int quizId) {
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE quizId = '" + quizId + "';");
		return resultSet;
		
	}
	
	
	
	boolean checkValidQuestion() {
		if (score < 0)return false;
		return true;
	}
	
	abstract int evaluateAnswer(Answer answer);
	
}
