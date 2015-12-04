package quizCreateServlet;

import java.io.IOException;
import java.util.Arrays;

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
		int numAnswers = Integer.parseInt(request.getParameter("number"));
		if (numAnswers == 1){
			String answer = request.getParameter("answer");
			Answer.saveToDatabase(questionID, answer, 0, true, "");
		}
		else{
			String[] answers = request.getParameterValues("answer");

			if (type.equals("Matching")){

				String[] prompts = request.getParameterValues("prompt");
				for (int i = 0; i < numAnswers; i++){
					String answer = answers[i];
					String prompt = prompts[i];
					Answer.saveToDatabase(questionID, answer, i, true, prompt);
				}
			}
			if (type.equals("Multiple Choice")){

				String correctChoicesString = request.getParameter("correctChoices");
				String[] correctChoicesArr = correctChoicesString.split(";");
				for (int i = 0; i < numAnswers; i++){
					String answer = answers[i];
					if (Arrays.asList(correctChoicesArr).contains(Integer.toString(i))){
						Answer.saveToDatabase(questionID, answer, i, true, "");
					}
					else{
						Answer.saveToDatabase(questionID, answer, i, false, "");
					}
				}
			}
		}
		String quizID = request.getParameter("quizID");
		request.setAttribute("quizID", quizID);
		RequestDispatcher rd = request.getRequestDispatcher("QuestionCreationPage.jsp");
		rd.forward(request, response);
	}

}
