package messaging;
import account.*;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendRequestServlet
 */
//@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_PAGE = "AllFriendRequests.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public FriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String friend = request.getParameter("sender");
		String confirm = request.getParameter("confirmMsg");
		String reject = request.getParameter("ignoreMsg");
		

//		User us = (User)request.getSession().getAttribute("user"); //request send to them?
//		String userName = us.getUserName();
		User us = new User("nzioka");	
		MessageManager mm = (MessageManager)getServletContext().getAttribute("messageManager");	
		
		if (confirm != null) {
			request.setAttribute("successMessage", "You are now "
					+ "friends with " + friend);
		} else if (reject != null) {
			request.setAttribute("failureMessage", "Friend request removed");
		}
		RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
		if(rd != null)
			rd.forward(request, response);
	}

}
