package quizCreateServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qanda.Quiz;

/**
 * Servlet implementation class QuizCreateServlet
 */
@WebServlet("/QuizCreateServlet")
public class QuizCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int maxQuizNameLength = 64;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name.length() == 0 || name.length() > maxQuizNameLength) {
			RequestDispatcher dispatch = request.getRequestDispatcher("try-again.html");
			dispatch.forward(request, response);
		} else {
			String questionOrder = request.getParameter("questionOrder");
			String quizStyle = request.getParameter("quizStyle");
			String correctionStyle = request.getParameter("correctionStyle");
			String practiceModeEnabled = request.getParameter("practiceModeEnabled");
			int quizID = Quiz.createQuiz(name,
					questionOrder.equals("random"),
					quizStyle.equals("singlePage"),
					correctionStyle.equals("immediateCorrection"),
					practiceModeEnabled.equals("yes"));
			
			// Database error.
			if (quizID < 0) {
				response.getWriter().println("Error creating quiz. Please try again.");
			}
			
			// Move on to question adding flow.
			response.getWriter().println("QUIZ ID: " + quizID + "\n[ADD QUESTION PAGE]");
			
			/*
			TODO:
				- add quizID to user's list of quizzes
				-link up question creation page
			RequestDispatcher dispatch = request.getRequestDispatcher("[insert question creation page name]");
			dispatch.forward(request, response);
			*/
		}
	}

}