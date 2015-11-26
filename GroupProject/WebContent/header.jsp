<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header id="site-header">
		<div id="site-banner">
			<div id="classpath-title">CardinalQuiz</div>
			<div id="login-container">
       			Welcome Back, pat!
       		</div>
       	</div>
      
      <nav>
      	<a class="nav-item" href="/GroupProject/index.jsp">Home</a>
        <a class="nav-item" href="/GroupProject/browse.jsp">Browse Quizzes</a>
        <a class="nav-item" href="/GroupProject/notifications.jsp">Notifications</a>
        <a class="nav-item" href="/GroupProject/friends.jsp">Friends</a>
       <form id="login-form" method="POST" action="/logout/" class="ng-pristine ng-valid">
               <button type="submit" class="auth-btn">Logout</button>
              <input type="hidden" name="csrfmiddlewaretoken" value="nBuwSaDva0fVGB5YLyVgaPsX9cRbUmZV">
       </form>
       </nav>
</header>