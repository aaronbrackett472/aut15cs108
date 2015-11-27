package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.DatabaseConnection;

public class Quiz {
	
	private int numQuestions;
	private int id;
	private String name;
	private boolean random;
	private boolean singlePage;
	private boolean immediateCorrection;
	
	ArrayList<Question> questions;
	
	/**
	 * Creates a quiz in the database, and returns its database ID.
	 * 
	 * @param name
	 * @param random
	 * @param singlePage
	 * @param immediateCorrection
	 * @return
	 */
	public static int create(String name, boolean random, boolean singlePage, boolean immediateCorrection) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("INSERT INTO quizzes (name, randomorder, singlepage, immediatecorrection) VALUES('" +
				name + "', '" + (random ? 1 : 0) + "', '" + (singlePage ? 1 : 0) + "', '" + (immediateCorrection ? 1 : 0) + "');");
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
	 * Constructs a QUiz object using database ID id.
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
			this.random = resultSet.getBoolean("randomorder");
			this.singlePage = resultSet.getBoolean("singlepage");
			this.immediateCorrection = resultSet.getBoolean("immediatecorrection");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Populate questions vector.
		resultSet = connection.executeQuery("SELECT * FROM " + Question.questionTable + " WHERE quizID LIKE " + id + ";");
		try {
			while (resultSet.next()) {
				questions.add(new Question(resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Shuffle questions if random order specified.
		if (random) Collections.shuffle(questions);
		
		connection.close();
	}
	
	public boolean useSinglePage() {
		return singlePage;
	}
	
	public boolean useImmediateCorrection() {
		return immediateCorrection;
	}
	
	public int getNumQuestions() {
		return questions.size();
	}
	
	public Question getQuestionAtIndex(int index) {
		return questions.get(index);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	/*
	 * New method - return x most recent quizzes
	 * Used by homepage and the browse quiz page (w/ different limit)
	 * @param limit - how many quizzes should be returned
	 * @return List<Quiz> - a list of Quiz objects
	 */
	public static List<Quiz> getRecentQuizzes(int limit) {
		DatabaseConnection connection = new DatabaseConnection();
		
		ResultSet resultSet = connection.executeQuery("SELECT * FROM quizzes ORDER BY id DESC LIMIT "+ Integer.toString(limit) + ";");
		List<Quiz> quizzes = new ArrayList<Quiz>(limit);
		try {
			while (resultSet.next()) {
				quizzes.add(new Quiz(resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return quizzes;
	}
}
