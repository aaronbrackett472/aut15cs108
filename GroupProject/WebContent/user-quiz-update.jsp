<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Quiz</title>
</head>
<body>
<h1>Edit Quiz</h1>
<form action="UserQuizListServlet" method="post">
	<input name="id" type="hidden" value="<%= request.getParameter("id") %>">
	Name: <input name="name" type="text" value="<%= request.getParameter("name") %>"><br>
	<textarea name="description" rows="4" cols="50"><%= request.getParameter("description") %></textarea>
	Question order:<br>
	<%
	boolean randomOrder = request.getParameter("order").equals("random");
	%>
	<input type="radio" name="order" value="random" <%= randomOrder ? "checked" : "" %>> Random<br>
	<input type="radio" name="order" value="ordered" <%= !randomOrder ? "checked" : "" %>> Ordered<br>
	Page layout:<br>
	<%
	boolean singlePage = request.getParameter("pages").equals("single");
	%>
	<input type="radio" name="pages" value="single" <%= singlePage ? "checked" : "" %>> Single page for all questions<br>
	<input type="radio" name="pages" value="multiple" <%= !singlePage ? "checked" : "" %>> One question per page<br>
	Correction style:<br>
	<%
	boolean immediateCorrection = request.getParameter("correction").equals("immediate");
	%>
	<input type="radio" name="correction" value="immediate" <%= immediateCorrection ? "checked" : "" %>> Immediately<br>
	<input type="radio" name="correction" value="end" <%= !immediateCorrection ? "checked" : "" %>> At the end<br>
	Practice mode:<br>
	<%
	boolean practiceModeAllowed = request.getParameter("practice").equals("enabled");
	%>
	<small>(This allows a user to take the quiz without their score being recorded)</small><br>
	<input type="radio" name="practice" value="enabled" <%= practiceModeAllowed ? "checked" : "" %>> Enabled<br>
	<input type="radio" name="practice" value="disabled" <%= !practiceModeAllowed ? "checked" : "" %>> Disabled<br>
	Tags: <input name="tags" type="text" value="<%= request.getParameter("tags") %>"><br>
	<input type=submit value="Update">
</form>
</body>
</html>