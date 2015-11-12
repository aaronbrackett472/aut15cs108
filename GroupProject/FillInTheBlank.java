package hw6;

public class FillInTheBlank extends Question {
	
	@Override
	boolean checkValidQuestion() {
		if (!super.checkValidQuestion()) return false;
		if (!question.contains("#blank"))return false;
		return true;
	}

}
