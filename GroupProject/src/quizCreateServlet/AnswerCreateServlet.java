package quizCreateServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import qanda.*;
/**
 * Servlet implementation class AnswerCreateServlet
 */
@WebServlet("/AnswerCreateServlet")
public class AnswerCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AnswerCreateServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		int questionID = Integer.parseInt(request.getParameter("questionID"));
		String[] answers = request.getParameterValues("answer");
		int numAnswers = Integer.parseInt(request.getParameter("number"));
		for (int i = 0; i < numAnswers; i++){
			String answer = answers[i];
			int answerID = Answer.saveToDatabase(questionID, answer, i);
		}
		String quizID = request.getParameter("quizID");
		request.setAttribute("quizID", quizID);
		RequestDispatcher rd = request.getRequestDispatcher("QuestionCreationPage.jsp");
		rd.forward(request, response);
	}

}
