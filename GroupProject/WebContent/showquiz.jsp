<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(request.getParameter("id") == null){
	out.println("Invalid Quiz ID supplied");
} else {
	String quizId = request.getParameter("id");
	ServletContext context = request.getServletContext();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/main.css">
<title><% out.print(quizId); %></title>
</head>
<body>
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
       
       </nav>

    </header>
    <main>
<%
/* 
for (questionId: quiz) {
	out.println("<li><a href=\"show-product.jsp?id=" + p.productId + "\">" + p.name + "</a></li>");
} */
%>
	</main>
</body>
</html>

<%
}
%>