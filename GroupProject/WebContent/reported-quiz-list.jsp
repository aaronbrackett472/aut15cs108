<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.QuizReport, java.util.ArrayList, database.*"%>


<%
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Reported Quizzes</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
    <div class="result-selected-class">Reported Quizzes</div>	
      <div id="browse-results-list">
	<table class="table table-hover table-striped" style="width:100%">
	<tr>
		<th>Report ID</th>
		<th>Quiz ID</th>		
		<th>Quiz Name</th>
		<th>Author</th>
		<th>Reporter</th>
		<th>Comment</th>
		<th>Review</th>
	</tr>
	<%! @SuppressWarnings("unchecked") %>
	<%
	int kCommentPreviewLength = 80;
	ArrayList<QuizReport> reports = (ArrayList<QuizReport>) request.getAttribute("reports");
	for (QuizReport report : reports) {
		String formattedComment = report.comment;
		if (formattedComment.length() > kCommentPreviewLength) {
			formattedComment = formattedComment.substring(0, kCommentPreviewLength) + "...";
		}
		out.println("<tr>"
				+ "<td>" + report.id + "</td>"
				+ "<td>" + report.quizID + "</td>"
				+ "<td>" + report.quizName + "</td>"
				+ "<td>" + report.author + "</td>"
				+ "<td>" + report.reporter + "</td>"
				+ "<td>" + formattedComment + "</td>"
				+ "<td><form action=\"reported-quiz-review.jsp\" method=\"post\">"
				+ "<input name=\"id\" type=\"hidden\" value=\"" + report.id + "\">"
				+ "<input name=\"quizID\" type=\"hidden\" value=\"" + report.quizID + "\">"
				+ "<input name=\"quizName\" type=\"hidden\" value=\"" + report.quizName + "\">"
				+ "<input name=\"author\" type=\"hidden\" value=\"" + report.author + "\">"
				+ "<input name=\"reporter\" type=\"hidden\" value=\"" + report.reporter + "\">"
				+ "<input name=\"comment\" type=\"hidden\" value=\"" + report.comment + "\">"
				+ "<input name=\"resolved\" type=\"hidden\" value=\"" + (report.resolved ? "true" : "false") + "\">"
				+ "<input type=\"submit\" value=\"Review\"></form></td></tr>");
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