package hw6;

public class Question {
	int score;
	String question;
	
	boolean checkValidQuestion() {
		if (score < 0)return false;
		return true;
	}
	
	// Default constructor - why?
	public Question(){
		score = 1;
		question = "";
	}
	
	public Question(int score, String question) {
		this.score = score;
		this.question = question;
	}
	
}
