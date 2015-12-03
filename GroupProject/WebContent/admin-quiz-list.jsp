<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Quiz, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quizzes</title>
</head>
<body>
<h1>Quizzes</h1>
<table style="width:100%">
	<tr>
		<th>Quiz ID</th>
		<th>Name</th>		
		<th>Author</th>
		<th>Description</th>
		<th>Date Created</th>
		<th>Delete</th>
	</tr>
	<%! @SuppressWarnings("unchecked") %>
	<%
	int kDescriptionPreviewLength = 80;
	ArrayList<Quiz> quizzes = (ArrayList<Quiz>) request.getAttribute("quizzes");
	for (Quiz quiz : quizzes) {
		String formattedDescription = quiz.description;
		if (formattedDescription.length() > kDescriptionPreviewLength) {
			formattedDescription = formattedDescription.substring(0, kDescriptionPreviewLength) + "...";
		}
		out.println("<tr>"
				+ "<td>" + quiz.id + "</td>"
				+ "<td>" + quiz.name + "</td>"
				+ "<td>" + quiz.author + "</td>"
				+ "<td>" + formattedDescription + "</td>"
				+ "<td>" + quiz.createdAt + "</td>"
				+ "<td><form action=\"AdminQuizListServlet\" method=\"post\">"
				+ "<input name=\"id\" type=\"hidden\" value=\"" + quiz.id + "\">"
				+ "<input type=\"submit\" value=\"Delete\"></form></td></tr>");
	}
	%>
</table>
</body>
</html>