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
	
	@Override
	public String getQuestionHTML(int questionOrder) {
		return "<div class=\"result-selected-class\">" + Integer.toString(questionOrder+1) + ". " + this.question + "</div>" + "<img src=\"" + this.imageUrl + "\" class=\"picture-response\" />";
	}

}
