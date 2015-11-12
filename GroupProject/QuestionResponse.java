package hw6;

public class QuestionResponse extends Question {
	
	public QuestionResponse(int score, String question) {
		super(score, question);
	}
	
	boolean isCorrect(Answer answer) {
		return true;
	}
}
