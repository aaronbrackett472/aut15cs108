//package QuizProject;
package account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBContextListener;
import database.DatabaseConnection;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnection connection = (DatabaseConnection)request.getServletContext().getAttribute(DBContextListener.DATABASE_CONTEXT_ATTRIBUTE);	
		AccountManager accounts =  new AccountManager(connection);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(accounts.verifyUser(username, password)) {
//			RequestDispatcher dispatch =  request.getRequestDispatcher("create-account-welcome.jsp");
			RequestDispatcher dispatch =  request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		} else {
			RequestDispatcher  dispatch =  request.getRequestDispatcher("try-again-login.html");
			dispatch.forward(request, response);
		}
	}

}
