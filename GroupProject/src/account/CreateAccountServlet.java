//package QuizProject;
package account;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;

import database.DBContextListener;


/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		DatabaseConnection connection = (DatabaseConnection)request.getServletContext().getAttribute(DBContextListener.DATABASE_CONTEXT_ATTRIBUTE);
		AccountManager accounts =  new AccountManager(connection);
		String username =  request.getParameter("username");
		String password = request.getParameter("password");
		if(accounts.checkAccountExists(username)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("newaccount.jsp?error=duplicateusername");
			dispatch.forward(request, response);
		} else {
			accounts.registerUser(username, password);
			response.sendRedirect("/GroupProject/index.jsp?message=accountcreated");
		}
		
		
	}

}
