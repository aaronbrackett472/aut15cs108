package qanda;

import java.util.List;

public class MultipleChoiceAnswer extends Answer {
	
	private int index;
	private List<Integer> indexes;
	
	//Constructor for creating questions
	public MultipleChoiceAnswer(int index) {
		super();
		this.index = index;
	}
	
	//Constructor for answering questions
	public MultipleChoiceAnswer(List<Integer> indices){
		super();
		for (Integer i : indices){
			indexes.add(i);
		}
	}
	
	public int getIndex(){
		return index;
	}
	
	public List<Integer> getIndices(){
		return indexes;
	}
}
