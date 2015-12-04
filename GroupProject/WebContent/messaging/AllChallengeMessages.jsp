<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, messaging.*"%>

<!-- This page lists all the challenge messages for a particular user
	One way to use this page would be to create a link in the user's home page 
	Each challenge message has a link that directs to ViewChallenge page
	Assumes that the user has already logged in, and that the "user" attribute is set
	 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	User us = (User) ses.getAttribute("user");
	String user = us.getUserName();
	MessageManager mm = null;
	String pagen = request.getParameter("page");
	int pagenum = 1;
	if (pagen != null) {
		pagenum = Integer.parseInt(pagen);
	}
	List<ChallengeMessage> messages = null;
	ServletContext ctx = getServletContext();
	mm = (MessageManager) ctx.getAttribute("messageManager");
	messages = mm.getChallenges(us);
	String title = "Challenge Messages";
	int numMsgs = messages.size();
%>
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
	width: 1028px;
	height: 596px;
	z-index: 3;
	left: 0px;
	top: 100px;
	background-color: #DBD6E0;
	color: #99F;
}

#request {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#request td, #request th {
	font-size: 1em;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#request th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #ffffff;
}

.heading {
	font-family: "Comic Sans MS", cursive;
	font-size: 28px;
	font-weight: 100;
	position: relative;
	left: 30px;
	top: 20px;
	color: white;
}

.userlinks {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	position: relative;
	left: 20px;
	top: 10px;
}

.unread {
	font-size: 14px;
	font-weight: bold;
	position: relative;
	left: 20px;
	color: black;
}

.read {
	font-size: 14px;
	position: relative;
	left: 20px;
	color: black;
}

.headers {
	font-size: 14px;
	font-weight: bold;
	color: blue;
	position: relative;
	left: 20px;
}

.page {
	text-align: right;
	font-size: 14px;
	font-weight: bold;
}
</style>

<title><%=title%></title>

</head>
<body>
	<div id="apDiv1">
		<label class="heading"><strong><%=title%></strong></label>
	</div>
	<div id="apDiv2">
		<%
			String header = "From";
			String olderLink = "AllChallengeMessages.jsp?page=" + (pagenum + 1);
			String newerLink = "AllChallengeMessages.jsp?page=" + (pagenum - 1);
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
						ChallengeMessage m = messages.get(i);
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