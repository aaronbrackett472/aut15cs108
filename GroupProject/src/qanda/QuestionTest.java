package qanda;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuestionTest {

	@Test
	public void testSaveToDatabase() {
		QuestionResponse q1 = new QuestionResponse(1, "Who is your daddy?", "You");
		Question.saveToDatabase(0, "Response", q1.question, q1.correctResponse, 1, "n/a");
		//String questionString = Question.getQuestionByQuizId(0);
		//assertTrue(questionString.equals("Who is your daddy?"));
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
