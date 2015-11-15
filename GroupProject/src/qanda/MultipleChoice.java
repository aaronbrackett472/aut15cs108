package qanda;


import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question {
	
	List<String> choices;
	int correctIndex;
	
	public MultipleChoice(int score, String question, List<String> choices, int index) {
		super(score, question);
		this.choices = new ArrayList<String>();
		for (String choice: choices) {
			this.choices.add(choice);
		}
		correctIndex = index;
	}
	
	public boolean isCorrect(Answer answer){
		MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
		if (mca.getIndex()==correctIndex){
			return true;
		}
		return false;
	}

}
