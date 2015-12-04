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
<form action="BrowseQuizzesServlet" method="get">
	<input name="keyword" id="search-box" placeholder="Search by keyword" class=""
		value="<%= request.getParameter("keyword") %>">
</form>
<table style="width:100%">
	<tr>
		<th>Name</th>
		<th>Description</th>
		<th>Tags</th>
		<th>Author</th>
		<th>Date Created</th>
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
				+ "<td><a href=\"quizsummary.jsp?id=" + quiz.id + "\">" + quiz.name + "</a></td>"
				+ "<td>" + formattedDescription + "</td>"
				+ "<td>");
		for (String tag : quiz.tags) {
			out.println("<a href=\"BrowseQuizzesServlet?keyword=tag:" + tag + "\">" + tag + "</a>");
		}
		out.println("</td>"
				+ "<td>" + quiz.author + "</td>"
				+ "<td>" + quiz.createdAt + "</td></tr>");
	}
	%>
</table>
</body>
</html>