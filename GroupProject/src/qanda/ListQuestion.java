package qanda;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.DatabaseConnection;

public class ListQuestion extends Question {
	private int count;
	
	public ListQuestion(DatabaseConnection connection, int id) {
		super(connection, id);
		
		count = 0;
		Set<Integer> usedIndices = new HashSet<Integer>();
		List<Answer> answerList = Answer.getCorrectAnswersByQuestionId(connection, id);
		for (Answer a : answerList){
			if (!usedIndices.contains(a.answerIndex)){
				count++;
				usedIndices.add(a.answerIndex);
			}
		}
	}
	
	@Override
	public String getResponseInputHTML(){
		String response = "";
		
		for (int i = 0; i < count; i++){
			response+=super.getResponseInputHTML();
			response+="<br>";
		}
		return response; 
	}
	
	@Override
	public int evaluateAnswer(String[] answers) {
		
		List<Answer> correctAnswers = Answer.getAnswersByQuestionId(this.connection, this.id);
		System.out.println("numAnswers: " + correctAnswers.size());
		int numCorrect = 0;
		for (int i = 0; i < answers.length; i++){
			String answer = answers[i];
			System.out.println(answer);
			for(Answer correctAnswer: correctAnswers) {
				System.out.println("got here");
				if (imageUrl == "yes"){
					if (correctAnswer.isCorrect(answer, i)){
						numCorrect++;
						System.out.println("Correct!");
						break;
					}
				}
				else{
					if (correctAnswer.isCorrectString(answer)){
						numCorrect++;
						System.out.println("Correct!");
						break;
					}
				}
			}
		}
		System.out.println("numCorrect: " + numCorrect);
		System.out.println("score for this question: " + score);
		return numCorrect*score;
	}
	
}
