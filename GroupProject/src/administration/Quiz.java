package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import database.DatabaseConnection;

public class Quiz {
	public final int id;
	public final String name;
	public final String author;
	public final String description;
	public final boolean randomOrder;
	public final boolean singlePage;
	public final boolean immediateCorrection;
	public final boolean practiceModeAllowed;
	public final String createdAt;
	public final ArrayList<String> tags;
	public final String tagString;
	
	public Quiz(int id, String name, String author, String description, String createdAt) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.randomOrder = false;
		this.singlePage = true;
		this.immediateCorrection = false;
		this.practiceModeAllowed = false;
		this.createdAt = createdAt;
		this.tags = new ArrayList<String>();
		this.tagString = "";
	}
	
	public Quiz(int id, String name, String author, String description, String createdAt, ArrayList<String> tags) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.randomOrder = false;
		this.singlePage = true;
		this.immediateCorrection = false;
		this.practiceModeAllowed = false;
		this.createdAt = createdAt;
		this.tags = new ArrayList<String>(tags);
		
		String tagString = tags.toString();
		this.tagString = tagString.substring(1, tagString.length() - 1);
	}
	
	public Quiz(int id, String name, String author, String description,
			boolean randomOrder, boolean singlePage, boolean immediateCorrection, boolean practiceModeAllowed,
			String createdAt, ArrayList<String> tags) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
		this.randomOrder = randomOrder;
		this.singlePage = singlePage;
		this.immediateCorrection = immediateCorrection;
		this.practiceModeAllowed = practiceModeAllowed;
		this.createdAt = createdAt;
		this.tags = new ArrayList<String>(tags);
		
		String tagString = tags.toString();
		this.tagString = tagString.substring(1, tagString.length() - 1);
	}
	
	public static void deleteQuiz(int id) {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("DELETE FROM Quizzes WHERE id = " + id + ";");
		connection.close();
	}
	
	public static void updateQuiz(int id, String name, String description,
			boolean randomOrder, boolean singlePage, boolean immediateCorrection, boolean practiceModeAllowed,
			String tags) {
		
		Quiz oldQuiz = getQuiz(id);
		
		ArrayList<Boolean> changes = new ArrayList<Boolean>();
		changes.add(!oldQuiz.name.equals(name));
		changes.add(!oldQuiz.description.equals(description));
		changes.add(oldQuiz.randomOrder != randomOrder);
		changes.add(oldQuiz.singlePage != singlePage);
		changes.add(oldQuiz.immediateCorrection != immediateCorrection);
		changes.add(oldQuiz.practiceModeAllowed != practiceModeAllowed);
		
		String columns[] = {"name", "description", "randomOrder", "singlePage", "immediateCorrection", "practiceModeAllowed"};
		
		String values[] = {"\"" + name + "\"", "\"" + description + "\"",
				String.valueOf(randomOrder ? 1 : 0),
				String.valueOf(singlePage ? 1 : 0),
				String.valueOf(immediateCorrection ? 1 : 0),
				String.valueOf(practiceModeAllowed ? 1 : 0)};
		
		DatabaseConnection connection = new DatabaseConnection();
		for (int i = 0; i < changes.size(); i++) {
			if (changes.get(i)) {
				connection.executeUpdate("UPDATE Quizzes SET " + columns[i] + " = " + values[i] + " WHERE id = " + id + ";");
			}
		}
		connection.close();
	}
	
	public static Quiz getQuiz(int id) {
		DatabaseConnection connection = new DatabaseConnection();
		ArrayList<Quiz> quizzes = getQuizzes(connection, "SELECT * FROM Quizzes WHERE id = " + id + ";");
		connection.close();
		return quizzes.get(0);
	}
	
	public static ArrayList<Quiz> getQuizzes() {
		DatabaseConnection connection = new DatabaseConnection();
		ArrayList<Quiz> quizzes = getQuizzes(connection, "SELECT * FROM Quizzes;");
		connection.close();
		return quizzes;
	}
	
	/*
	 * Search through questions by keyword
	 * @param keyword - what we're searching by
	 * @return ArrayList<Integer> - all of the quiz id's that fit that criteria
	 */
	public static ArrayList<Quiz> getQuizzesByKeyword(String keyword) {
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet;
		
		String query;
		String tagKeyword = "tag:";
		if (keyword.indexOf(tagKeyword) == 0) {
			
			// If no tag specified, treat as empty search..
			if (keyword.length() == tagKeyword.length())
				return getQuizzes();
			
			ArrayList<Integer> quizIDs = new ArrayList<Integer>();
			String tagQuery = "SELECT * FROM Tags WHERE tag =\"" + keyword.substring(tagKeyword.length()) + "\";";
			resultSet = connection.executeQuery(tagQuery);
			try {
				while (resultSet.next()) {
					quizIDs.add(resultSet.getInt("quizID"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Return empty set if no matching tag found.
			if (quizIDs.size() == 0) {
				return new ArrayList<Quiz>();
			}
			
			String quizIDString = quizIDs.toString();
			query = "SELECT * FROM Quizzes WHERE id IN (" + quizIDString.substring(1, quizIDString.length() - 1) + ");";
		} else {
			query = "SELECT * FROM Quizzes WHERE name LIKE \"%" + keyword + "%\" "
					+ "OR description LIKE \"%" + keyword + "%\" "
					+ "OR createdBy LIKE \"%" + keyword + "%\";";
		}
		
		ArrayList<Quiz> quizzes = getQuizzes(connection, query);
		connection.close();
		
		return quizzes;
	}
	
	public static ArrayList<Quiz> getQuizzesByUsername(String username) {
		DatabaseConnection connection = new DatabaseConnection();
		String query = "SELECT * FROM Quizzes WHERE createdBy =\"" + username + "\";";
		ArrayList<Quiz> quizzes = getQuizzes(connection, query);
		connection.close();
		return quizzes;
	}
	
	private static ArrayList<Quiz> getQuizzes(DatabaseConnection quizzesConnection, String query) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		DatabaseConnection tagsConnection = new DatabaseConnection();
		ResultSet resultSet = quizzesConnection.executeQuery(query);
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				quizzes.add(new Quiz(id,
						resultSet.getString("name"),
						resultSet.getString("createdBy"),
						resultSet.getString("description"),
						resultSet.getBoolean("randomOrder"),
						resultSet.getBoolean("singlePage"),
						resultSet.getBoolean("immediateCorrection"),
						resultSet.getBoolean("practiceModeAllowed"),
						resultSet.getString("createdDate"),
						getTags(tagsConnection, id)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tagsConnection.close();
		
		return quizzes;
	}
	
	private static ArrayList<String> getTags(DatabaseConnection connection, int quizID) {
		ArrayList<String> tags = new ArrayList<String>();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Tags WHERE quizID = " + quizID + ";");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				tags.add(resultSet.getString("tag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	public static void updateTags(String tags, int quizID) {
		List<String> tagList = Arrays.asList(tags.split("\\s*,\\s*"));
		DatabaseConnection connection = new DatabaseConnection();
		List<String> oldTagList = getTags(connection, quizID);
		tagList.removeAll(oldTagList);
		createTags(connection, tagList, quizID);
		connection.close();
	}
	
	public static void createTags(DatabaseConnection connection, List<String> tagList, int quizID) {
		for (String tag : tagList) {
			String query = "INSERT INTO Tags (tag, quizID) VALUES(\"" +
					tag + "\", " + quizID + ");";
			connection.executeUpdate(query);
		}
	}
}
