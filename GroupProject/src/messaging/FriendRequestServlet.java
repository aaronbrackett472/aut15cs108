package messaging;

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
		String accept = request.getParameter("accept");
		String friend = request.getParameter("whose");
//		User us = (User)request.getSession().getAttribute("user"); //request send to them?
//		String userName = us.getUserName();
		
		FriendRequest msg = (FriendRequest)request.getSession().getAttribute("message");
		MessageManager mm = (MessageManager)getServletContext().getAttribute("messageManager");	
		if (accept.equals("yes")) {
			AccountManager manager = new AccountManager();
			manager.addFriend(friend);
			request.setAttribute("successMessage", "You are now "
					+ "friends with " + friend);
		} else if (accept.equals("no")) {
			request.setAttribute("failureMessage", "Friend request removed");
		}
		mm.deleteMessage(msg);
		RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
		if(rd != null)
			rd.forward(request, response);
	}

}
