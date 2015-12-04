<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

if(request.getAttribute("id") == null){
	out.println("Invalid Quiz ID supplied");
}
	
%>
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Report Quiz</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<%
		String username = (String)session.getAttribute("loggedin_user");
		if (username == null) {
			out.print(Util.showWarningMessage("You are not logged in. Please log in/create an account before using CardinalQuiz."));
			out.close();
			}
      %>	
    <main>
    <div>
    	<div id="main-browse-container-center">
  			<div id="result-info-container" style="width:100%;">
  			
<h1>Report "<%= request.getAttribute("name") %>"</h1>
<p>Author: <%= request.getAttribute("author") %></p>
<p>Description: <%= request.getAttribute("description") %></p>
<p>Date Created: <%= request.getAttribute("createdAt") %></p>
<form action="ReportQuizServlet" method="post">
	<p>Include an optional comment as to why this quiz should be reviewed:</p>
	<input name="quizID" type="hidden" value="<%= request.getAttribute("id") %>">
	<input name="quizName" type="hidden" value="<%= request.getAttribute("name") %>">
	<input name="author" type="hidden" value="<%= request.getAttribute("id") %>">
	<input name="reporter" type="hidden" value="<%= username %>">
	<textarea name="comment" rows="4" cols="50"></textarea>
	<div class="red-class-container">
		<button type="submit" value="Submit">Submit Report</button>
    </div>
</form>
</div>
</div>
</div>
</main>
</body>
</html>