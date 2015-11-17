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
	
	static int saveToDatabase(int quizID, String type, String question, String correctAnswer, int questionIndex, String imageUrl){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO questions (quizID, type, question, correctAnswer, questionIndex, imageUrl) VALUES(" +
			quizID + ", " + type + ", " + question + ", " + correctAnswer + ", " + questionIndex;
		if (imageUrl != null){
			query += ", " + imageUrl;
		}
		query += ";";
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
	
	
	
	boolean checkValidQuestion() {
		if (score < 0)return false;
		return true;
	}
	
	// Default constructor - why?
	public Question(){
		score = 1;
		question = "";
	}
	
	public Question(int score, String question) {
		this.score = score;
		this.question = question;
	}
	
	abstract boolean isCorrect(Answer answer);
	
}
