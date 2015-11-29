<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

if(request.getAttribute("id") == null){
	out.println("Invalid Quiz ID supplied");
} else {
	int quizId = (Integer)request.getAttribute("id");
	int totalScore = (Integer)request.getAttribute("totalScore");
	int perfectScore = (Integer)request.getAttribute("perfectScore");
	Quiz currentQuiz = new Quiz(connection, quizId);
%>
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Quiz Result: <% out.print(currentQuiz.getName()); %></title>
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
  			<div class="result-selected-class">Your Score: <% out.print(totalScore); %> / <% out.print(perfectScore); %></div>
    			<form action="index.jsp" name="quiz-response" method="POST">
<%

 for (int i = 0; i < currentQuiz.getNumQuestions(); i++) {
	Question currentQuestion = currentQuiz.getQuestionAtIndex(i);
	int questionId = currentQuestion.getQuestionId();
	
	out.println("<div style=\"padding-top: 40px;\">");
	
	if(currentQuestion.getType().equals("Response")){
		QuestionResponse q = new QuestionResponse(connection, questionId);
		out.println(q.getQuestionHTML(i));
		//out.println(q.getResponseInputHTML());
		
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		/* if (currentScore == 0)out.print("<div class=\"row\"><div class=\"bg-danger\">");
		else if (currentScore < currentQuestion.getScore())out.print("<div class=\"row\"><div class=\"bg-warning\">");
		else out.print("<div class=\"row\"><div class=\"bg-success\">"); */
		
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
		
		
	}
	
	if(currentQuestion.getType().equals("Blank")){
		FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	if(currentQuestion.getType().equals("Picture")){
		PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	
	if(currentQuestion.getType().equals("MultipleChoice")){
		MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	out.println("</div>");
	
}  
%>
	<div class="add-class-container" style="text-align:center;">
      <button type="submit" value="Submit">Back to Homepage</button>
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