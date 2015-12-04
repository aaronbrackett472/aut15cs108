package adminQuizListServlet;

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

import administration.Announcement;
import administration.Quiz;
import database.DatabaseConnection;

/**
 * Servlet implementation class AdminQuizListServlet
 */
@WebServlet("/AdminQuizListServlet")
public class AdminQuizListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQuizListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Quiz> quizzes = Quiz.getQuizzes();
		
		request.setAttribute("quizzes", quizzes);
		RequestDispatcher dispatch = request.getRequestDispatcher("admin-quiz-list.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz.deleteQuiz(Integer.parseInt(request.getParameter("id")));
		
		doGet(request, response);
	}

}
