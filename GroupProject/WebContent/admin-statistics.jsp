<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Statistics</title>
</head>
<body>
<h1>Site Statistics</h1>
<p><%= request.getAttribute("userCount") %> users.</p>
<p><%= request.getAttribute("quizzesTakenCount") %> quizzes taken.</p>
</body>
</html>