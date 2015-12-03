<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.QuizReport, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reported Quizzes</title>
</head>
<body>
<h1>Reported Quizzes</h1>
<table style="width:100%">
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
</body>
</html>