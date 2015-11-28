package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class FillInTheBlank extends Question {
	//represents a blank in the question
	private static final String blankCharacter = "@@@@";
	private static final String blankReplacementHTML = "_______";
	
	public FillInTheBlank(int id) {
		super(id);
	}
	
	@Override
	boolean checkValidQuestion() {
		if (!super.checkValidQuestion()) return false;
		if (!question.contains(blankCharacter))return false;
		return true;
	}
	
	@Override
	public String getQuestionHTML(int questionOrder) {
		String questionHTML = this.question.replace(blankCharacter, blankReplacementHTML);
		return "<div class=\"result-selected-class\">" + Integer.toString(questionOrder+1) + ". " + questionHTML + "</div>";
	}

}
