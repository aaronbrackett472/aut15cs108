package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class PictureResponse extends Question {
	private String imageUrl;
	private String question;
	private String correctAnswer;

	public PictureResponse(int id){
		this.id = id;
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + questionTable + " WHERE id = '" + id + "';");
		try {
			resultSet.first();
			this.question = resultSet.getString("question");
			this.score = resultSet.getInt("score");
			this.imageUrl = resultSet.getString("imageUrl");
			this.correctAnswer = resultSet.getString("correctAnswer");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int evaluateAnswer(Answer answer) {
		StringAnswer stringAnswer = (StringAnswer) answer;
		if (stringAnswer.getResponse().equals(correctAnswer)){
			return this.score;
		}
		return 0;
	}

}
