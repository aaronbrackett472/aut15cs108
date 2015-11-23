package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class FillInTheBlank extends Question {
	//represents a blank in the question
	private static final String blankCharacter = "@@@@";
	private String correctAnswer;
	
	public FillInTheBlank(int id) throws SQLException{
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE id = '" + id + "';");
		try {
			// If no question found with this id
			if(resultSet.first() == false){
				this.id = -1;
				return;
			}
			this.question = resultSet.getString("question");
			this.score = resultSet.getInt("score");
			this.correctAnswer = resultSet.getString("correctAnswer");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	boolean checkValidQuestion() {
		if (!super.checkValidQuestion()) return false;
		if (!question.contains("#blank"))return false;
		return true;
	}

	@Override
	public int evaluateAnswer(Answer answer) {
		StringAnswer stringAnswer = (StringAnswer) answer;
		if (stringAnswer.getResponse() == correctAnswer){
			return this.score;
		}
		return 0;
	}

}
