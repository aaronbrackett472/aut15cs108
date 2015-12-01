package reportedQuizListServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.QuizReport;
import database.DatabaseConnection;

/**
 * Servlet implementation class ReportedQuizListServlet
 */
@WebServlet("/ReportedQuizListServlet")
public class ReportedQuizListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportedQuizListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<QuizReport> reports = new ArrayList<QuizReport>();
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM QuizReports WHERE resolved = 0;");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				reports.add(new QuizReport(resultSet.getInt("id"),
						resultSet.getInt("quizID"),
						resultSet.getString("quizName"),
						resultSet.getString("author"),
						resultSet.getString("reporter"),
						resultSet.getString("comment"),
						resultSet.getBoolean("resolved")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();
		
		request.setAttribute("reports", reports);
		RequestDispatcher dispatch = request.getRequestDispatcher("reported-quiz-list.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
