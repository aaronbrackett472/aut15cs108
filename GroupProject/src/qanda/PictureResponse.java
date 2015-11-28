package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class PictureResponse extends Question {


	public PictureResponse(DatabaseConnection connection, int id){
		super(connection, id);
	}
		
	@Override
	public String getQuestionHTML(int questionOrder) {
		return "<div class=\"result-selected-class\">" + Integer.toString(questionOrder+1) + ". " + this.question + "</div>" + "<img src=\"" + this.imageUrl + "\" class=\"picture-response\" />";
	}

}
