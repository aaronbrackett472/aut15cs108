package qanda;

import database.DatabaseConnection;
import javafx.util.Pair;

public class MatchingQuestion extends Question {
	
	public MatchingQuestion(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	

	@Override
	public String getResponseInputHTML() {
		ChoiceSet cs = new ChoiceSet();
		cs.getChoicesByQuestionId(this.connection, this.id);
		StringBuilder returnString = new StringBuilder();
		returnString.append("<ul>");
		for(Pair<String, Boolean> choice : cs.choicesList) {
			
			returnString.append("<li id=\"choice-list\"><input type=\"radio\" name=\"response-" + this.id + "\" value=\"" + choice.getKey() + "\"> " + choice.getKey() + "</li>");
		}
		returnString.append("</ul>");
		return returnString.toString();
	}
	
}
