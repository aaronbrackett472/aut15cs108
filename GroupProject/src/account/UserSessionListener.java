//package QuizProject;
package account;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class UserSessionListener
 *
 */
@WebListener
public class UserSessionListener implements HttpSessionListener {
	//Constants
	public static String ACCOUNTS_CONTEXT_ATTRIBUTE =  "accounts";
	
	//instance variables
	private AccountManager accounts;
	
    /**
     * Default constructor. 
     */
    public UserSessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  {
       accounts = new AccountManager();
       HttpSession session =  arg0.getSession();
       session.setAttribute(ACCOUNTS_CONTEXT_ATTRIBUTE, accounts);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
        accounts.closeConnections();
    }
	
}
