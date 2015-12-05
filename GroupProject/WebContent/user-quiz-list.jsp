<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Quiz, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Quizzes You Created</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Quizzes You Created</div>	
      <div id="browse-results-list">
<table class="table table-hover table-striped" style="width:100%">
	<tr>
		<th>Name</th>
		<th>Description</th>
		<th>Question Order</th>
		<th>Page Layout</th>
		<th>Correction Style</th>
		<th>Practice Mode</th>
		<th>Tags</th>
		<th>Date Created</th>
		<th>Edit</th>
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
				+ "<td>" + (quiz.randomOrder ? "Random" : "Ordered") + "</td>"
				+ "<td>" + (quiz.singlePage ? "Single Page" : "Single Question Per Page") + "</td>"
				+ "<td>" + (quiz.immediateCorrection ? "Immediate" : "End") + "</td>"
				+ "<td>" + (quiz.practiceModeAllowed ? "Enabled" : "Disabled") + "</td>"
				
				// Tags.
				+ "<td>");
		for (String tag : quiz.tags) {
			out.println("<a href=\"BrowseQuizzesServlet?keyword=tag:" + tag + "\">" + tag + "</a>");
		}
		out.println("</td>"
				
				+ "<td>" + quiz.createdAt + "</td>"
				+ "<td><form action=\"user-quiz-update.jsp\" method=\"post\">"
				+ "<input name=\"id\" type=\"hidden\" value=\"" + quiz.id + "\">"
				+ "<input name=\"name\" type=\"hidden\" value=\"" + quiz.name + "\">"
				+ "<input name=\"description\" type=\"hidden\" value=\"" + quiz.description + "\">"
				+ "<input name=\"order\" type=\"hidden\" value=\""
					+ (quiz.randomOrder ? "random" : "ordered") + "\">"
				+ "<input name=\"pages\" type=\"hidden\" value=\""
					+ (quiz.singlePage ? "single" : "multiple") + "\">"
				+ "<input name=\"correction\" type=\"hidden\" value=\""
					+ (quiz.immediateCorrection ? "immediate" : "end") + "\">"
				+ "<input name=\"practice\" type=\"hidden\" value=\""
					+ (quiz.practiceModeAllowed ? "enabled" : "disabled") + "\">"
				+ "<input name=\"tags\" type=\"hidden\" value=\"" + quiz.tagString + "\">"
				+ "<input type=\"submit\" value=\"Edit\"></form></td></tr>");
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