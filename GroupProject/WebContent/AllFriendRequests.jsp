<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, messaging.*, java.sql.Timestamp, account.*, database.*"%>
<!-- This page lists all the friend requests for a particular user
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
	ServletContext context = request.getServletContext();
	String user = (String) session.getAttribute("loggedin_user");
	MessageManager manager = null;
	List<FriendRequest> results = null;
	manager = (MessageManager) context.getAttribute("messageManager");
	results = manager.getFriendRequests(user);
	int numReqs = results.size();
	int numMsgs = manager.getNumMessages(user);
%>
<jsp:include page="cssfile.jsp" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<h4 style="text-align:left;float:left;"><span style="font-weight:normal;"><a href="AllNoteMessages.jsp">Messages<%if (numMsgs >0) { %>(<%=numMsgs%>)<%}%></a></span> &bull; 
	    <a href="AllFriendRequests.jsp">Friend Requests<%if (numReqs >0) { %>(<%=numReqs%>)<%}%></a> 
	</h4>
	<div style="float:right;padding-left:15px;padding-top:5px">
		<form method="post" action="compose.jsp">
			<input type="submit" style="margin:15px;font-size:17px;font-weight:bold" value="Compose" />
		</form>
	</div>	
	<hr style="clear:both;"/>
	<div style="margin-top:15px">
		<%if (results.size() != 0) { 
			for (int i = 0; i < results.size(); i++) { 
				FriendRequest f = results.get(i);
				String sender = f.sender;
				String receiver = f.receiver;
				int id = f.id;
			%>
			<div class="search_user_container">
						<a href="user/profile.jsp?user= <%=sender%>"><%=sender%></a> &nbsp; &nbsp;
 					<form style="display:inline" action="FriendRequestServlet" method="POST">
						<input type="submit" name="confirmMsg" value="Confirm"> &nbsp;
						<input type="submit" name="ignoreMsg" value = "Ignore" onclick="acceptMessage();">
						<input type="hidden" name="from" value=<%=sender%>>
						<input type="hidden" name="receiver" value=<%=receiver%>>
						<input type="hidden" name="id" value=<%=id%>>										
					</form>
			</div>
		<%		
			}
		} else {
		%>
		<center><h4> No new friend requests! </h4></center>
		<%	
		}
		%>
	</div>
	</body>
</html>