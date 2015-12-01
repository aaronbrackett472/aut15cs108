<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, java.text.*, java.sql.Timestamp, account.*"%>

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
	 */ User us = new User("nzioka");
	String user = us.getUserName();
	int id = Integer.parseInt(request.getParameter("ID"));
	List<NoteMessage> messages = new ArrayList<NoteMessage>();
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages = manager.getDrafts(us);
	NoteMessage note = messages.get(id);
	String title = "View Draft";
	String sender = note.getSenderName();
	String subject = note.getSubject();
	String body = note.getMessageBody();
	String receiverName = note.getReceiverName();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	String time = sdf.format(note.getDateSent());
	ses.setAttribute("draft", note);

	
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
		<form id="form1" name="form1" method="post" action="MessageServlet">
			<input type="hidden" name="type" id="type" value="note" /> <label
				class="message"><b>From:</b></label> <input class="messagefield"
				type="text" name="fromname" id="from" value="<%=sender%>" size=30 />
			<label class="message"><b>To:</b></label> <input class="messagefield"
				type="text" name="toname" id="toname" value="<%=receiverName%>"
				size=30 /> <input type="hidden" name="to"
				value="<%=receiverName%>"></input>
			<p>
				<label class="message"><b>Subject</b></label>
			</p>
			<p>
				<input class="messagefield" type="text" name="subject" id="subject"
					value="<%=subject%>" size=100 />
			</p>
			<p>
				<label class="message"><b>Message</b></label>
			</p>
			<p>
				<textarea class="messagefield" name="body" id="body" cols="90"
					rows="22"><%=body%></textarea>
			</p>
			<input class="send" type="submit" name="action" id="action"
				value="Send" /> <input class="send" type="submit" name="action"
				id="action" value="Save" /> <input class="send" type="submit"
				name="action" id="action" value="Discard" />
		</form>
	</div>
</body>
</html>