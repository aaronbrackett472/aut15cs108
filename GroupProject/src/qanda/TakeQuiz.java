package qanda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DatabaseConnection;

/**
 * Servlet implementation class TakeQuiz
 */
@WebServlet("/TakeQuiz")
public class TakeQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakeQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int quizId = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
		
		// check if logged in
		String username = (String)session.getAttribute("loggedin_user");
		if (username == null) {
			// Do something
			response.getWriter().append("Not logged in!");
		}		
		
		Quiz q = new Quiz(connection, quizId);
		session.setAttribute("currentQuiz", q);
		RequestDispatcher dispatch;
		
		if(q.useSinglePage()) {
			dispatch = request.getRequestDispatcher("showquiz.jsp?quizid=" + quizId);
		} else {
			session.setAttribute("totalScore", 0);
			session.setAttribute("perfectScore", 0);
			dispatch = request.getRequestDispatcher("oneperpage.jsp?question=0");
		}
		
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
