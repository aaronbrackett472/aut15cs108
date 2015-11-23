package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;


public class Answer {
	
	//-1 for no index (i.e. not multiple choice)
	
	static int saveToDatabase(int questionID, String answer, int answerIndex){
		DatabaseConnection connection = new DatabaseConnection();
		String query = "INSERT INTO answers (questionID, answer, answerIndex) VALUES(" +
			questionID + ", " + answer + ", " + answerIndex + ";";
		connection.executeUpdate(query);
		int id = -1;
		ResultSet resultSet = connection.executeQuery("SELECT * FROM answers;");
		try {
			resultSet.last();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return id;
	}
}
