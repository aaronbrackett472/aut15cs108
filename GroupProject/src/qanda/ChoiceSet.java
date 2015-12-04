package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.util.Pair;

public class ChoiceSet {
	
	public static String choicesTable = Answer.answerTable;
	
	int questionId;
	public List<Pair<String, Boolean>> choicesList;
	
	/*
	 * Default constructor
	 * To display choices to the user (when displaying a quiz) create an instance of ChoiceSet
	 * Invoke the getChoicesByQuestionId method
	 * If creating a new question and want to save this to db, add choices using the addChoices method.
	 * After that, invoke the SaveToDatabase() method.
	 */
	public ChoiceSet(int questionId){
		this.questionId = questionId;
		this.choicesList = new ArrayList<Pair<String, Boolean>>();
	}
	
	public ChoiceSet(){
		this.choicesList = new ArrayList<Pair<String, Boolean>>();
	}
	
	/*
	 * After constructing the ChoiceSet instance o
	 * Invoke o.getChoicesByQuestionId(int questionId) to populate the instance with choices from db 
	 */
	void getChoicesByQuestionId(DatabaseConnection connection, int questionId) {
		//DatabaseConnection connection = new DatabaseConnection();
		System.out.println(questionId);
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + choicesTable + " WHERE questionID = " + questionId + ";");
		this.choicesList.clear();
		try {
			this.questionId = questionId;
			while(resultSet.next() != false) {
				this.addChoices(resultSet.getString("answer"), resultSet.getBoolean("correct"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addChoices(String choice, boolean isCorrect) {
		choicesList.add(new Pair<String, Boolean>(choice, isCorrect));
	}
	
	int convertBooleanToInt(Boolean val) {
		if(val){
			return 1;
		}
		return 0;
	}
	
	/*
	 * if the ChoiceSet instance is newly created and choice values are added manually by the client
	 * Invoke saveToDatabase(connection) to save it to the ChoiceSet table
	 * return -1 if save fail
	 */
	int saveToDatabase(DatabaseConnection connection){
		
		int id = -1;
		int choiceIndex = 0;
		//DatabaseConnection connection = new DatabaseConnection();
		for(Pair<String, Boolean> choice: choicesList) {
			String query = "INSERT INTO " + choicesTable + " (questionID, answer, answerIndex, correct) VALUES('" + 
			this.questionId + "', '" + choice.getKey() + "', '" +  choiceIndex + "', '" + convertBooleanToInt(choice.getValue()) + "');";
			connection.executeUpdate(query);
			choiceIndex++;
		}
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + choicesTable + " WHERE questionID ='" + this.questionId + "';");
		try {
			resultSet.last();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//connection.close();
		return id;
	}
	
}
