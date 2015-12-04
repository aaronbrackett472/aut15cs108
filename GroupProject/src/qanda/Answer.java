package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;


public class Answer {
	
	public static String answerTable = "Answers";
	
	public List<String> answerLists;
	int questionId;
		
	public static void saveToDatabase(int questionID, String answer, int answerIndex, boolean correct, String prompt){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO Answers (questionID, answer, answerIndex, correct, prompt) VALUES(" +
			questionID + ", \"" + answer + "\", " + answerIndex + ", " + (correct?1:0) + ", \"" + prompt + "\");";
		System.out.println(query);
		connection.executeUpdate(query);
		connection.close();
	}
	
	public Answer() {
		answerLists = new ArrayList<String>();
	}
	
	void getAnswersByQuestionId(DatabaseConnection connection, int questionId) {
		
		if(connection == null)connection = new DatabaseConnection();
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + answerTable + " WHERE questionID = '" + questionId + "';");
		this.answerLists.clear();
		try {
			this.questionId = questionId;
			while(resultSet.next() != false) {
				answerLists.add(resultSet.getString("answer"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
