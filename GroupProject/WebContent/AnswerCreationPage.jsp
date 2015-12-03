<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add A Question!</title>
</head>
<body>
<h1>Add an Answer for your Question!</h1>
<form name="answerData" id="answerData" action="AnswerCreateServlet" method="post">
	<input type="hidden" name="quizID" id="quizID" value="<%= request.getAttribute("quizID") %>">
	<input type="hidden" name="questionID" id="questionID" value="<%= request.getAttribute("questionID") %>">
	<input type="hidden" name="type" id="type" value="<%= request.getAttribute("type") %>">
	<input type="hidden" name="number" id="number" value="<%= request.getAttribute("number") %>">
	<input type="hidden" id="correctChoices" name="correctChoices" value="">
</form>
<script src="AnswerCreation.js"></script>
</body>
</html>