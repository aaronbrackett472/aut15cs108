<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, java.sql.Timestamp, java.text.*, account.*"%>

<!-- Shows a single note message. Linked with AllNoteMessages. 
	Assumes that we can get the current user from the set attribute
	From this page reply to the message(redirected to the SendNote.jsp file)
     You can also delete the message (redirect to SendMessage servlet which handles deletion of the 
     entry from the friendship table -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
/* 	User us = (User) ses.getAttribute("user");
 */	User us = new User("nzioka");
	String user = us.getUserName();
	int id = Integer.parseInt(request.getParameter("ID"));
	List<NoteMessage> messages = new ArrayList<NoteMessage>();
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");	
	messages = manager.getNoteMessages(us, "received");
	NoteMessage note = messages.get(id);
	String title = "View Note";
	String sender = note.getSenderName();
	String subject = note.getSubject();
	String body = note.getMessageBody();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	String time = sdf.format(note.getDateSent());
	ses.setAttribute("message", note);
	manager.markRead(note);
%>
<jsp:include page="cssfile.jsp" />

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
<jsp:include page="copyheader.jsp" />

	<div id="apDiv1">
		<label class="heading" id="heading"><strong><%=title%></strong></label>
	</div>
	<jsp:include page="notifications.jsp" />
	
	<%
		String deleteLink = "MessageServlet?action=Discard";
	%>
	<div id="apDiv2">
		<p>&nbsp;</p>
		<label class="message"><%=time%> <%=sender%> wrote:<br /> <br />
			Subject: <%=subject%><br /> <br /> </label> <label class="body"><%=body%></label><br></br>
		<br></br>
		<%
			String replyLink = "SendNote.jsp?to=" + sender;
		%><label class="message"><a href=<%=replyLink%>>Reply</a></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label class="message"><a href=<%=deleteLink%>>Delete this
				message</a></label>
	</div>
</body>
</html>