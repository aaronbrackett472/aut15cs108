package messaging;

import java.io.IOException;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
//		User user = (User)request.getSession().getAttribute("user");
		
		User user = new User("alfonce");
		String receiver = request.getParameter("to");
		String subject = request.getParameter("subject");
		String action = request.getParameter("action");
		String body = request.getParameter("body");
		String type = request.getParameter("type");
		String forwardAddress = "MessageStatus.jsp?to="+receiver+"&type="+type+"&action="+action;
//		NoteMessage draft = (NoteMessage)request.getSession().getAttribute("draft");
//		draft.setMessageType("draft");
		MessageManager mm = (MessageManager)getServletContext().getAttribute("messageManager");
//		synchronized(getServletContext()){
//			mm = (MessageManager)getServletContext().getAttribute("messageManager");
//		}
		
		
		Message m = null;
		
		if(action.equals("Send")){ //send three types of messages(add them to table)
			if(type.equals("note")){
//				if (draft != null) {
//					//discard draft (message has been sent)
//					mm.deleteMessage(draft);
//				}
				m = new NoteMessage(user.getUserName(), receiver, subject, new Date(System.currentTimeMillis()), body);
			}else if(type.equals("challenge")){
				String quiz = request.getParameter("quiz");
				m = new ChallengeMessage(user.getUserName(), receiver, subject, new Date(System.currentTimeMillis()), body, quiz);
			}else if(type.equals("friendrequest")){
				m = new FriendRequest(user.getUserName(), receiver, subject, new Date(System.currentTimeMillis()), body);
			}
			request.getSession().setAttribute("message", m);
			mm.addMessage(m);
		}else if(action.equals("Save")){
			m = new NoteMessage(user.getUserName(), receiver, subject, new Date(System.currentTimeMillis()), body);
			m.setMessageType("draft");
			mm.addMessage(m);
			request.getSession().setAttribute("message", m);
		}else if(action.equals("Discard")){
			if(type == null){
				m = (Message)request.getSession().getAttribute("message");
				mm.deleteMessage(m);
			}else if(type.equals("note")){
//				mm.deleteMessage(draft);				
			}else if(type.equals("sent")){
				m = (Message)request.getSession().getAttribute("sent");
				mm.deleteMessage((NoteMessage)m);
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardAddress);
		if(rd != null)
			rd.forward(request, response);
	}

}
