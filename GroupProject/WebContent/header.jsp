<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, account.*" %>
<header id="site-header">
		<div id="site-banner">
			<div id="classpath-title">CardinalQuiz</div>
			<div id="login-container">
       			<%
       			
       			%>
       			<form action="LoginServlet" method="POST">
       			<input id="form-box" type="text" name="username" placeholder="Username">
       			<input id="form-box" type="password" placeholder="Password">
       			<button type="submit" class="auth-btn">Login</button>
       			</form>
       			<%
       			%>
       		</div>
       	</div>
      
      <nav>
      	<a class="nav-item" href="/GroupProject/index.jsp">Home</a>
        <a class="nav-item" href="/GroupProject/browse.jsp">Browse Quizzes</a>
        <a class="nav-item" href="/GroupProject/notifications.jsp">Notifications</a>
        <a class="nav-item" href="/GroupProject/friends.jsp">Friends</a>
       </nav>
</header>