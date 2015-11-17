package QuizProject;

/*
 * Holds a single achievement object
 */
public class Achievement {
	//instance variables
	private String name;
	private String timeAcquired;
	
	
	/**
	 * Constructor
	 */
	public Achievement(String name, String timeAcquired) {
		this.name = name;
		this.timeAcquired = timeAcquired;
	}
	
	/**
	 * Returns name of the achivement
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the time the achievement was acquired
	 */
	public String getTimeAcquired() {
		return timeAcquired;
	}

}
