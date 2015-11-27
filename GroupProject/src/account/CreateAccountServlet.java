//package QuizProject;
package account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountManager accounts =  (AccountManager)request.getSession().getAttribute(UserSessionListener.ACCOUNTS_CONTEXT_ATTRIBUTE);
		String username =  request.getParameter("username");
		String password = request.getParameter("password");
		if(accounts.checkAccountExists("username")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create-account-fail.jsp");
			dispatch.forward(request, response);
		} else {
			accounts.registerUser(username, password);
			RequestDispatcher dispatch = request.getRequestDispatcher("create-account-welcome.jsp");
			dispatch.forward(request, response);
		}
		
		
	}

}
