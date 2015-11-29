<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
/* 	User us = (User) ses.getAttribute("user");
 */	User us = new User("alfonce");
	String receiverName = (String) request.getParameter("to");
	String user = us.getUserName();
	Message m = (Message) ses.getAttribute("message");
	String action = request.getParameter("action");

	String title = "Message Status";
 	String type = request.getParameter("type");
 	String subject = "";
	String body = "";
	String time = "";
	
	 if (m != null) {
		subject = m.getSubject();
		body = m.getMessageBody();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		time = sdf.format(m.getDateSent());
	} 
	String message = "";
	if (action.equals("Save")) {
		message = "Your message has been saved.";
	} else if (action.equals("Discard")) {
		message = "Your message has been discarded.";
	} else {
		if (type.equals("note")) {
			message = "Your message has been sent.";
		} else if (type.equals("challenge")) {
			message = "Your challenge has been sent.";
		} else { //type == friend
			message = "Your request has been sent to " + receiverName + " for approval.";
		}
	}
	ses.removeAttribute("message");
	ses.removeAttribute("sent");
	ses.removeAttribute("draft");

	int[] messages = {0, 0, 0}; //notes, friendrequests, challenge
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages[0] = manager.numMessages(us, "note");
	messages[1] = manager.numMessages(us, "friendrequest");
	messages[2] = manager.numMessages(us, "challenge");
%>
<link rel="stylesheet" type="text/css" href="messaging.css">

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
		<br /> <br /> <br /> <label class="userlinks"><%=messages[0]%>
			<a class="link" href=<%=allNotes%>>Messages</a><br /> <br /> <%=messages[1]%>
			<a class="link" href=<%=friendrequests%>>Friend requests</a><br /> <br />
			<%=messages[2]%> <a class="link" href=<%=challenges%>>Challenges</a><br />
			<br /> </label>

	</div>
	<div id="apDiv2">
		<label class="status"><%=message%></label><br /> <br />
		<%
			if(m != null &&  !action.equals("Discard")) {
		%>
		<label class="message">On <%=time%> you wrote:
		</label><br /> <br /> <br /> <label class="message">Subject: <%=subject%></label><br />
		<br /> <label class="message">Message:</label> <br /> <br /> <label
			class="body"><%=body%></label><br /> <br />
		<%
			}
		%>
	</div>

</body>
</html>