<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="messaging.*, java.util.*, java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
/* 	User us = (User) ses.getAttribute("user");
 */	
	User us = new User("alfonce");
	String receiverName = (String) request.getParameter("to");
	String user = us.getUserName();
	Message m = (Message) ses.getAttribute("message");
	String title = "Challenge";
	String subject="Challenge";	
	int[] messages = {0, 0, 0}; //notes, friendrequests, challenge
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages[0] = manager.numMessages(us, "note");
	messages[1] = manager.numMessages(us, "friendrequest");
	messages[2] = manager.numMessages(us, "challenge");
%>
<link rel = "stylesheet"  type="text/css" href="messaging.css">
<title><%=title%></title>
<script type="text/javascript">
	function discardMessage() {
		document.getElementById('subject').value = "";
		document.getElementById('body').value = "";
		var div = document.getElementById('apDiv2');
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
		  <form id="form1" name="form1" method="post" action="MessageServlet">
			<input type="hidden" name="type" value="challenge"></input>
		  	<label class="message"><b>From:</b></label>
		    <input class="messagefield" type="text" name="fromname" id="from" value="<%=user %>" size=30/>
		  	<label class="message"><b>To:</b></label>
		    <input class="messagefield" type="text" name="toname" id="toname" value="<%=receiverName %>" size=30/>    		    
		    <input type="hidden" name="to" value="<%=receiverName %>"></input>
		    <p><label class="message"><b>Subject</b></label></p>
		    <p><input class="messagefield" type="text" name="subject" id="subject" value="<%=subject %>" size=100/></p>
		    <p><label class="message"><b>Quiz name</b></label></p>
		    <p><input class="messagefield" type="text" name="quiz" id="quiz" size=100/></p>
		    <p><label class="message"><b>Message</b></label></p>
		    <p><textarea class="messagefield" name="body" id="body" cols="90" rows="22"></textarea></p>
		    <input class = "send" type="submit" name="action" id="action" value="Send" />
		    <input class = "send" type="button" name="discard" id="discard" value="Discard" onclick="discardMessage();"/>
		  </form>
		</div>

</body>
</html>