//package QuizProject;
package account;

import java.util.Date;

/**
 * Holds a single history record
 */
public class HistoryItem {
	//private variables
	private String username;
	private int score;
	private int maxScore;
	private int quizId;
	private int minuteTaken;
	private Date dateTaken;
	
	/**
	 * Constructor
	 */
	public HistoryItem(String username, int score, int maxScore, int quizId, int minuteTaken, Date dateTaken) {
		this.username = username;
		this.score = score;
		this.maxScore = maxScore;
		this.quizId  = quizId;
		this.minuteTaken = minuteTaken;
		this.dateTaken = dateTaken;
	}
	
	/**
	 * Returns the username of the history item
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * Returns the score of the history item
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the maximum score attainable for the quiz taken
	 */
	public int getMaxScore() {
		return maxScore;
	}
	
	/**
	 * Returns the quiz ID of the history item
	 */
	public int getQuizId() {
		return quizId;
	}
	
	/**
	 * Returns the time the score of the history item was achieved
	 */
	public Date getDateTaken() {
		return dateTaken;
	}
	
	public int getMinuteTaken() {
		return minuteTaken;
	}
	
}
