<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, messaging.*, java.sql.Timestamp, account.*"%>

<!-- This page lists all the friendrequests for a particular user
	One way to use this page would be to create a link in the user's home page 
	Each friend request has a link that directs to ViewRequest page
	Assumes that the user has already logged in, and that the "user" attribute is set
	 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
/* 	User user = (User) ses.getAttribute("user");
 */	User user = new User("nzioka");
	String username = user.getUserName();
	MessageManager manager = null;
	String pagen = request.getParameter("page");
	int pagenum = 1;
	if (pagen != null) {
		pagenum = Integer.parseInt(pagen);
	}
	List<FriendRequest> messages = null;
	ServletContext ctx = getServletContext();
	manager = (MessageManager) ctx.getAttribute("messageManager");
	String title = "Friend Requests";
	messages = manager.getFriendRequests(user);
	int numMsgs = messages.size();
%>

<jsp:include page="cssfile.jsp" />

<title><%=title%></title>
</head>
<body>
<jsp:include page="copyheader.jsp" />

	<div id="apDiv1">
		<label class="heading"><strong><%=title%></strong></label>
	</div>

	<jsp:include page="notifications.jsp" />
	
	<div id="apDiv2">
		<%
			String olderLink = "AllFriendRequests.jsp?page=" + (pagenum + 1);
			String newerLink = "AllFriendRequests.jsp?page=" + (pagenum - 1);

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

			<%
				if (messages != null) {
					int messageStart = (pagenum - 1) * messagesPerPage;
					int messagesEnd = (pagenum) * messagesPerPage;
					int limit = Math.min(numMsgs, messagesEnd);
					for (int i = messageStart; i < limit; i++) {
						FriendRequest m = messages.get(i);
						String senderName = m.getSenderName();
						String time = m.getDateSent().toString();
						String receiver = m.getReceiverName();
						String subject = m.getSubject();
						String body = "body";
						boolean isRead = m.isRead();
						String labelClass = isRead ? "read" : "unread";
						String viewLink = "ViewRequest.jsp?ID=" + i;
			%><tr>
				<td><label class=<%=labelClass%>> <%=senderName%>
				</label></td>
				<td><label class=<%=labelClass%>><a href=<%=viewLink%>><%=subject%></a></label></td>
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