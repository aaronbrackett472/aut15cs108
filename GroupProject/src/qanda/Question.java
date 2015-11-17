package qanda;

import database.DatabaseConnection;
import database.DatabaseConnection.*;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
	/*
	 * Establish connection with the database
	 */
	public void establishDatabaseConnection() {
		this.connection = new DatabaseConnection();
		this.statement = connection.getStatement();
		
		// Needs to redesign db to add ID and to work with the rest of the question types
	    /*
	    Table: question
			+-------------------+--------------+------+-----+---------+----------------+
			| Field       		| Type         | Null | Key | Default | Extra          |
			+-------------------+--------------+------+-----+---------+----------------+
			| questionId  		| int(11)      | NO   | PRI | NULL    | auto_increment |
			| questionText		| int(11)      | NO   | MUL | NULL    |                |
			| score				| VARCHAR(64)  | NO   | MUL | NULL    |                |
			| isMultipleChoice  | TINYINT      | NO   |     | NULL    |                |
			| imageUrl		    | VARCHAR(64)  | NO   |     | NULL    |                |
			+-------------------+--------------+------+-----+---------+----------------+
		 */
		String friendshipQuery = "CREATE TABLE IF NOT EXISTS " + questionTable +
				" (questionId INT, " +
				" (questionText CHAR(64), " +
				" score VARCHAR(64), )" + 
				" isMultipleChoice TINYINT, )" + 
				" imageUrl VARCHAR(64) )";
		executeSQLQuery(friendshipQuery);
		
		// Create a table storing choices
		String choiceQuery = "CREATE TABLE IF NOT EXISTS " + choiceTable +
				  " (choiceText CHAR(64), " +
				  " questionId INT, )" + 
				  " isCorrect TINYINT, )" + 
				  " order VARCHAR(64) )";
		executeSQLQuery(choiceQuery);
	}
	
	public void executeSQLQuery(String query) {
		
		try {
			this.statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
