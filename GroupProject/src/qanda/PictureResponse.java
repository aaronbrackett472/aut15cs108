package qanda;


public class PictureResponse extends Question {
	private String imageUrl;
	private String question;
	private String correctResponse;

	public PictureResponse(String url, String question, String answer){
		imageUrl = url;
		this.question = question;
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
