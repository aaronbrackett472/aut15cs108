<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, java.text.*"%>

<!-- Shows a single challenge message. Linked with AllChallengeMessages. 
	Assumes that we can get the current user from the set attribute
	From this page take the message(redirected to the SendNote.jsp file)
     You can also delete the message (redirect to SendMessage servlet which handles deletion of the 
     entry from the friendship table -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	User us = (User) ses.getAttribute("user");
	String user = us.getUserName();
	int id = Integer.parseInt(request.getParameter("ID"));
	List<ChallengeMessage> messages = null;
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages = manager.getChallenges(us);
	ChallengeMessage msg = messages.get(id);
	String title = "View Challenge";
	String sender = msg.getSenderName();
	String subject = msg.getSubject();
	String body = msg.getMessageBody();
	String quizname = msg.getQuizName();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	String time = sdf.format(msg.getDateSent());
	manager.markRead(msg);
	ses.setAttribute("message", msg);
	
	int[] allMessages = {0, 0, 0}; //notes, friendrequests, challenge	
	allMessages[0] = manager.numMessages(us, "note");
	allMessages[1] = manager.numMessages(us, "friendrequest");
	allMessages[2] = manager.numMessages(us, "challenge");
%>
<link rel = "stylesheet"  type="text/css" href="messaging.css">

<title><%=title%></title>
<script type="text/javascript">
	function discardMessage() {
		document.getElementById('subject').value = "";
		document.getElementById('body').value = "";
		var div = document.getElementById('apDiv3');
		div.removeChild(document.getElementById('form1'));
		document.getElementById('heading').innerHTML = "Request Discarded";
		var label = document.createElement('label');
		label.className = 'message';
		label.innerHTML = "<br></br>Your request has been discarded.";
		div.appendChild(label);
	}
</script>
</head>
<body>
	<div id="apDiv1">
		<label class="heading" id="heading"><strong><%=title%></strong></label>
	</div>
	<%
		String allNotes = "AllNoteMessages.jsp";
		String friendrequests = "AllFriendRequests.jsp";
		String challenges = "AllChallengeMessages.jsp";
	%>
	<div id="notifications">
		<br /> <br /> <br /> <label class="userlinks"><%=allMessages[0]%>
			<a class="link" href=<%=allNotes%>>Messages</a><br /> <br /> <%=allMessages[1]%>
			<a class="link" href=<%=friendrequests%>>Friend requests</a><br /> <br />
			<%=allMessages[2]%> <a class="link" href=<%=challenges%>>Challenges</a><br />
			<br /> </label>

	</div>
	<%
		String deleteLink = "MessageServlet?action=Discard";
	%>

	<div id="apDiv2">
		  <p>&nbsp;</p>
		  <label class="message"><%=time%> <%=sender%> wrote:<br /><br />
			  Subject: <%=subject%><br /><br />
		  </label>
		  <label class="body"><%=body%></label><br></br><br></br>
		  <%String acceptLink = "quiz-summary.jsp?"; //need to redirect to the quiz page 
			%><label class="message"><a href=<%=acceptLink%>>Take <%=quizname %></a></label>&nbsp;&nbsp;&nbsp;&nbsp;
			<label class="message"><a href=<%=deleteLink%>>Delete this message</a></label><br/><br/>
		</div>
</body>
</html>