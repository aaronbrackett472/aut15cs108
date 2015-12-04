<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="messaging.*, java.util.*, java.sql.Timestamp, java.text.*, account.*"%>


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
	ServletContext context = request.getServletContext();
	String user = (String) session.getAttribute("loggedin_user");
	user="nzioka";
	int id = Integer.parseInt(request.getParameter("msg_id"));
	List<Message> messages = new ArrayList<Message>();
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");
	messages = manager.getUserMessages(user);
	Message msg = manager.getMessage(id);
	String sender = msg.getSenderName();
	String subject = msg.getSubject();
	String body = msg.getMessageBody();
	String type = msg.getMessageType();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	Timestamp time = msg.getDateSent();
	
	int quiz_id = -1;
	if (type.equals("challenge")) {
		quiz_id = msg.getId();
	}
	ses.setAttribute("message", msg);
	manager.markRead(id);
	int numMsgs = messages.size();
	int numReqs = manager.numRequests(user);
 %>
<jsp:include page="cssfile.jsp" />
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
	<jsp:include page="header.jsp" />
	<h4 style="text-align:left;float:left;"><span style="font-weight:normal;"><a href="AllNoteMessages.jsp">Messages<%if (numMsgs >0) { %>(<%=numMsgs%>)<%}%></a></span> &bull; 
	    <span style="font-weight:normal;"><a href="AllFriendRequests.jsp">Friend Requests <%if (numReqs >0) { %>(<%=numReqs%>)<%}%></a> </span>
	</h4>
		<div style="float:right;padding-left:15px;padding-top:5px">
		<form method="post" action="compose.jsp">
			<input type="submit" style="margin:15px;font-size:17px;font-weight:bold" value="Compose" />
		</form>
	</div>	
	<hr style="clear:both;"/>
	<%
	if (type.equals("note")) {
		%>

	<div id="apDiv2">
		<p>&nbsp;</p>
		<label class="message"><%=time%> <%=sender%> wrote:<br /> <br />
			Subject: <%=subject%><br /> <br /> </label> <label class="body"><%=body%></label><br></br>
		<br></br>
		<%
			String replyLink = "SendNote.jsp?to=" + sender+"&msg_id="+msg.getId();
		%><label class="message"><a href=<%=replyLink%>>Reply</a></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
		
		<%	
	} else {
		%>
		<div id="apDiv2">
		<p>&nbsp;</p>
		<%
		body =msg.getSenderName()+ "has challenged you to take a quiz";
		%>
		<label class="message"><%=time%> <%=sender%> wrote:<br /> <br />
			Subject: <%=subject%><br /> <br /> </label> <label class="body"><%=body%></label><br></br>
		<br></br>
		<%
			String replyLink = "quizsummary.jsp?quiz_id=" + msg.getQuizId();
		%><label class="message"><a href=<%=replyLink%>>Click here to view challenge</a></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
			
		<% 
	}
	%>
</body>
</html>