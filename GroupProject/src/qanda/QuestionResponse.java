package qanda;



public class QuestionResponse extends Question {
		
	private String correctResponse;
	
	public void saveToDatabase() {
		this.establishDatabaseConnection();
		
		// This needs to be finalized
		String queryString = "INSERT INTO "+ this.questionTable + " (questionText, score, isMultipleChoice) " +
				"VALUES('" + this.question + "', '" + this.score + "', 0)" ;
		
		this.executeSQLQuery(queryString);
	}
	
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
