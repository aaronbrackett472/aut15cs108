package qanda;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultipleChoice extends Question {
	
	List<String> choices;
	Set<Integer> correctIndex;
	
	public MultipleChoice(int score, String question, List<String> choices, List<Integer> indexes) {
		super(score, question);
		this.choices = new ArrayList<String>();
		for (String choice: choices) {
			this.choices.add(choice);
		}
		correctIndex = new HashSet<Integer>();
		for (int index : indexes){
			correctIndex.add((Integer) index);
		}
	}
	
	
	public boolean isCorrect(Answer answer){
		MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
		List<Integer> indices = mca.getIndices();
		for (Integer index : indices){
			if (!correctIndex.contains(index)){
				return false;
			}
		}
		if (indices.size() == correctIndex.size()){
			return true;
		}
		return false;
	}
}
