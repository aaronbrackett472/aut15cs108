package qanda;

import static org.junit.Assert.*;

import org.junit.Test;

import database.DatabaseConnection;

public class QuizTest {

	@Test
	public void testCreate() {
		DatabaseConnection connection = new DatabaseConnection();
		Quiz.createQuiz(connection, "awesome unittest quiz 1", "no desc bro", false, false, false, false, "pat");
		//fail("Not yet implemented");
	}

//	@Test
//	public void testQuiz() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testUseSinglePage() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUseImmediateCorrection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetNumQuestions() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetQuestionAtIndex() {
//		fail("Not yet implemented");
//	}

}
