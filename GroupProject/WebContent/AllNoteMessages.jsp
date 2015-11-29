<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, messaging.*"%>

<!-- This page lists all the note messages for a particular user
	One way to use this page would be to create a link in the user's home page 
	Each note message has a link that directs to ViewNote page
	Assumes that the user has already logged in, and that the "user" attribute is set
	 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	
	HttpSession ses = request.getSession();
/* 	User us = (User) ses.getAttribute("user");
 */	User us = new User("alfonce");
	String user = us.getUserName();
	String pagen = request.getParameter("page");
	int pagenum = 1;
	if (pagen != null) {
		pagenum = Integer.parseInt(pagen);
	}
	List<NoteMessage> messages = null;
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages = manager.getNoteMessages(us);
	String title = "Note Messages";
	int numMsgs = messages.size();
	int[] allMessages = {0, 0, 0}; //notes, friendrequests, challenge
	
	allMessages[0] = numMsgs;
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
	<div id="apDiv2">
		<%
			String olderLink = "AllNoteMessages.jsp?page=" + (pagenum + 1);
			String newerLink = "AllNoteMessages.jsp?page=" + (pagenum - 1);

			int messagesPerPage = 15;
			int numDisplayed = pagenum * messagesPerPage;
			if (pagenum > 1) {
		%>
		<label class="page"><a href=<%=newerLink%>>Newer</a></label>
		<%
			}
			if (numDisplayed < numMsgs) {
		%><label class="page"><a href=<%=olderLink%>>Older</a></label>
		<%
			}
		%>
		<table id="request">
			<tr>
				<th>From</th>
				<th>Subject</th>
			</tr>
			<%
				if (messages != null) {
					int messageStart = (pagenum - 1) * messagesPerPage;
					int messagesEnd = (pagenum) * messagesPerPage;
					int limit = Math.min(numMsgs, messagesEnd);

					for (int i = messageStart; i < limit; i++) {
						NoteMessage m = messages.get(i);
						String senderName = m.getSenderName();
						String time = m.getDateSent().toString();
						String receiver = m.getReceiverName();
						String subject = m.getSubject();
						String body = "body";
						boolean isRead = m.isRead();
						String labelClass = isRead ? "read" : "unread";
						String viewLink = "ViewNote.jsp?ID=" + i;
			%><tr>
				<td><label class=<%=labelClass%>> <%=senderName%> </label></td>
				<td><label class=<%=labelClass%>><a href=<%=viewLink%>><%=subject%></a> </label></td>
			</tr>
			<%
				}
				}
			%>
		</table>
		<%
			if (pagenum > 1) {
		%>
		<label class="page"><a href=<%=newerLink%>>Newer</a></label>
		<%
			}
			if (numDisplayed < numMsgs) {
		%><label class="page"><a href=<%=olderLink%>>Older</a></label>
		<%
			}
		%>


	</div>


</body>
</html>