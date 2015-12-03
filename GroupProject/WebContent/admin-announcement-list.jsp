<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Announcement, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reported Quizzes</title>
</head>
<body>
<h1>Announcements</h1>
<table style="width:100%">
	<tr>
		<th>Announcement ID</th>
		<th>Header</th>		
		<th>Body</th>
		<th>Author</th>
		<th>Date Created</th>
		<th>Edit</th>
	</tr>
	<%! @SuppressWarnings("unchecked") %>
	<%
	int kBodyPreviewLength = 80;
	ArrayList<Announcement> announcements = (ArrayList<Announcement>) request.getAttribute("announcements");
	for (Announcement announcement : announcements) {
		String formattedBody = announcement.body;
		if (formattedBody.length() > kBodyPreviewLength) {
			formattedBody = formattedBody.substring(0, kBodyPreviewLength) + "...";
		}
		out.println("<tr>"
				+ "<td>" + announcement.id + "</td>"
				+ "<td>" + announcement.header + "</td>"
				+ "<td>" + formattedBody + "</td>"
				+ "<td>" + announcement.author + "</td>"
				+ "<td>" + announcement.createdAt + "</td>"
				+ "<td><form action=\"admin-announcement-update.jsp\" method=\"post\">"
				+ "<input name=\"id\" type=\"hidden\" value=\"" + announcement.id + "\">"
				+ "<input name=\"author\" type=\"hidden\" value=\"" + announcement.author + "\">"
				+ "<input name=\"header\" type=\"hidden\" value=\"" + announcement.header + "\">"
				+ "<input name=\"body\" type=\"hidden\" value=\"" + announcement.body + "\">"
				+ "<input name=\"createdAt\" type=\"hidden\" value=\"" + announcement.createdAt + "\">"
				+ "<input type=\"submit\" value=\"Edit\"></form></td></tr>");
	}
	%>
</table>
</body>
</html>