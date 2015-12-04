package qanda;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.History;
import account.HistoryItem;
import database.DatabaseConnection;

/**
 * Servlet implementation class OnePerPageGrader
 */
@WebServlet("/OnePerPageGrader")
public class OnePerPageGrader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnePerPageGrader() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
		
		// Check if logged in
		String username = (String)session.getAttribute("loggedin_user");
		if (username == null) {
			// Do something
			response.getWriter().append("Not logged in!");
		}
		
		Quiz currentQuiz = (Quiz)session.getAttribute("currentQuiz");
		int quizId = currentQuiz.getId();
		
		// Check the quiz Id parameter
		if (currentQuiz == null) {
			response.getWriter().append("Quiz Not Found in Session!");
		}
		
		int questionIndex;
		
		// Check the questionId parameter
		if (request.getParameter("question") == null) {
			response.getWriter().append("Invalid question num parameter!");
		}
		
		questionIndex = Integer.parseInt(request.getParameter("question"));
		
		int totalScore = (Integer)session.getAttribute("totalScore");
		int perfectScore = (Integer)session.getAttribute("perfectScore");
		
		Enumeration<String> attrList = request.getParameterNames();
		while (attrList.hasMoreElements()) {
			String attrName = attrList.nextElement();
			
			// If this POST variable is a response to a question
			if(attrName.startsWith("response-")) {
				int questionId = Integer.parseInt(attrName.substring(attrName.lastIndexOf("-") + 1));
				int score;
				String questionResponse = request.getParameter(attrName);
				Question currentQuestion = new Question(connection, questionId);
				if(currentQuestion.getType().equals("Multiple Choice")){
					MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(questionResponse);
				} else {
					score = currentQuestion.evaluateAnswer(questionResponse);
				}
				
				session.setAttribute("response-" + questionId, questionResponse);
				session.setAttribute("score-" + questionId, score);
				
				totalScore += score;
				perfectScore += currentQuestion.getScore();
			}
		}
		
		session.setAttribute("totalScore", totalScore);
		session.setAttribute("perfectScore", perfectScore);
		
		RequestDispatcher dispatch;
		
		if (questionIndex+1 == currentQuiz.getNumQuestions()) {
			
			// Store result in history
			System.out.println("adding to history");
			History historyClass = new History(connection);
			historyClass.storeItem(new HistoryItem(username, totalScore, perfectScore, quizId, new Date()));
			
			// Increment the taken counter
			Quiz.incrementQuizId(connection, quizId);
			
			// Need a script to check if any achievement is unlocked
			
			// Store this in request
			session.setAttribute("totalScore", totalScore);
			session.setAttribute("perfectScore", perfectScore);
			
			dispatch = request.getRequestDispatcher("gradedquiz.jsp?id=" + currentQuiz.getId());
		} else {
			if(currentQuiz.useImmediateCorrection()){
				dispatch = request.getRequestDispatcher("oneperpage-graded.jsp?question=" + questionIndex);
			} else {
				dispatch = request.getRequestDispatcher("oneperpage.jsp?question=" + (questionIndex+1));
			}
		}
		dispatch.forward(request, response);
		
	}

}
