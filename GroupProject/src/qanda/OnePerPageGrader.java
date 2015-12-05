package qanda;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.Achievement;
import account.AchievementItem;
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
		
		// Check the quiz Id parameter
		if (currentQuiz == null) {
			response.getWriter().append("Quiz Not Found in Session!");
		}
		
		int quizId = currentQuiz.getId();
		
		// Check the questionId parameter
		if (request.getParameter("question") == null) {
			response.getWriter().append("Invalid question num parameter!");
		}
		
		int questionIndex = Integer.parseInt(request.getParameter("question"));
		
		int totalScore = (Integer)session.getAttribute("totalScore");
		int perfectScore = (Integer)session.getAttribute("perfectScore");
		
		Enumeration<String> attrList = request.getParameterNames();
		while (attrList.hasMoreElements()) {
			String attrName = attrList.nextElement();
			
			// If this POST variable is a response to a question
			if(attrName.startsWith("response-")) {
				int questionId = Integer.parseInt(attrName.substring(attrName.lastIndexOf("-") + 1));
				int score;
				String[] responses = request.getParameterValues(attrName);
				Question currentQuestion = new Question(connection, questionId);
				//System.out.println("type: " + currentQuestion.getType());
				if(currentQuestion.getType().equals("Question-Response")||currentQuestion.getType().equals("Response")){
					QuestionResponse q = new QuestionResponse(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
				}
				
				else if(currentQuestion.getType().equals("Fill in the Blank")||currentQuestion.getType().equals("Blank")){
					FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
				}
				
				else if(currentQuestion.getType().equals("Picture Response")||currentQuestion.getType().equals("Picture")){
					PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
				}
				
				else if(currentQuestion.getType().equals("Multiple Choice")||currentQuestion.getType().equals("MultipleChoice")){
					MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
					for (String r: responses){
						System.out.println(r);
					}
				}
				else if(currentQuestion.getType().equals("Matching")){
					//System.out.println("type: " + currentQuestion.getType());
					MatchingQuestion q = new MatchingQuestion(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
				}
				else if(currentQuestion.getType().equals("List")){
					ListQuestion q = new ListQuestion(connection, currentQuestion.getQuestionId());
					score = q.evaluateAnswer(responses);
				}
				
				else{
					System.out.println("type: " + currentQuestion.getType());
					Question q = new Question();
					score = q.evaluateAnswer(responses);
				}
				System.out.println("Score: " + score);
				for (String stringResponse: responses){
					request.setAttribute("response-" + questionId, stringResponse);
				}
				request.setAttribute("score-" + questionId, score);
				
				//session.setAttribute("question-" + questionId, score);
				//response.getWriter().append("<div>Question ID " + questionId + ", Score: " + score + "</div>");
            
                session.setAttribute("response-" + questionId, responses);
                session.setAttribute("score-" + questionId, score);
				
				totalScore += score;
				perfectScore += currentQuestion.getPerfectScore(questionId);
			}
		}
		
		session.setAttribute("totalScore", totalScore);
		session.setAttribute("perfectScore", perfectScore);
		
		RequestDispatcher dispatch;
		
		// If this is the last question, grade it, store result in history, and check if any achievement is unlocked
		if (questionIndex+1 == currentQuiz.getNumQuestions()) {
			
			// Calculate time taken
			Date quizFinishTime = new Date();
			Date quizStartTime = (Date)session.getAttribute("quizStartTime");
			int minuteTaken = (int) ((quizFinishTime.getTime() - quizStartTime.getTime())/1000);
			session.setAttribute("minuteTaken", minuteTaken);
			
			Boolean practiceMode = (Boolean)session.getAttribute("practiceMode");
			if(!practiceMode) {
				// Store result in history
				System.out.println("adding to history");
				History historyClass = new History(connection);
				historyClass.storeItem(new HistoryItem(username, totalScore, perfectScore, quizId, minuteTaken, quizFinishTime));
				
				// Increment the taken counter
				Quiz.incrementQuizId(connection, quizId);
			}
						
			// Check if any achievement is unlocked
			Achievement ac = new Achievement(connection);
			List<AchievementItem> unlockedAchievements = ac.unlockAchievementsIfAny(username, quizId, practiceMode);
			for(AchievementItem aItem: unlockedAchievements) {
				ac.storeAchievementItem(aItem);
			}
			session.setAttribute("unlockedAchievements", unlockedAchievements);
			
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
