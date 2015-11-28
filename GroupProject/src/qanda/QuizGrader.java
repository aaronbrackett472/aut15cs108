package qanda;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//doGet(request, response);
		int totalScore = 0;
		
		// Check if logged in
		Enumeration<String> attrList = request.getParameterNames();
		while(attrList.hasMoreElements()){
			String attrName = attrList.nextElement();
			
			// If this POST variable is a response to a question
			if(attrName.startsWith("response-")) {
				int questionId = Integer.parseInt(attrName.substring(attrName.lastIndexOf("-") + 1));
				int score;
				String questionResponse = request.getParameter(attrName);
				Question currentQuestion = new Question(questionId);
				if(currentQuestion.getType().equals("MultipleChoice")){
					MultipleChoice q = new MultipleChoice(currentQuestion.getQuestionId());
					score = q.evaluateAnswer(questionResponse);
				} else {
					score = currentQuestion.evaluateAnswer(questionResponse);
				}
				response.getWriter().append("<div>Question ID " + questionId + ", Score: " + score + "</div>");
				
				totalScore += score;
			}
		}
		
		response.getWriter().append("Total Score: " + totalScore);
	
	}

}
