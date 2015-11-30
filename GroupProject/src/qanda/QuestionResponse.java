package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class QuestionResponse extends Question {
		
	/*
	 * Construct an instance of QuestionResponse populated with question info fetched from the db, given a questionId
	 */
	
	public QuestionResponse(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	
}
