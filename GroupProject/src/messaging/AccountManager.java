package messaging;

public class AccountManager {

	private User user;
	private String name;
	public AccountManager() {
		this.user = user;
		this.name = name;
		
	}
	
	public User getUser(String name) {
		if (name.equals("username")) {
			return user;
		}
		return null;
		
	}
	


	public void removeFriends(String user, String friend) {
		// TODO Auto-generated method stub	
	}

	public void addFriend(String friend) {
		// TODO Auto-generated method stub
		
	}

}
