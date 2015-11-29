<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

if(request.getParameter("id") == null){
	out.println("Invalid Quiz ID supplied");
} else {
	int quizId = Integer.parseInt( request.getParameter("id") );
	
	//ServletContext context = request.getServletContext();
	Quiz currentQuiz = new Quiz(connection, quizId);
%>
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Quiz: <% out.print(currentQuiz.getName()); %></title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<%
       			String username = (String)session.getAttribute("loggedin_user");
       			if (username == null) {
       				out.print(Util.showWarningMessage("You are not logged in. Please log in/create an account before using CardinalQuiz."));
       			} else {
       			%>
       			
    <main>
    <div>
    	<div id="main-browse-container-center">
  			<div id="result-info-container" style="width:100%;">
  			<div class="result-selected-class"><% out.print(currentQuiz.getName()); %></div>
  			<div class="prereqs">
          		<div class="placeholder-text"><% out.print(currentQuiz.getDescription()); %></div>
      		</div>
  			<div style="padding-top: 40px;"></div>
  			<div class="result-relation-title">
          		<div class="placeholder-text">Created by <% out.print(currentQuiz.getCreator()); %> on <% out.print(currentQuiz.getCreationDate()); %>.
          		Taken <% out.print(currentQuiz.getTakenCount()); %> times.</div>
      		</div>
      		<div style="padding-top: 40px;"></div>
      		<div class="result-selected-class">Your Performance</div>
      		<div style="padding-top: 40px;"></div>
      		<div class="result-selected-class">Top Performers</div>
    		<form action="TakeQuiz" name="quiz-response" method="GET">
    			<input type="hidden" name="id" value="<% out.print(currentQuiz.getId()); %>">
				<div class="add-class-container">
      				<button type="submit" value="Submit">Take This Quiz!</button>
    			</div>
    		</form>
			</div>
		</div>
	</div>
	</main>
</body>
</html>

<%
       			}
}
%>