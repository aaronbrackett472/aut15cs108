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
	
	List<String> choices;
	
	public MultipleChoice(int id) {
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE id = '" + id + "';");
		try {
			resultSet.first();
			this.question = resultSet.getString("question");
			this.score = resultSet.getInt("score");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int evaluateAnswer(Answer answer){
		
		int correctCount = 0;
		ChoiceSet choices = new ChoiceSet();
		choices.getChoicesByQuestionId(this.id);
		
		for (Pair<String, Boolean> choice: choices.choicesList){
			if(choice.getValue()) {
				if(choice.getKey().equals(answer)) {
					correctCount++;
				}
			}
		}
		
		return correctCount;
	}
}
