package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class PictureResponse extends Question {


	public PictureResponse(int id){
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
