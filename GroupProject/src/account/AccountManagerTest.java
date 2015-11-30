//package QuizProject;
package account;


import static org.junit.Assert.*;
import database.*;

import org.junit.Test;

public class AccountManagerTest {

	@Test
	public void testRegisterUser() {
		DatabaseConnection connection =  new DatabaseConnection();
		AccountManager am = new AccountManager(connection);
		am.registerUser("eric", "1234");
		//accountExists()
		assertEquals(true, am.checkAccountExists("eric"));
		assertEquals(false, am.checkAccountExists("mukwi"));
		
		//verifyUser()
		assertEquals(true, am.verifyUser("eric", "1234"));
		assertEquals(false, am.verifyUser("eric", "eric"));
		connection.close();
	}

}
