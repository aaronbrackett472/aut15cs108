<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Announcement</title>
</head>
<body>
<h1>Create Announcement</h1>
<form action="AdminAnnouncementUpdateServlet" method="post">
	<input name="author" type="hidden" value="<%= session.getAttribute("loggedin_user") %>">
	<br>Header: <input name="header" type="text">
	<br>Body:<br><textarea name="body" rows="4" cols="50"></textarea>
	<br><input name="create" type="submit" value="Post">
</form>
</body>
</html>