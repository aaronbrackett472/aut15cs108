package qanda;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.DatabaseConnection;
import javafx.util.Pair;

public class MultipleChoice extends Question {
	
	public MultipleChoice(DatabaseConnection connection, int id) {
		super(connection, id);
	}
	
	/*@Override
	public int evaluateAnswer(String[] answers){

		int correctCount = 0;
		ChoiceSet choices = new ChoiceSet();
		choices.getChoicesByQuestionId(this.connection, this.id);
		
		for (Pair<String, Boolean> choice: choices.choicesList){
			// If choice is true
			if(choice.getValue()) {
				if(choice.getKey().trim().toLowerCase().equals(answer.trim().toLowerCase())) {
					correctCount++;
				}
			}
		}
		
		return correctCount*score;
	}*/
	
	@Override
	public int evaluateAnswer(String[] answers) {
		
		List<Answer> correctAnswers = Answer.getCorrectAnswersByQuestionId(this.connection, this.id);
		int numCorrect = 0;
		System.out.println("got here 1");
		for (int i = 0; i < answers.length; i++){
			boolean answerFound = false;
			System.out.println("got here 2");
			String answer = answers[i];
			System.out.println(answer);
			for(Answer correctAnswer: correctAnswers) {
				System.out.println("got here 3");
				if (correctAnswer.isCorrect(answer, i)){
					System.out.println("ca: " + correctAnswer);
					System.out.println("correctindex: " + correctAnswer.answerIndex);
					System.out.println("index: " + i);
					numCorrect++;
					answerFound = true;
					System.out.println("Correct!");
					break;
				}
			}
			if (!answerFound){
				numCorrect--;
			}
		}
		System.out.println("numCorrect: " + numCorrect);
		System.out.println("score for this question: " + score);
		if (numCorrect <= 0){
			return 0;
		}
		return numCorrect*score;
	}
	
	@Override
	public String getResponseInputHTML() {
		ChoiceSet cs = new ChoiceSet();
		cs.getChoicesByQuestionId(this.connection, this.id);
		String returnString = "";
		returnString+="<ul>";
		System.out.println(cs.choicesList.size());
		for(Pair<String, Boolean> choice : cs.choicesList) {
			
			returnString+="<li id=\"choice-list\"><input type=\"radio\" name=\"response-" + this.id + "\" value=\"" + choice.getKey() + "\"> " + choice.getKey() + "</li>";
		}
		returnString+="</ul>";
		return returnString;
	}
}
