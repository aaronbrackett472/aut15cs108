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
		
		String[] answers = request.getParameterValues("answer");
		
		if (type.equals("Fill in the Blank")||type.equals("Question-Response")||type.equals("Picture Response")||type.equals("List")){
			for (int i = 0; i < numAnswers; i++){
				String answer = answers[i];
				String[] splitAnswers = answer.split(";");
				for (String a: splitAnswers){
					Answer.saveToDatabase(questionID, a, i, true, "");
				}
			}
		}
		
		
		if (type.equals("Matching")){

			String[] prompts = request.getParameterValues("prompt");
			for (int i = 0; i < numAnswers; i++){
				String answer = answers[i];
				String prompt = prompts[i];
				Answer.saveToDatabase(questionID, answer, i, true, prompt);
			}
		}
		if (type.equals("Multiple Choice")){
			String checkboxes = request.getParameter("correct");
			String[] splitCheckboxes = checkboxes.split(",");
			for (int i = 0; i < numAnswers; i++){
				String answer = answers[i];
				String iString = Integer.toString(i);
				boolean saved = false;
				for (String check : splitCheckboxes){
					if (iString == check){	
						Answer.saveToDatabase(questionID, answer, i, true, "");
						saved = true;
						break;
					}
						
				}
				if (!saved){
					Answer.saveToDatabase(questionID, answer, i, false, "");
				}
			}
		}
		String quizID = request.getParameter("quizID");
		request.setAttribute("quizID", quizID);
		RequestDispatcher rd = request.getRequestDispatcher("QuestionCreationPage.jsp");
		rd.forward(request, response);
	}

}
