<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, account.*, database.*"%>

<%
	//display all users (can select from here to send friend request/message etc))
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	String username = request.getParameter("who");
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String currentUser = (String)session.getAttribute("loggedin_user");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");		
	User us = new User(currentUser, connection);
	AccountManager account = new AccountManager(connection);
	List<String> users = account.getAllUsers();
	int numUsers = users.size();
	String imageLink = "user.png";	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico">
	<jsp:include page="include.jsp"/>
	<title>User Profile</title>
</head>
<body>
	<jsp:include page="header.jsp"/>

    <main>
    <div>
<div id="main-browse-container-center">
  <div id="result-info-container" style="width:100%;">
	<div class="result-selected-class"><%= username %></div>

	<img class="image" src=<%=imageLink %> width=200 height=200></img><br></br><br/>
	
	<%
		if (!us.checkFriend(username)) {
			String addFriend = "FriendRequestServlet?to=" + username + "&from=" + currentUser;
			
			%>
			<label class="user"><a class="link" href=<%=addFriend%>>Add <%=username%> as a friend </a></label><br /> <br />
			<% } else {
				%>
				<label class="user"><%=username%> is already your friend!</label><br /> <br />
				<%
		}
		String sendNote = "SendNote.jsp?receiver=" + username;
		%>
		<label class="user"><a class="link" href=<%=sendNote%>>Send Note
		</a></label><br /> <br />
		<%
	%>
	</div>
	</div>
	</div>
	</main>
</body>
</html>