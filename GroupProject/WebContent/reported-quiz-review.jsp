<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Review "<%= request.getParameter("quizName") %>"</title>
</head>
<body>
<h1>Review <span>"<%= request.getParameter("quizName") %>"</span></h1>
<p>Author: <span><%= request.getParameter("author") %></span></p>
<p>Reporter: <span><%= request.getParameter("reporter") %></span></p>
<p>Quiz ID: <span><%= request.getParameter("quizID") %></span></p>
<p>Comment:</p>
<p><small><span><%= request.getParameter("comment") %></span></small></p>
<form action="ReportedQuizReviewServlet" method="post">
	<input name="id" type="hidden" value="<%= request.getParameter("id") %>">
	<input name="resolved" type="submit" value="Mark Resolved"><br>
</form>
<p><span>-OR-</span></p>
<form action="ReportedQuizReviewServlet" method="post">
	<input name="id" type="hidden" value="<%= request.getParameter("id") %>">
	<input name="author" type="hidden" value="<%= request.getParameter("author") %>">
	<span>Suspend author for</span>
	<input name="suspension" type="text" value="30">
	<span>days.</span>
	<input name="suspendAuthor" type="submit" value="Submit">
</form>
</body>
</html>