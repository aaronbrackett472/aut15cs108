
package account;

import database.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class UserTest {
	
	@Test
	public void addFriendTest() {
		DatabaseConnection connection =  new DatabaseConnection();
		AccountManager accounts =  new AccountManager(connection);

		//add users
		accounts.registerUser("amuj", "isokum");
		accounts.registerUser("juma", "mukoi");
		accounts.registerUser("uvyu", "");
		
		//make friends
		User user =  new User("amuj", connection);
		user.addFriend("juma");
		user.addFriend("kish");
		user.addFriend("uvyu");
		
		//test
		assertEquals(false, user.checkFriend("amuj"));
		assertEquals(true, user.checkFriend("juma"));
		assertEquals(false, user.checkFriend("kish"));
		assertEquals(true, user.checkFriend("uvyu"));
		
		//bi-directionality of friendships
		User user2 = new User("uvyu", connection);
		assertEquals(true, user2.checkFriend("amuj"));
		connection.close();
	}
	
	@Test
	public void getAllFriendsTest() {
		DatabaseConnection connection =  new DatabaseConnection();
		Set<String> amujFriends =  new HashSet<String>(Arrays.asList("juma", "uvyu"));
		User userAmuj =  new User("amuj", connection);
		assertEquals(true, userAmuj.getAllFriends().equals(amujFriends));
		connection.close();
			
	}
	
}
