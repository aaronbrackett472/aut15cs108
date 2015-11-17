package qanda;


import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question {
	
	List<String> choices;
	int correctIndex;
	
	public MultipleChoice(int score, String question, List<String> choices, List<Integer> index) {
		super(score, question);
		this.choices = new ArrayList<String>();
		for (String choice: choices) {
			this.choices.add(choice);
		}
		correctIndex = index;
	}
	
	public void saveToDatabase() {
		this.establishDatabaseConnection();
		
		// This needs to be finalized
		String queryString = "INSERT INTO "+ this.questionTable + " (questionText, score) " +
				"VALUES('" + this.question + "', '" + this.score + "')" ;
		
		this.executeSQLQuery(queryString);
		
		for (String choice: choices) {
			String queryString2 = "INSERT INTO "+ this.questionTable + " (questionText, score) " +
					"VALUES('" + this.question + "', '" + this.score + "')" ;
			this.executeSQLQuery(queryString2);
		}
		
	}
	
	public boolean isCorrect(Answer answer){
		MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
		if (mca.getIndex()==correctIndex){
			return true;
		}
		return false;
	}

}
