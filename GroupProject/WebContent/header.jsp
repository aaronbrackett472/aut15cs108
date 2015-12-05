<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="account.*, database.*" %>
<header id="site-header">
		<div id="site-banner">
			<div id="classpath-title">CardinalQuiz</div>
			<div id="login-container">
       			<%
       			ServletContext context = request.getServletContext();
       			DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
       			String username = (String)session.getAttribute("loggedin_user");
       			
       			if (username == null) {
       			
       			%>
       			<form name ="login-form" action="LoginServlet" method="POST">
       				<input id="form-box" type="text" name="username" placeholder="Username">
       				<input id="form-box" type="password" name="password" placeholder="Password">
       				<a href="newaccount.jsp" class="auth-btn">Sign Up</button></a>
       				<button type="submit" class="auth-btn">Login</button>
       				
       			</form>
       			<%
       			} else {
       			%>
       				Welcome back, <% out.print(username); %>! <a href="LogoutServlet">Logout</a>
       			<%
       			}
       			%>
       		</div>
       	</div>
      
      <nav>
      	<a class="nav-item" href="index.jsp">Home</a>
      	<a class="nav-item" href="quiz-create.jsp">Create a Quiz</a>
        <a class="nav-item" href="BrowseQuizzesServlet">Browse Quizzes</a>
        
        
	<%
	if (username != null) {
		
	%>        
        <a class="nav-item" href="UserQuizListServlet">Quizzes You Created</a>
        <a class="nav-item" href="allusers.jsp">Add Friends</a>
        <a class="nav-item" href="AllNoteMessages.jsp">Inbox (<%= User.getUnreadMailCount(connection, username) %>)</a>
        <a class="nav-item" href="AllFriendRequests.jsp">Friend Request (<%= User.getFriendRequestCount(connection, username) %>)</a>
        <form class="nav-item" id="searchform" method="GET" action="finduser.jsp">
		  	<input class="navbar-box" type="text" name="search" placeholder="Search User">
		</form>
        <%
        }
        %>
        <%
        	
        	if (session.getAttribute("isAdmin") != null) {
        		int isAdminLoggedin = (Integer)session.getAttribute("isAdmin");
        		if(isAdminLoggedin == 1) {
        			%>
        			
        			<a class="nav-item" href="admin.jsp">Admin Panel</a>
        	<%
        		} 
        	}
        	%>
       </nav>
</header>