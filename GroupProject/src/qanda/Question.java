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
	public static String questionTable = "question";
	public static String choiceTable = "choice";
	
	int score;
	String question;
	
	private Statement statement;
	private DatabaseConnection connection;
	
	// This needs to work across multiple questions type
	static int saveToDatabase(int quizID, String type, String question, String correctAnswer, int questionIndex, String imageUrl){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO questions (quizID, type, question, correctAnswer, questionIndex, imageUrl) VALUES('" +
			quizID + "', '" + type + "', '" + question + "', '" + correctAnswer + "', '" + questionIndex  + "', '" + imageUrl;
		
		query += "');";
		connection.executeUpdate(query);
		int id = -1;
		ResultSet resultSet = connection.executeQuery("SELECT * FROM questions;");
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
	
	// Get quiz
	static ResultSet getQuestionsByQuizId(int quizID) {
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM questions WHERE quizId = '" + quizID + "');");
		
		return resultSet;
		
	}
	
	
	
	boolean checkValidQuestion() {
		if (score < 0)return false;
		return true;
	}
	
	abstract boolean isCorrect(Answer answer);
	
}
