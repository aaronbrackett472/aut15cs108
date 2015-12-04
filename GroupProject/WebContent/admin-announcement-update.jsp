<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Announcement "<%= request.getParameter("id") %>"</title>
<link rel="shortcut icon" href="favicon.ico">
</head>
<body>
<form action="AdminAnnouncementUpdateServlet" method="post">
	ID: <span><%= request.getParameter("id") %></span>
	<input name="id" type="hidden" value="<%= request.getParameter("id") %>">
	<br>Author: <span><%= request.getParameter("author") %></span>
	<br>Date Created: <span><%= request.getParameter("createdAt") %></span>
	<br>Header: <input name="header" type="text" value="<%= request.getParameter("header") %>">
	<br>Body:<br><textarea name="body" rows="4" cols="50"><%= request.getParameter("body") %></textarea>
	<br><input name="update" type="submit" value="Update">
</form>
<form action="AdminAnnouncementUpdateServlet" method="post">
	<input name="id" type="hidden" value="<%= request.getParameter("id") %>">
	<input name="delete" type="submit" value="Delete">
</form>
</body>
</html>