<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.Announcement, java.util.List, account.*"%>
<%
	String username = (String)session.getAttribute("loggedin_user");
	if (username == null) {
		out.print("You are not logged in!");
		out.close();
	} else {
		User currentUserObject = (User)session.getAttribute("userobject");
    	if (currentUserObject != null) {
    		if( !currentUserObject.isAdmin() ) {
    			out.print("You don't have a permission to view this page!");
    			out.close();
    		}
    	}
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Administrator's Panel</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Manage Site Announcements</div>	
      <div id="browse-results-list">
<table class="table table-hover table-striped" style="width:100%">
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
	int kBodyPreviewLength = 100;
	List<Announcement> announcements = (List<Announcement>) request.getAttribute("announcements");
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
</div>
</div>
</div>
</div>
</main>
</body>
</html>