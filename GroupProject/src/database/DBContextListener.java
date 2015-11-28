package database;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class DBContextListener
 *
 */
@WebListener
public class DBContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DBContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ServletContext context = arg0.getServletContext();
    	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
    	connection.close();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ServletContext context = arg0.getServletContext();
    	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
    	if(connection == null) {
    		connection = new DatabaseConnection();
    		context.setAttribute("databaseconnection", connection);
    	}
    }
	
}
