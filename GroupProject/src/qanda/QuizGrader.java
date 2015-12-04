package qanda;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseConnection;
import account.History;
import account.HistoryItem;
import account.Achievement;
import account.AchievementItem;

/**
 * Servlet implementation class QuizGrader
 */
@WebServlet("/QuizGrader")
public class QuizGrader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizGrader() {
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
		
		int totalScore = 0, perfectScore = 0;
		ServletContext context = request.getServletContext();
		DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
		
		// Check if logged in
		String username = (String)session.getAttribute("loggedin_user");
		if (username == null) {
			// Do something
			response.getWriter().append("Not logged in!");
		} 
		
		// Check the quiz Id parameter
		if (request.getParameter("id") == null) {
			response.getWriter().append("Invalid quiz Id parameter!");
		}
		
		int quizId = Integer.parseInt(request.getParameter("id"));
		
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
				
				//session.setAttribute("question-" + questionId, score);
				//response.getWriter().append("<div>Question ID " + questionId + ", Score: " + score + "</div>");
				
				totalScore += score;
				perfectScore += currentQuestion.getScore();
			}
		}
		
//		GregorianCalendar calendar = new GregorianCalendar();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String curDateString = format.format(calendar.getTime());
		
		// Store result in history
		System.out.println("adding to history");
		History historyClass = new History(connection);
		
		Date quizFinishTime = new Date();
		Date quizStartTime = (Date)session.getAttribute("quizStartTime");
		int minuteTaken = (int) ((quizFinishTime.getTime() - quizStartTime.getTime())/1000);
		
		historyClass.storeItem(new HistoryItem(username, totalScore, perfectScore, quizId, minuteTaken, quizFinishTime));
		
		// Increment the taken counter
		Quiz.incrementQuizId(connection, quizId);
		
		// Need a script to check if any achievement is unlocked
		
		// Store this in request
		session.setAttribute("totalScore", totalScore);
		session.setAttribute("perfectScore", perfectScore);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("gradedquiz.jsp?id=" + quizId);
		dispatch.forward(request, response);
		
		//response.getWriter().append("Total Score: " + totalScore);
	
	}

}
