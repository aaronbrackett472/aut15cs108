package qanda;


public class QuestionResponse extends Question {
	
	private String correctResponse;
	
	public QuestionResponse(int score, String question, String answer) {
		super(score, question);
		correctResponse = answer;
	}
	
	@Override
	boolean isCorrect(Answer answer) {
		StringAnswer stringAnswer = (StringAnswer) answer;
		if (stringAnswer.getResponse() == correctResponse){
			return true;
		}
		return false;
	}
}
