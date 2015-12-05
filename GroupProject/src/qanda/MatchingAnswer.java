package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.DatabaseConnection;
import javafx.util.Pair;

public class MatchingAnswer {
public static String choicesTable = Answer.answerTable;
	
	int questionId;
	public List<String> column1, column2;
	
	/*
	 * Default constructor
	 * To display choices to the user (when displaying a quiz) create an instance of ChoiceSet
	 * Invoke the getChoicesByQuestionId method
	 * If creating a new question and want to save this to db, add choices using the addChoices method.
	 * After that, invoke the SaveToDatabase() method.
	 */
	public MatchingAnswer(){
		
	}
	
	
	/*
	 * After constructing the ChoiceSet instance o
	 * Invoke o.getChoicesByQuestionId(int questionId) to populate the instance with choices from db 
	 */
	void getMatchingByQuestionId(DatabaseConnection connection, int questionId) {
		//DatabaseConnection connection = new DatabaseConnection();
		System.out.println(questionId);
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + choicesTable + " WHERE questionID = " + questionId + ";");

		this.column1 = new ArrayList<String>();
		this.column2 = new ArrayList<String>();
		this.questionId = questionId;
		
		try {
			
			while(resultSet.next() != false) {
				this.column1.add(resultSet.getString("answer"));
				this.column2.add(resultSet.getString("prompt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Collections.shuffle(this.column2);
		
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

}
