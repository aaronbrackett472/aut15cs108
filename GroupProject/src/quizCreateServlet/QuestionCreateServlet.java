package quizCreateServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;
import qanda.*;

/**
 * Servlet implementation class QuestionCreateServlet
 */
@WebServlet("/QuestionCreateServlet")
public class QuestionCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public QuestionCreateServlet() {
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
		
		ServletContext context = request.getServletContext();
		DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
		
		String question = request.getParameter("question");
		int score = Integer.parseInt(request.getParameter("score"));
		
		//int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
		String[] types = {
				"Question-Response", 
				"Fill in the Blank", 
				"Multiple Choice", 
				"Picture Response",
				"Matching"
		};
		
		int typeIndex = Integer.parseInt(request.getParameter("type"));
		String type = types[typeIndex];
		String imageUrl = request.getParameter("url");
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		int questionID = Question.saveToDatabase(connection, quizID, type, score, question, imageUrl);
		if (request.getParameter("numChoices") != null){
			request.setAttribute("number", request.getParameter("numChoices"));
		}
		else if (request.getParameter("numPairs") != null){
			request.setAttribute("number", request.getParameter("numPairs"));
		}
		else{
			request.setAttribute("number", "1");
		}
		RequestDispatcher rd = request.getRequestDispatcher("AnswerCreationPage.jsp");
		request.setAttribute("questionID", questionID);
		request.setAttribute("type", type);
		request.setAttribute("quizID", quizID);
		rd.forward(request, response);
	}

}
