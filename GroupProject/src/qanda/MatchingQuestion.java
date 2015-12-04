package qanda;

import java.util.List;

import database.DatabaseConnection;
import javafx.util.Pair;

public class MatchingQuestion extends Question {
	
	public MatchingQuestion(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	

	@Override
	public int evaluateAnswer(String[] answers){

		List<Answer> correctAnswers = Answer.getCorrectAnswersByQuestionId(this.connection, this.id);
		int numCorrect = 0;
		for (int i = 0; i < answers.length; i++){
			String answer = answers[i];
			System.out.println(answer);
			for(Answer correctAnswer: correctAnswers) {
				if (correctAnswer.isCorrect(answer, i)){
					numCorrect++;
					System.out.println("Correct!");
					break;
				}
			}
		}
		System.out.println("numCorrect: " + numCorrect);
		System.out.println("score for this question: " + score);
		return numCorrect*score;
		
	}
	
	
	
	
	@Override
	public String getResponseInputHTML() {
		ChoiceSet cs = new ChoiceSet();
		cs.getChoicesByQuestionId(this.connection, this.id);
		StringBuilder returnString = new StringBuilder();
		returnString.append("<ul>");
		for(Pair<String, Boolean> choice : cs.choicesList) {
			
			returnString.append("<li id=\"choice-list\"><input type=\"radio\" name=\"response-" + this.id + "\" value=\"" + choice.getKey() + "\"> " + choice.getKey() + "</li>");
		}
		returnString.append("</ul>");
		return returnString.toString();
	}
	
}
