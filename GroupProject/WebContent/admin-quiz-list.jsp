<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Quiz, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Manage Quizzes</title>
</head>
<body>

	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Manage Quizzes</div>	
      <div id="browse-results-list">
<table class="table table-hover table-striped" style="width:100%">
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
</div>
</div>
</div>
</div>
</main>
</body>
</html>