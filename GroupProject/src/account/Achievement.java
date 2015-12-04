package account;

import database.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import qanda.Quiz;

/**
 * Avails access and manipulation of achievement items
 * @author Musyoka
 */
public class Achievement {
	//Instance variables
	DatabaseConnection connection;
	
	//Constants
	public static String ACHIEVEMENT_TABLE = "Achievement";
	
	//To be used to access achievement names and imageLinks 
	private static int AMATEUR_AUTHOR = 0;
	private static int PROFILIC_AUTHOR = 1;
	private static int PRODIGIOUS_AUTHOR = 2;
	private static int QUIZ_MACHINE = 3;
	private static int GREATEST = 4;
	private static int PRACTICE = 5;
	
	private static String[] ACHIEVEMENT_NAMES= new String[]{
			"Amateur Author",
			"Profilic Author",
			"Prodigious Author",
			"Quiz Machine",
			"I am the Greatest",
			"Practice Makes Perfect"
	};
	
	private static String[] ACHIEVEMENT_DESCRIPTION = new String[] {
			"The user created a quiz.",
			"The user created five quizzes.",
			"The user created ten quizzes.",
			"The user took ten quizzes.",
			"The user had the highest score on a quiz.",
			"The user took a quiz in practice mode"
	};
	
	
	private static String[] IMAGE_LINKS = new String[] {
			"imageLinks/amateur",
			"imageLinks/profilic",
			"imageLinks/prodigious",
			"imageLinks/quiz-machine",
			"imageLinks/greatest",
			"imageLinks/practice"			
	};
	
			
	
	
	
	/**
	 * Constructor
	 * @param connection open database connection
	 */
	public Achievement(DatabaseConnection connection) {
		this.connection =  connection;
	}
	
	/** 
	 * Adds an AchievementItem into the database
	 * @param  item the achievement item
	 */
	public void storeAchievementItem(AchievementItem item) {
		String querry;
		if(item.getDateAcquired().isEmpty()) {
			//default to now
			querry = "INSERT INTO "+ ACHIEVEMENT_TABLE + " (username, achievementName, achievementDescription, image) " + 
					 "VALUES('" + item.getUserName() + "', '" + item.getAchievementName() + "', '" + item.getDescription() + "', '"+ item.getImageLink()+ "')" ;		
		} else {
			querry = "INSERT INTO "+ ACHIEVEMENT_TABLE + " (username, achievementName, achievementDescription, image, dateAcquired) " + 
					 "VALUES('" + item.getUserName() + "', '" + item.getAchievementName() + "', '" + item.getDescription() + "', '"+ item.getImageLink()+ "', '" + item.getDateAcquired()+ "')" ;

		}
		connection.executeUpdate(querry);
	}
	
	/**
	 * Gets all achievements by in the database by all users
	 * @return achievements all achievements
	 */
	public ArrayList<AchievementItem> getAllAchievements() {
		String querry = "SELECT * FROM " + ACHIEVEMENT_TABLE;
		return getAchievementsByQuery(querry);
	}
	
	/**
	 * Get achievementItems by username
	 * @param username the user's name
	 * @return achievements all achievements by the user
	 *
	 */
	public ArrayList<AchievementItem> getAchievementByUser(String user) {
		String querry = "SELECT * FROM " + ACHIEVEMENT_TABLE + " WHERE username='" + user + "'";
		return getAchievementsByQuery(querry);
	}
	
