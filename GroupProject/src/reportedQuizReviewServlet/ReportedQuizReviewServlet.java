package reportedQuizReviewServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;
import utilities.Utilities;

/**
 * Servlet implementation class ReportedQuizReviewServlet
 */
@WebServlet("/ReportedQuizReviewServlet")
public class ReportedQuizReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportedQuizReviewServlet() {
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
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("UPDATE QuizReports SET resolved = 1 WHERE id = "
				+ request.getParameter("id") + ";");
		if (request.getParameter("suspendAuthor") != null) {
			int days = Integer.parseInt(request.getParameter("suspension"));
			connection.executeUpdate("UPDATE Accounts SET suspended = 1 WHERE username = \""
					+ request.getParameter("author") + "\";");
			connection.executeUpdate("UPDATE Accounts SET suspensionEnd = \"" + Utilities.getFutureDateInDays(days)
					+ "\" WHERE username = \"" + request.getParameter("author") + "\";");
		}
		connection.close();
		
		RequestDispatcher dispatch = request.getRequestDispatcher("ReportedQuizListServlet");
		dispatch.forward(request, response);
	}

}
