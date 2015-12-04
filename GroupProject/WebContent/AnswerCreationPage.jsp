<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*, java.util.List" %>
<%
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Welcome to Cardinal Quiz</title>
</head>
<body>
	<jsp:include page="header.jsp"/>

    <main>
    <div>
    <div id="main-browse-container-center">
  <div id="result-info-container" style="width:100%;">
    <div class="result-selected-class">Add an Answer for your Question!</div>
    
<form name="answerData" id="answerData" action="AnswerCreateServlet" method="post">
	<input type="hidden" name="quizID" id="quizID" value="<%= request.getAttribute("quizID") %>">
	<input type="hidden" name="questionID" id="questionID" value="<%= request.getAttribute("questionID") %>">
	<input type="hidden" name="type" id="type" value="<%= request.getAttribute("type") %>">
	<input type="hidden" name="number" id="number" value="<%= request.getAttribute("number") %>">
	<input type="hidden" id="correctChoices" name="correctChoices" value="">
</form>
<script src="AnswerCreation.js"></script>
</div>
</div>
</body>
</html>