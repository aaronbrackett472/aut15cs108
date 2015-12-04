<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

if (request.getParameter("quizid") == null) {
	out.println("Invalid Quiz ID supplied");
} else {
	int quizId = Integer.parseInt( request.getParameter("quizid") );
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
    			<form action="QuizGrader" name="quiz-response" method="POST">
<%

 for (int i = 0; i < currentQuiz.getNumQuestions(); i++) {
	Question currentQuestion = currentQuiz.getQuestionAtIndex(i);
	
	out.println("<div style=\"padding-top: 40px;\">");
	if(currentQuestion.getType().equals("Question-Response")){
		QuestionResponse q = new QuestionResponse(connection, currentQuestion.getQuestionId());
		
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Fill in the Blank")){
		FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Picture Response")){
		PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	
	if(currentQuestion.getType().equals("Multiple Choice")){
		MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	out.println("</div>");
	
}  
%>
	<div class="add-class-container" style="text-align:center;">
      <button type="submit" value="Submit">Grade My Quiz!</button>
      <input type="hidden" name="id" value="<% out.print(quizId); %>" />
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