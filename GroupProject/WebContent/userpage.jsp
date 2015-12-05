<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, account.*, database.*, qanda.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	String username = request.getParameter("who");
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String currentUser = (String)session.getAttribute("loggedin_user");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");	
	User us = new User(currentUser, connection);
   	History his = new History(connection);
	List<Quiz> recentQuizzes = his.getAllQuizzesTakenByUsername(username);
	AccountManager account = new AccountManager(connection);
	List<String> users = account.getAllUsers();
	int numUsers = users.size();
	String imageLink = "user.png";	
%>
<jsp:include page="include.jsp"/>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<img class="image" src=<%=imageLink %> width=200 height=200></img><br></br><br/>
	
	<%
		 if (!us.checkFriend(username)) {
 		String addFriend = "FriendRequestServlet?to=" + username + "&from=" + currentUser;
			%>
			<button onclick="location.href = '<%=addFriend %>';" id="myButton" class="float-left submit-button" >Request Friend</button>
			<%
		   } 
 		String sendNote = "SendNote.jsp?receiver=" + username;
		%>
			<button onclick="location.href = '<%=sendNote %>';" id="myButton" class="float-left submit-button" >Send Message</button>
		<%
	%>
	
	<h2 style="color:blue;">Attempted Quizzes</h2>
	<div id="browse-results-container">
      <ul id="browse-results-list">
        <%
        	for (Quiz q: recentQuizzes) {
        %>
        
        <li class="browse-result">
          <div class="title"><a href="quizsummary.jsp?id=<% out.print(q.getId()); %>"><% out.print(q.getName()); %></a></div>
          <div class="description">Created by <% out.print(q.getCreator()); %> on <% out.print(q.getCreationDate()); %>. Taken <% out.print(q.getTakenCount()); %> times</div>
        </li>
        <%
        	}
        %>
      </ul>
    </div>
    <%
    	if (recentQuizzes.isEmpty()) {
    		%>
    		<h3> No quizzes taken yet </h3>
    		<%
    	}
    
    %>
    
    
</body>
</html>