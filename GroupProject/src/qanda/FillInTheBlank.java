package qanda;

public class FillInTheBlank extends Question {
	private static final String blankCharacter = "@@@@";//represents a blank in the question
	private String correctResponse;
	
	public FillInTheBlank(int score, String question, String response){
		super(score, question);
		correctResponse = response;
	}
	
	@Override
	boolean checkValidQuestion() {
		if (!super.checkValidQuestion()) return false;
		if (!question.contains("#blank"))return false;
		return true;
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
