package reportQuizServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.Quiz;
import database.DatabaseConnection;

/**
 * Servlet implementation class ReportQuizServlet
 */
@WebServlet("/ReportQuizServlet")
public class ReportQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Quizzes WHERE id = " + id + ";");
		try {
			resultSet.first();
			request.setAttribute("id", id);
			request.setAttribute("author", resultSet.getString("createdBy"));
			request.setAttribute("name", resultSet.getString("name"));
			request.setAttribute("description", resultSet.getString("description"));
			request.setAttribute("createdAt", resultSet.getString("createdDate"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		
		RequestDispatcher dispatch = request.getRequestDispatcher("report-quiz.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnection connection = new DatabaseConnection();
		connection.executeUpdate("INSERT INTO QuizReports (quizID, quizName, author, reporter, comment, resolved) VALUES("
				+ request.getParameter("quizID") + ", "
				+ "\"" + request.getParameter("quizName") + "\", "
				+ "\"" + request.getParameter("author") + "\", "
				+ "\"" + request.getParameter("reporter") + "\", "
				+ "\"" + request.getParameter("comment") + "\", 0);");
		connection.close();
		
		RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
		dispatch.forward(request, response);
	}

}
