<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat, java.util.*, messaging.*"%>

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
	User us = (User) ses.getAttribute("user");
	String user = us.getUserName();
	int id = Integer.parseInt(request.getParameter("ID"));
	List<FriendRequest> messages = null;
	ServletContext ctx = getServletContext();
	MessageManager mm = null;
	mm = (MessageManager) ctx.getAttribute("messageManager");
	messages = mm.getFriendRequests(us);
	FriendRequest friend = messages.get(id);

	String title = "View Request";
	String sender = friend.getSenderName();
	String subject = friend.getSubject();
	String body = friend.getMessageBody();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	String time = sdf.format(friend.getDateSent());
	mm.markRead(friend);
	ses.setAttribute("message", friend);
%>
<title><%=title%></title>

<style type="text/css">
#apDiv1 {
	position: absolute;
	width: 1250px;
	height: 100px;
	z-index: 1;
	left: 0px;
	top: 0px;
	background-color: #048;
}

#apDiv2 {
	position: absolute;
	width: 815px;
	height: 596px;
	overflow: scroll;
	z-index: 3;
	left: 0px;
	top: 100px;
	background-color: #DDDDDD;
	color: #99F;
}

.heading {
	font-family: "Comic Sans MS", cursive;
	font-size: 32px;
	font-weight: 100;
	position: relative;
	left: 30px;
	top: 20px;
	color: white;
}

.message {
	color: black;
	font-weight: bold;
	position: relative;
	left: 20px;
	top: 20px;
}

.body {
	color: black;
	position: relative;
	left: 20px;
	top: 20px;
}

</style>

</head>
<body>
	<div id="apDiv1">
		<label class="heading"><strong><%=title%></strong></label>
	</div>
	<%
		String deleteLink = "SendMessage?action=Discard";
	%>
	<div id="apDiv2">
		<p>&nbsp;</p>
		<label class="message"><%=time%> <%=sender%> wrote:<br />
		<br /> Subject: <%=subject%><br />
		<br /> </label> <label class="body"><%=body%></label><br></br>
		<br></br>
		<%
			String acceptLink = "FriendRequestServlet?whose=" + sender + "&accept=yes";
			String declineLink = "FriendRequestServlet?whose=" + sender + "&accept=no";
		%><label class="message"><a href=<%=acceptLink%>>Accept</a></label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label class="message"><a href=<%=declineLink%>>Decline</a></label>&nbsp;&nbsp;&nbsp;&nbsp;
		<label class="message"><a href=<%=deleteLink%>>Delete this
				message</a></label>
	</div>

</body>
</html>