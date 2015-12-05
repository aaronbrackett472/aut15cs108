package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;


public class Answer {
	
	public static String answerTable = "Answers";
	
	//public List<String> answerLists;
	private int questionId;
	public String answer;
	public int answerIndex;
	private boolean correct;
	private String prompt;
	
		
	public static void saveToDatabase(int questionID, String answer, int answerIndex, boolean correct, String prompt){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO Answers (questionID, answer, answerIndex, correct, prompt) VALUES(" +
			questionID + ", \"" + answer + "\", " + answerIndex + ", " + (correct?1:0) + ", \"" + prompt + "\");";
		System.out.println(query);
		connection.executeUpdate(query);
		connection.close();
	}
	
	public Answer(int questionId, String answer, int answerIndex, boolean correct, String prompt) {
		this.questionId = questionId;
		this.answer = answer;
		this.answerIndex = answerIndex;
		this.correct = correct;
		this.prompt = prompt;
		//answerLists = new ArrayList<String>();
	}
	
	public static List<Answer> getAnswersByQuestionId(DatabaseConnection connection, int questionId) {
		
		List<Answer> answerList = new ArrayList<Answer>();
		
		if(connection == null)connection = new DatabaseConnection();
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + answerTable + " WHERE questionID = '" + questionId + "';");
		//this.answerLists.clear();
		try {
			//this.questionId = questionId;
			while(resultSet.next() != false) {
				String answer = resultSet.getString("answer");
				int answerIndex = resultSet.getInt("answerIndex");
				boolean correct = resultSet.getBoolean("correct");
				String prompt = resultSet.getString("prompt");
				Answer a = new Answer(questionId, answer, answerIndex, correct, prompt);
				answerList.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answerList;
	}
	public static List<Answer> getCorrectAnswersByQuestionId(DatabaseConnection connection, int questionId) {
		
		List<Answer> answerList = new ArrayList<Answer>();
		
		if(connection == null)connection = new DatabaseConnection();
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + answerTable + " WHERE questionID = '" + questionId + "';");
		//this.answerLists.clear();
		try {
			//this.questionId = questionId;
			while(resultSet.next() != false) {
				boolean correct = resultSet.getBoolean("correct");
				if (!correct) continue;
				String answer = resultSet.getString("answer");
				int answerIndex = resultSet.getInt("answerIndex");
				
				String prompt = resultSet.getString("prompt");
				Answer a = new Answer(questionId, answer, answerIndex, correct, prompt);
				answerList.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return answerList;
	}
	
	public boolean isCorrect(String answer, int answerIndex){
		if (answer.trim().toLowerCase().equals(this.answer.trim().toLowerCase())){
			if (answerIndex == this.answerIndex){
				return true;
			}
		}
		return false;
	}
	
	public boolean isCorrectMatching(String prompt, String answer){
		if (answer.trim().toLowerCase().equals(this.answer.trim().toLowerCase())){
			if (prompt.trim().toLowerCase().equals(this.prompt.trim().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isCorrectString(String answer){
		if (answer.trim().toLowerCase().equals(this.answer.trim().toLowerCase())){
			return true;
		}
		return false;
	}
}
	
	
	