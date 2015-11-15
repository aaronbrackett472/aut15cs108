package qanda;

public class MultipleChoiceAnswer extends Answer {
	
	private int index;
	
	public MultipleChoiceAnswer(int index) {
		super();
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	
}
