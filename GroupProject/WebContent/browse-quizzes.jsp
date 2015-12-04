<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Quiz, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse Quizzes</title>
</head>
<body>
<h1>Quizzes</h1>
<table style="width:100%">
	<tr>
		<th>Name</th>
		<th>Description</th>		
		<th>Author</th>
		<th>Date Created</th>
		<th>Link</th>
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
				+ "<td>" + quiz.name + "</td>"
				+ "<td>" + formattedDescription + "</td>"
				+ "<td>" + quiz.author + "</td>"
				+ "<td>" + quiz.createdAt + "</td>"
				+ "<td><a href=\"quizsummary.jsp?id=" + quiz.id + "\">Take Quiz!</a></td></tr>");
	}
	%>
</table>
</body>
</html>