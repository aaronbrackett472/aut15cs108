package adminStatisticsServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.User;
import database.DatabaseConnection;

/**
 * Servlet implementation class AdminStatisticsServlet
 */
@WebServlet("/AdminStatisticsServlet")
public class AdminStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userCount = 0;
		int quizzesTakenCount = 0;
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Quizzes;");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				quizzesTakenCount += resultSet.getInt("takenCounter");
			}
			
			resultSet = connection.executeQuery("SELECT * FROM Accounts;");
			resultSet.last();
			userCount = resultSet.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();

		request.setAttribute("userCount", userCount);
		request.setAttribute("quizzesTakenCount", quizzesTakenCount);
		RequestDispatcher dispatch = request.getRequestDispatcher("admin-statistics.jsp");
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
