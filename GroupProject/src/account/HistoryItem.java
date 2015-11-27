//package QuizProject;
package account;

/**
 * Holds a single history record
 */
public class HistoryItem {
	//private variables
	private int score;
	private String time;
	
	/**
	 * Constructor
	 */
	public HistoryItem(int score, String time) {
		this.score = score;
		this.time = time;
	}
	
	/**
	 * Returns the score of the history item
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the time the score of the history item was achieved
	 */
	public String getTime() {
		return time;
	}
	
}
