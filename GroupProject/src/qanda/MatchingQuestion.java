package qanda;

import database.DatabaseConnection;

public class MatchingQuestion extends Question {
	
	public MatchingQuestion(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	

	@Override
	public String getResponseInputHTML() {
		MatchingAnswer ma = new MatchingAnswer();
		ma.getMatchingByQuestionId(connection, this.id);
		
		StringBuilder returnString = new StringBuilder();
		returnString.append("<ul>");
		for(String answer: ma.column1) {
			returnString.append("<li id=\"choice-list\">" + answer + "&nbsp;&nbsp;&nbsp;<select name=\"response-" + this.id + "\" >");
			for(String choice: ma.column2) {
				returnString.append("<option value=\"" + choice + "\">" + choice + "</option>");
			}
			returnString.append("</select></li>");
		}
		returnString.append("</ul>");
		return returnString.toString();
	}
	
}
