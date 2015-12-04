package messaging;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendRequestServlet
 */
@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_PAGE = "friendrequestlist.jsp";
       
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
		String accept = request.getParameter("accept");
		String friend = request.getParameter("whose");
		User user = (User)request.getSession().getAttribute("user");
		String username = user.getUserName();
		if(accept.equals("yes")){
			AccountManager am = null;
			am = new AccountManager();
			am.addFriend(friend);
		} else if (accept.equals("no")) {
			//decline request
		}
		RequestDispatcher rd = request.getRequestDispatcher(MESSAGE_PAGE);
		rd.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User us = (User)request.getSession().getAttribute("user");
		String user = us.getUserName();
		String friend = request.getParameter("friend");
		AccountManager am = null;
		synchronized(getServletContext()){
			am = (AccountManager)getServletContext().getAttribute("accountManager");
		}
		System.out.println("removing");
		am.removeFriends(user, friend);
		RequestDispatcher rd = request.getRequestDispatcher("friendlist.jsp");
		if(rd != null)
			rd.forward(request, response);
	}

}
