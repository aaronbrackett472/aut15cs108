package qanda;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.DatabaseConnection;
import javafx.util.Pair;

public class MultipleChoice extends Question {
	
	public MultipleChoice(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	
	@Override
	public int evaluateAnswer(String answer){

		int correctCount = 0;
		ChoiceSet choices = new ChoiceSet();
		choices.getChoicesByQuestionId(this.connection, this.id);
		
		for (Pair<String, Boolean> choice: choices.choicesList){
			// If choice is true
			if(choice.getValue()) {
				if(choice.getKey().trim().toLowerCase().equals(answer.trim().toLowerCase())) {
					correctCount++;
				}
			}
		}
		
		if (correctCount > 0) {
			return this.score;
		} else {
			return 0;
		}
	}
	
	@Override
	public String getResponseInputHTML() {
		ChoiceSet cs = new ChoiceSet();
		cs.getChoicesByQuestionId(this.connection, this.id);
		String returnString = "";
		returnString+="<ul>";
		System.out.println(cs.choicesList.size());
		for(Pair<String, Boolean> choice : cs.choicesList) {
			
			returnString+="<li id=\"choice-list\"><input type=\"radio\" name=\"response-" + this.id + "\" value=\"" + choice.getKey() + "\"> " + choice.getKey() + "</li>";
		}
		returnString+="</ul>";
		return returnString;
	}
}
