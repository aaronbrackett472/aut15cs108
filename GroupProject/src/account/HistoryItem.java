//package QuizProject;
package account;

/**
 * Holds a single history record
 */
public class HistoryItem {
	//private variables
	private String username;
	private int score;
	private int maxScore;
	private int quizId;
	private String time;
	
	/**
	 * Constructor
	 */
	public HistoryItem(String username, int score, int maxScore, int quizId, String time) {
		this.username = username;
		this.score = score;
		this.maxScore = maxScore;
		this.quizId  = quizId;
		this.time = time;
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
	public String getTime() {
		return time;
	}
	
}
