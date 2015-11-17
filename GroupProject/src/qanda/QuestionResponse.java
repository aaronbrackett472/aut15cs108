package qanda;



public class QuestionResponse extends Question {
		
	private String correctResponse;
	

	
	@Override
	boolean isCorrect(Answer answer) {
		StringAnswer stringAnswer = (StringAnswer) answer;
		if (stringAnswer.getResponse() == correctResponse){
			return true;
		}
		return false;
	}
}