	/**
	 * A helper method, gets achievements given a query string
	 * @param querry query string
	 * @return achievements achievement items according to the query
	 * 
	 */
	private ArrayList<AchievementItem> getAchievementsByQuery(String querry) {
		ArrayList<AchievementItem> achievements =  new ArrayList<AchievementItem>();
		try{
			ResultSet rs = connection.executeQuery(querry);	
			while(rs.next()) {
				String username = rs.getString(1);
				String achievementName = rs.getString(2);
				String achievementDescription = rs.getString(3);
				String imageLink =  rs.getString(4);
				String dateAcquired =  rs.getString(5);
				AchievementItem item = new AchievementItem(username, achievementName, achievementDescription, imageLink, dateAcquired);
				achievements.add(item);		
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	
		return achievements;
	}
	
	/**
	 * Given a username, saves all the achievements that the user has achieved but have not
	 * yet been recorded.
	 * The method should be called to reflect achievement changes in the database after
	 * quiz(s) it taken or created.
	 * @param username the user
	 * @param quizId recently created or taken quiz 
	 * @param practiceMode true if the quiz was taken in practice mode
	 * @return unlocked the unlocked items
	 */
	public ArrayList<AchievementItem> unlockAchievementsIfAny(String username, int quizId, boolean practiceMode) {
		ArrayList<AchievementItem> unlocked =  new ArrayList<AchievementItem>();
		History history =  new History(connection);
		ArrayList<HistoryItem> userHistory = history.getHistoryByUsername(username);
		
		//Quiz taking type of achievements
		int quizzesTaken = userHistory.size();
		if(quizzesTaken >= 10) {
			saveRecentlyUnlocked(username,QUIZ_MACHINE, unlocked);
		}
		if(hasHighestQuizScore(username, quizId)) {
			saveRecentlyUnlocked(username, GREATEST, unlocked);
		}
		if(practiceMode) {
			saveRecentlyUnlocked(username, PRACTICE, unlocked);
		}
		
		//Quiz Creation part
		int quizzesByUser = Quiz.countQuizzesByUser(username, connection);	
		if(quizzesByUser >= 1 && quizzesByUser < 5) {
			saveRecentlyUnlocked(username, AMATEUR_AUTHOR, unlocked) ;
		} else if(quizzesByUser >= 5 && quizzesByUser < 10 ) {
			//both amateur and profilic
			saveRecentlyUnlocked(username, AMATEUR_AUTHOR, unlocked) ;
			saveRecentlyUnlocked(username, PROFILIC_AUTHOR, unlocked) ;
		} else if(quizzesByUser >= 10) {
			saveRecentlyUnlocked(username, AMATEUR_AUTHOR, unlocked) ;
			saveRecentlyUnlocked(username, PROFILIC_AUTHOR, unlocked) ;
			saveRecentlyUnlocked(username, PRODIGIOUS_AUTHOR, unlocked) ;		
		}
		
		
		return unlocked;
	}
	/** 
	 * Checks whether the given username has the highest score on the
	 * given quiz id
	 * @return true if username has highest score on quizid
	 */
	private boolean hasHighestQuizScore(String username, int quizId) {
		History history = new History(connection);
		ArrayList<HistoryItem> quizIdHistory = history.getHistoryByQuizId("" + quizId);
		int highestScoreGenerally = 0;
		int highestScoreUser = 0;
		for(HistoryItem item: quizIdHistory) {
			if(item.getScore() > highestScoreGenerally) {
				highestScoreGenerally = item.getScore();
			}
			if(username.equals(item.getUserName())) {
				if(item.getScore() > highestScoreUser){
					highestScoreUser = item.getScore();
				}
			}
		}
		
		if(highestScoreGenerally == highestScoreUser) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Saves an achievement If it has not been saved already in the database
	 * Add the achievement to the passed ArrayList if the achievement has been added for the 
	 * user for the first time
	 * @param username the user name
	 * @param achievementIndex the index of the achievement in ACHIEVEMENT_NAMES
	 * @param unlocked the ArrayList where a copy of newly unlocked AchievementItem is saved
	 */
	public void saveRecentlyUnlocked(String username, int achievementIndex, ArrayList<AchievementItem> unlocked) {
		boolean alreadyUnlocked = false;
		ArrayList<AchievementItem> userAchievements = getAchievementByUser(username); 
		for(AchievementItem item: userAchievements) {
			if(ACHIEVEMENT_NAMES[achievementIndex].equals(item.getAchievementName())) {
				alreadyUnlocked =  true;
				break;
			}
		}
		if(!alreadyUnlocked) {
			AchievementItem item =  new AchievementItem(
					username,
					ACHIEVEMENT_NAMES[achievementIndex],
					ACHIEVEMENT_DESCRIPTION[achievementIndex],
					IMAGE_LINKS[achievementIndex],
					"") ;
			storeAchievementItem(item);
			unlocked.add(item);
		}
		
	}
	
	
	
}
