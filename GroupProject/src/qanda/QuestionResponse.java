package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class QuestionResponse extends Question {
		
	/*
	 * Construct an instance of QuestionResponse populated with question info fetched from the db, given a questionId
	 */
	
	public QuestionResponse(int id) {
		super(id);
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
