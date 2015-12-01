<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat, java.util.*, messaging.*, java.sql.Timestamp, account.*"%>

<!-- Shows a single friend request. Link this page to the page that contains all 
     friendship request message. From this page you can confirm or decline a request(
     redirected to FriendRequestServlet. 
     You can also delete the request (redirect to SendMessage servlet which handles deletion of the 
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
	List<FriendRequest> messages = null;
	ServletContext ctx = getServletContext();
	MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
	messages = manager.getFriendRequests(us);
	FriendRequest friend = messages.get(id);

	String title = "View Request";
	String sender = friend.getSenderName();
	String subject = friend.getSubject();
	String body = friend.getMessageBody();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	String time = sdf.format(friend.getDateSent());
	manager.markRead(friend);
	ses.setAttribute("message", friend);
	
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
		/* String rejectLink = "FriendRequestServlet?action=reject";
		String acceptLink = "FriendRequestServlet?action=reject"; */
	%>
	<div id="apDiv2">
		<p>&nbsp;</p>
		
		<label class="message"><%=time%> <%=sender%> wrote:<br /> <br />
			Subject: <%=subject%><br /> <br /> </label> <label class="body"><%=body%></label><br></br>
		<br></br>
		<%
			String acceptLink = "FriendRequestServlet?whose=" + sender + "&accept=yes";
			String declineLink = "FriendRequestServlet?whose=" + sender + "&accept=no";
		%><label class="message"><a href=<%=acceptLink%>>Accept</a></label>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="hidden" name="type" value="request"></input>
		<label class="message"><a href=<%=declineLink%>>Decline</a></label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label class="message"><a href=<%=deleteLink%>>Delete this
				message</a></label>
	</div>

</body>
</html>