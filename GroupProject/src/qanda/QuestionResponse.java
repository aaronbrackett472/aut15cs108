package qanda;



public class QuestionResponse extends Question {
		
	public String correctResponse;
	
	public QuestionResponse(int score, String question, String correctResponse) {
		this.score = score;
		this.question = question;
		this.correctResponse = correctResponse;
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
