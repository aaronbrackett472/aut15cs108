package hw6;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question {
	
	List<String> choices;
	
	public MultipleChoice(int score, String question, List<String> choices) {
		super(score, question);
		this.choices = new ArrayList<String>();
		for (String choice: choices) {
			this.choices.add(choice);
		}
	}

}
