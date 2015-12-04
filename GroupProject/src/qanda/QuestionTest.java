package qanda;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import database.DatabaseConnection;

public class QuestionTest {

	@Test
	public void testSaveToDatabase() {
		
		DatabaseConnection connection = new DatabaseConnection();
		
		Question.saveToDatabase(connection, 1, "Response", 1, "Who is your daddy?", "n/a");
		ResultSet questionResult = Question.getQuestionsByQuizId(connection, 1);
		try {
			questionResult.last();
			assertTrue(questionResult.getString("question").equals("Who is your daddy?"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connection.close();
		
	}
	
	@Test
	public void testQuestionResponse() {
		
		DatabaseConnection connection = new DatabaseConnection();
		
		ChoiceSet c = new ChoiceSet(1);
		c.addChoices("Mark", false);
		c.addChoices("Ben", false);
		c.addChoices("Pat", true);
		c.addChoices("Patty", false);
		c.saveToDatabase(connection);
		
		ChoiceSet b = new ChoiceSet();
		b.getChoicesByQuestionId(connection, 1);
		
		assertTrue(b.choicesList.get(0).getKey().equals("Mark"));
		assertTrue(b.choicesList.get(1).getKey().equals("Ben"));
		assertTrue(b.choicesList.get(2).getKey().equals("Pat"));
		
		connection.close();
	}

//	@Test
//	public void testCheckValidQuestion() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQuestion() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQuestionIntString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsCorrect() {
//		fail("Not yet implemented");
//	}

}
