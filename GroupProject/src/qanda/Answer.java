package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;


public class Answer {
		
	public static void saveToDatabase(int questionID, String answer, int answerIndex, boolean correct, String prompt){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO Answers (questionID, answer, answerIndex, correct, prompt) VALUES(" +
			questionID + ", " + answer + ", " + answerIndex + ", " + correct + ", " + prompt + ";";
		connection.executeUpdate(query);
		connection.close();
	}
}
