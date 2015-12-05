<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Quiz, java.util.ArrayList"%>


<%
	String keyword = "";
	if(request.getParameter("keyword") != null)keyword = request.getParameter("keyword");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Browse All Quizzess</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Browse All Quizzes</div>	
      <div id="browse-results-list">

<form action="BrowseQuizzesServlet" method="get">
	<input name="keyword" id="search-box" placeholder="Search by keyword" class=""
		value="<%= keyword  %>">
</form>
<div style="padding-top:20px;"></div>
<table class="table table-hover table-striped" style="width:100%">
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
</div>
</div>
</div>
</div>
</main>
</body>
</html>