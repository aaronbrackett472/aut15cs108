package account;

/**
 * Holds a single achievement object
 * @author Musyoka
 */
public class AchievementItem {
	//instance variables
	private String username;
	private String achievementName;
	private String achievementDescription;
	private String imageLink;
	private String dateAcquired;
	
	
	/**
	 * Constructor
	 * @param username the user name
	 * @param achivementName name of the achievement item
	 * @param achievementDesctription descriptive details of the achievement item
	 * @param imageLink relative link of image
	 * @param dateAcquired date when achievement item was acquired
	 * 
	 */
	public AchievementItem(String username, String achievementName, String achievementDescription,
			String imageLink, String dateAcquired)
	{
		this.username = username;
		this.achievementName = achievementName;
		this.achievementDescription =  achievementDescription;
		this.imageLink = imageLink;
		this.dateAcquired = dateAcquired;
	}
	
	/**
	 * Returns username of the winner of achievement item
	 */
	public String getUserName() {
		return username;
	}
	
	/**
	 * Returns the name of the achievement item
	 * 
	 */
	public String getAchievementName() {
		return achievementName;
	}
	
	/**
	 * Returns the description of the achievement item
	 * 
	 */
	public String getDescription() {
		return achievementDescription;
	}
	
	/**
	 * Returns the relative image link of the achievement item
	 */
	public String getImageLink() {
		return imageLink;
	}
	
	/**
	 * Returns the date and time the achievement was acquired
	 */
	public String getDateAcquired() {
		return dateAcquired;
	}

}

