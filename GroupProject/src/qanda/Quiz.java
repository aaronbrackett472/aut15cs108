package qanda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import database.DatabaseConnection;

public class Quiz {
	
	private static String quizzesTable = "Quizzes";
	
	private int numQuestions;
	private int id;
	private String name;
	private String description;
	private boolean random;
	private boolean singlePage;
	private boolean immediateCorrection;
	private int taken_count;
	private String createdBy;
	private Date createdDate;
	
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
	public static int createQuiz(DatabaseConnection connection, String name, boolean randomOrder, boolean singlePage, boolean immediateCorrection, boolean practiceModeAllowed, String createdBy) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestring = format.format(calendar.getTime());
		
		connection.executeUpdate("INSERT INTO " + quizzesTable + " (name, randomorder, singlepage, immediatecorrection, practiceModeAllowed, takenCounter, createdBy, createdDate) VALUES('" +
				name + "', '"
				+ (randomOrder ? 1 : 0) + "', '"
				+ (singlePage ? 1 : 0) + "', '"
				+ (immediateCorrection ? 1 : 0) + "', '"
				+ (practiceModeAllowed ? 1: 0)  + "', '"
				+ "0" + "', '"
				+ createdBy + "', '"
				+ datestring + "');");
		
		int id = -1;
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + quizzesTable + ";");
		try {
			resultSet.last();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//connection.close();
		return id;
	}
	
	/**
	 * Constructs a Quiz object using database ID id.
	 * @param id
	 */
	public Quiz(DatabaseConnection connection, int id) {
		this.id = id;
		this.questions = new ArrayList<Question>();
		
		DatabaseConnection connection2 = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM " + quizzesTable + " WHERE id LIKE " + id + ";");
		
		// Fill in state variables.
		try {
			resultSet.first();
			this.name = resultSet.getString("name");
			this.description = resultSet.getString("description");
			this.random = resultSet.getBoolean("randomorder");
			this.singlePage = resultSet.getBoolean("singlepage");
			this.immediateCorrection = resultSet.getBoolean("immediatecorrection");
			this.createdBy = resultSet.getString("createdBy");
			this.taken_count = resultSet.getInt("takenCounter");
			this.createdDate = resultSet.getDate("createdDate");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Populate questions vector.
		resultSet = connection.executeQuery("SELECT * FROM " + Question.questionTable + " WHERE quizID LIKE " + id + ";");
		try {
			while (resultSet.next()) {
				questions.add(new Question(connection2, resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Shuffle questions if random order specified.
		if (random) Collections.shuffle(questions);
		
		connection2.close();
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

	public String getDescription() {
		return this.description;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getCreationDate() {
		return this.createdDate.toString();
	}
	
	public int getTakenCount() {
		return this.taken_count;
	}
	
	public String getCreator() {
		return this.createdBy;
	}
	
	/*
	 * New method - return x most recent quizzes
	 * Used by the homepage and the browse quiz page (w/ different limit)
	 * @param limit - how many quizzes should be returned
	 * @return List<Quiz> - a list of Quiz objects
	 */
	public static List<Quiz> getRecentQuizzes(DatabaseConnection connection, int limit) {
		
		String queryString = "SELECT * FROM " + quizzesTable + " ORDER BY id DESC LIMIT "+ Integer.toString(limit) + ";";
		DatabaseConnection connection2 = new DatabaseConnection();
		
		ResultSet resultSet = connection.executeQuery(queryString);
		List<Quiz> quizzes = new ArrayList<Quiz>(limit);
		try {
			while (resultSet.next()) {
				quizzes.add(new Quiz(connection2, resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection2.close();
		return quizzes;
	}
}
