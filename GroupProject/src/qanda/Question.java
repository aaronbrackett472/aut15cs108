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
	protected DatabaseConnection connection;
	
	// This needs to work across multiple questions type
	public static int saveToDatabase(DatabaseConnection connection, int quizId, String type, int score, String question, String imageUrl){
		//DatabaseConnection connection = new DatabaseConnection();
		String correctAnswer = "";
		
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
		
		return id;
	}
	
	// Get all questions given a quiz id
	static ResultSet getQuestionsByQuizId(DatabaseConnection connection, int quizId) {
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE quizId = '" + quizId + "';");
		return resultSet;
	}
	
	public Question() {
		
	}
	
	/*
	 * When initialized given an int id, it will fetch other values from the db 
	 * To use each of the question
	 */
	public Question(DatabaseConnection connection, int id) {
		this.id = id;
		
		// Storing a connection - is this a good idea? What if it gets closed?
		this.connection = connection;
		
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
	
	public int evaluateAnswer(String answer) {
		if (answer.trim().toLowerCase().equals(correctAnswer.trim().toLowerCase())){
			return this.score;
		}
		return 0;
	}
	
	public String getQuestionHTML(int questionOrder) {
		return "<div class=\"result-selected-class\">" + Integer.toString(questionOrder+1) + ". " + this.question + "</div>";
	}
	
	public String getResponseInputHTML() {
		return "<input id=\"answer-input-box\" type=\"text\" name=\"response-" + this.id + "\" placeholder=\"Your Answer\" />";
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getQuestionId(){
		return this.id;
	}
	
	public int getScore() {
		return this.score;
	}
	
}
