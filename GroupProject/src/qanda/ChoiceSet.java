package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.util.Pair;

public class ChoiceSet {
	
	private static String choicesTable = "Choices";
	
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
	void getChoicesByQuestionId(int questionId) {
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + choicesTable + " WHERE questionId = '" + questionId + "';");
		this.choicesList.clear();
		try {
			this.questionId = questionId;
			while(resultSet.next() != false) {
				this.addChoices(resultSet.getString("choice"), resultSet.getBoolean("isCorrect"));
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
	 * return -1 if save fail
	 */
	int saveToDatabase(){
		
		int id = -1;
		DatabaseConnection connection = new DatabaseConnection();
		for(Pair<String, Boolean> choice: choicesList) {
			String query = "INSERT INTO " + choicesTable + " (questionId, choice, isCorrect) VALUES('" + 
			this.questionId + "', '" + choice.getKey() + "', '" + convertBooleanToInt(choice.getValue()) + "');";
			//System.out.println(query);
			connection.executeUpdate(query);
		}
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + choicesTable + " WHERE questionId ='" + this.questionId + "';");
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
	
}
