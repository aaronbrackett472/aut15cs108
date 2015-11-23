package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class FillInTheBlank extends Question {
	//represents a blank in the question
	private static final String blankCharacter = "@@@@";
	
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
	public int evaluateAnswer(Answer answer) {
		StringAnswer stringAnswer = (StringAnswer) answer;
		if (stringAnswer.getResponse().equals(correctAnswer)){
			return this.score;
		}
		return 0;
	}

}
