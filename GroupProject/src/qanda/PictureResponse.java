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
	
	public void saveToDatabase() {
		this.establishDatabaseConnection();
		
		// This needs to be finalized
		String queryString = "INSERT INTO "+ this.questionTable + " (questionText, imageUrl, score) " +
				"VALUES('" + this.question + "', '" + this.imageUrl + "', '" + this.score + "')" ;
		
		this.executeSQLQuery(queryString);
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
