//package QuizProject;
package account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		HttpSession session = request.getSession();
		AccountManager accounts = (AccountManager)session.getAttribute(UserSessionListener.ACCOUNTS_CONTEXT_ATTRIBUTE);
		if (accounts == null) {
			accounts = new AccountManager();
			session.setAttribute(UserSessionListener.ACCOUNTS_CONTEXT_ATTRIBUTE, accounts);
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ServletContext context = request.getServletContext();
    	//DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
		
		if(accounts.verifyUser(username, password)) {
			session.setAttribute("loggedin_user", username);
			response.sendRedirect("/GroupProject/");
		} else {
			System.out.println("invalid user or password");
			response.sendRedirect("/GroupProject/?message=badlogin");
		}
	}

}
