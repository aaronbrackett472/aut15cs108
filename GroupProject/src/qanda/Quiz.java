package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import database.DatabaseConnection;

public class Quiz {
	
	private int numQuestions;
	private int id;
	private String name;
	private boolean randomOrder;
	private boolean singlePage;
	private boolean immediateCorrection;
	private boolean practiceModeAllowed;
	
	ArrayList<Question> questions;
	
	/**
	 * Creates a quiz in the database, and returns its database ID.
	 * @return quiz database ID, -1 on database error
	 */
	public static int createQuiz(String name, boolean randomOrder, boolean singlePage, boolean immediateCorrection, boolean practiceModeAllowed) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("INSERT INTO quizzes (name, randomOrder, singlePage, immediateCorrection, practiceModeAllowed) VALUES ("
				+ "\"" + name + "\", "
				+ (randomOrder ? 1 : 0) + ", " 
				+ (singlePage ? 1 : 0) + ", " 
				+ (immediateCorrection ? 1 : 0) + ", " 
				+ (practiceModeAllowed ? 1 : 0) + ");");
		int id = -1;
		ResultSet resultSet = connection.executeQuery("SELECT * FROM quizzes;");
		try {
			resultSet.last();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return id;
	}
	
	/**
	 * Constructs a Quiz object using database ID id.
	 * @param id
	 */
	public Quiz(int id) {
		this.id = id;
		this.questions = new ArrayList<Question>();
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM quizzes WHERE id LIKE " + id + ";");
		
		// Fill in state variables.
		try {
			resultSet.first();
			this.name = resultSet.getString("name");
			this.randomOrder = resultSet.getBoolean("randomOrder");
			this.singlePage = resultSet.getBoolean("singlePage");
			this.immediateCorrection = resultSet.getBoolean("immediateCorrection");
			this.practiceModeAllowed = resultSet.getBoolean("practiceModeAllowed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Populate questions vector.
		resultSet = connection.executeQuery("SELECT * FROM questions WHERE quizID LIKE " + id + ";");
		try {
			while (resultSet.next()) {
				questions.add(new Question(resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Shuffle questions if random order specified.
		if (randomOrder) Collections.shuffle(questions);
		
		connection.close();
	}
	
	public boolean useSinglePage() {
		return singlePage;
	}
	
	public boolean useImmediateCorrection() {
		return immediateCorrection;
	}
	
	public boolean practiceModeAllowed() {
		return practiceModeAllowed;
	}
	
	public int getNumQuestions() {
		return questions.size();
	}
	
	public Question getQuestionAtIndex(int index) {
		return questions.get(index);
	}
}
