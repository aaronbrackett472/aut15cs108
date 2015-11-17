package QuizProject;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountManagerTest {

	@Test
	public void testRegisterUser() {
		AccountManager am = new AccountManager();
		am.registerUser("eric", "1234");
		//accountExists()
		assertEquals(true, am.checkAccountExists("eric"));
		assertEquals(false, am.checkAccountExists("mukwi"));
		
		//verifyUser()
		assertEquals(true, am.verifyUser("eric", "1234"));
		assertEquals(false, am.verifyUser("eric", "eric"));
	}

}
