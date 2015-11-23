package qanda;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class QuestionTest {

	@Test
	public void testSaveToDatabase() {
		
		Question.saveToDatabase(1, "Response", "Who is your daddy?", "You", "n/a");
		ResultSet questionResult = Question.getQuestionsByQuizId(1);
		try {
			questionResult.last();
			assertTrue(questionResult.getString("question").equals("Who is your daddy?"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQuestionResponse() {
		ChoiceSet c = new ChoiceSet(1);
		c.addChoices("Mark", false);
		c.addChoices("Ben", false);
		c.addChoices("Pat", true);
		c.addChoices("Patty", false);
		c.saveToDatabase();
		
		ChoiceSet b = new ChoiceSet();
		b.getChoicesByQuestionId(1);
		
		assertEquals(b.choicesList.size(), 4);
		assertTrue(b.choicesList.get(0).getKey().equals("Mark"));
		assertTrue(b.choicesList.get(1).getKey().equals("Ben"));
		assertTrue(b.choicesList.get(2).getKey().equals("Pat"));
	}

	@Test
	public void testCheckValidQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuestionIntString() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsCorrect() {
		fail("Not yet implemented");
	}

}
