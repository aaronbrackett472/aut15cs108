package qanda;

import database.DatabaseConnection;
import database.DatabaseConnection.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Question {
	
	//Constants
	public static String questionTable = "Questions";
	
	int id, score, quizId;
	String question, correctAnswer, imageUrl, type;
	
	private Statement statement;
	private DatabaseConnection connection;
	
	// This needs to work across multiple questions type
	static int saveToDatabase(int quizId, String type, int score, String question, String correctAnswer, String imageUrl){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO " + questionTable + " (quizId, type, score, question, correctAnswer, imageUrl) VALUES('" +
			quizId + "', '" + type + "', '" + score + "', '" + question + "', '" + correctAnswer + "', '" + imageUrl;
		
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
	
	public Question() {
		
	}
	
	/*
	 * When initialized given an int id, it will fetch other values from the db 
	 * To use each of the question
	 */
	public Question(int id) {
		this.id = id;
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE id = '" + id + "';");
		try {
			resultSet.first();
			this.quizId = resultSet.getInt("quizId");
			this.type = resultSet.getString("type");
			this.score = resultSet.getInt("score");
			this.question = resultSet.getString("question");
			this.correctAnswer = resultSet.getString("correctAnswer");
			this.imageUrl = resultSet.getString("imageUrl");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	boolean checkValidQuestion() {
		if (score < 0)return false;
		return true;
	}
	
	// Dummy implementation - will always be overridden
	int evaluateAnswer(Answer answer) {
		return 1;
	};
	
}
