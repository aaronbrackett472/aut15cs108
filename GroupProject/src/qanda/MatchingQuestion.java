package qanda;

import java.util.List;

import database.DatabaseConnection;

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
		MatchingAnswer ma = new MatchingAnswer();
		ma.getMatchingByQuestionId(connection, this.id);
		
		StringBuilder returnString = new StringBuilder();
		returnString.append("<ul>");
		for(String answer: ma.column1) {
			returnString.append("<li id=\"choice-list\">" + answer + "&nbsp;&nbsp;&nbsp;<select name=\"response-" + this.id + "\" >");
			for(String choice: ma.column2) {
				returnString.append("<option value=\"" + choice + "\">" + choice + "</option>");
			}
			returnString.append("</select></li>");
		}
		returnString.append("</ul>");
		return returnString.toString();
	}
	
}
