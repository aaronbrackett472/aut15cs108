package qanda;

import java.util.Arrays;
import java.util.List;

import database.DatabaseConnection;
import administration.Quiz;

public class Tag {
	
	public static void createTags(String tags, int quizID) {
		DatabaseConnection connection = new DatabaseConnection();
		
		// Split comma-separated list (regular expression: whitespace, comma, whitespace).
		List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
		Quiz.createTags(connection, tagList, quizID);
		connection.close();
	}

}
