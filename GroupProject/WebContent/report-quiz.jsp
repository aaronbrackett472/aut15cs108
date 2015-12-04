<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Report Quiz</title>
</head>
<body>
<h1>Report "<%= request.getAttribute("name") %>"</h1>
<p>Author: <%= request.getAttribute("author") %></p>
<p>Description: <%= request.getAttribute("description") %></p>
<p>Date Created: <%= request.getAttribute("createdAt") %></p>
<form action="ReportQuizServlet" method="post">
	<p>Include an optional comment as to why this quiz should be reviewed:</p>
	<input name="quizID" type="hidden" value="<%= request.getAttribute("id") %>">
	<input name="quizName" type="hidden" value="<%= request.getAttribute("name") %>">
	<input name="author" type="hidden" value="<%= request.getAttribute("id") %>">
	<input name="reporter" type="hidden" value="<%= session.getAttribute("loggedin_user") %>">
	<textarea name="comment" rows="4" cols="50"></textarea>
	<input type="submit" value="Submit">
</form>
</body>
</html>