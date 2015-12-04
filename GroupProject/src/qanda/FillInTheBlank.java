package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class FillInTheBlank extends Question {
	//represents a blank in the question
	private static final String blankCharacter = "@@@@";
	private static final String blankReplacementHTML = "_______";
	
	private int count; 
	
	public FillInTheBlank(DatabaseConnection connection, int id) {
		super(connection, id);
		int lastIndex = 0;

		while(lastIndex != -1){
		    lastIndex = question.indexOf(blankCharacter,lastIndex);
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += blankCharacter.length();
		    }
		}
	}
	
	
	
	@Override
	boolean checkValidQuestion() {
		if (!super.checkValidQuestion()) return false;
		if (question.indexOf(blankCharacter) == -1)return false;
		return true;
	}
	
	@Override
	public String getQuestionHTML(int questionOrder) {
		String questionHTML = this.question.replaceAll(blankCharacter, blankReplacementHTML);
		return "<div class=\"result-selected-class\">" + Integer.toString(questionOrder+1) + ". " + questionHTML + "</div>";
	}
	
	@Override
	public String getResponseInputHTML(){
		String response = "";
		
		for (int i = 0; i < count; i++){
			response+=super.getResponseInputHTML();
			response+="<br>";
		}
		return response; 
	}
}
