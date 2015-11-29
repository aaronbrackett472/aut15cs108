package messaging;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import database.DatabaseConnection;

/**
 * Application Lifecycle Listener implementation class WebsiteListener
 *
 */
@WebListener
public class WebsiteListener implements ServletContextListener {

	MessageManager messageManager;
	DatabaseConnection connection;
	
    /**
     * Default constructor. 
     */
    public WebsiteListener() {
    	connection = new DatabaseConnection();
    	messageManager = new MessageManager();
    }
    
 
	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext servContext = event.getServletContext();
    	servContext.setAttribute("messageManager", messageManager);
         // TODO Auto-generated method stub
    }
	
}
