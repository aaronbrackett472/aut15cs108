package messaging;
import account.*;

import java.io.IOException;

import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageServlet
 */
//@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("to");
		String subject = request.getParameter("subject");
		String action = request.getParameter("action");
		String body = request.getParameter("body");
		MessageManager mm = (MessageManager)getServletContext().getAttribute("messageManager");		
		Message m = null;
		
		if (action != null) {
			if(action.equals("Send")){ //send three types of messages(add them to table)
				m = new Message(0, "note",sender, receiver, subject, new Timestamp(System.currentTimeMillis()), body, 0);
				mm.addMessage(m);
				request.setAttribute("send", "Your message has been sent");
				RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
				if(rd != null)
					rd.forward(request, response);
	
			} else if (action.equals("Discard")) {
				String id = request.getParameter("msg_id");
				mm.deleteMessage(Integer.parseInt(id));
				request.setAttribute("delete", "Message has been deleted");
				RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
				if(rd != null)
					rd.forward(request, response);
			} else if (action.equals("challenge")) {
				String id = request.getParameter("id");
				int quiz_id = Integer.parseInt(id);
				m = new Message(0, "challenge", sender, receiver, subject, new Timestamp(System.currentTimeMillis()), body, quiz_id);
				mm.addMessage(m);
				request.setAttribute("challenge", "Your challenge has been sent");
				RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
				if(rd != null)
					rd.forward(request, response);
			} else if (action.equals("compose")) {
				String type = request.getParameter("type");
				String recepient = request.getParameter("receiver");
				System.out.println(recepient);
				if (type.equals("note")) {
					response.sendRedirect("SendNote.jsp?receiver="+recepient);
				} else if (type.equals("friendrequest")) {
					response.sendRedirect("allusers.jsp");	
				} else if (type.equals("challenge")) {
					response.sendRedirect("selectchallenges.jsp?to=" +recepient);
				} 
//				request.setAttribute("to", recepient);
//				RequestDispatcher rd = request.getRequestDispatcher("SendNote.jsp");
//				if(rd != null)
//					rd.forward(request, response);
			}
		} else if (request.getParameter("inbox_update") != null) {
			String update = request.getParameter("update_type");
			String[] checkedIds = request.getParameterValues("check");
			if (checkedIds != null) {
				if (update.equals("delete")) {
					for (String id: checkedIds) {
						int msg_id = Integer.parseInt(id);
						mm.deleteMessage(msg_id);
						response.sendRedirect("AllNoteMessages.jsp");
					}
				} else if (update.equals("unread")) {
					for (String id: checkedIds) {
						int msg_id = Integer.parseInt(id);
						mm.markUnread(msg_id);
						response.sendRedirect("AllNoteMessages.jsp");
					}
				} else {
					for (String id: checkedIds) {
						int msg_id = Integer.parseInt(id);
						mm.markRead(msg_id);
						response.sendRedirect("AllNoteMessages.jsp");
					}
				}
			}
	
		} else if (request.getParameter("select_quiz") != null) {
			String[] checkedIds = request.getParameterValues("check");
			if (checkedIds != null) {
				for (String id: checkedIds) {
					int quiz_id = Integer.parseInt(id);
					System.out.println(quiz_id);
					System.out.println(sender);
					System.out.println(receiver);
					m = new Message(0, "challenge", sender, receiver, subject, new Timestamp(System.currentTimeMillis()), body, quiz_id);
					mm.addMessage(m);
				}
			}
//			request.setAttribute("challenge", "Your challenge has been sent");
//			RequestDispatcher rd = request.getRequestDispatcher("MessageStatus.jsp");
//			if(rd != null)
//				rd.forward(request, response);
		}
	}

}
