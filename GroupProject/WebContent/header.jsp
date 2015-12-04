<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="account.*" %>
<header id="site-header">
		<div id="site-banner">
			<div id="classpath-title">CardinalQuiz</div>
			<div id="login-container">
       			<%
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
        <a class="nav-item" href="AllNoteMessages.jsp">Inbox</a>
        <a class="nav-item" href="friends.jsp">Friends</a>
        <%
        	User currentUserObject = (User)session.getAttribute("userobject");
        	if (currentUserObject != null) {
        		if( currentUserObject.isAdmin() ) {
        			%>
        			<a class="nav-item" href="admin.jsp">Admin Panel</a>
        			<%
        		}
        	}
        %>
       </nav>
</header>