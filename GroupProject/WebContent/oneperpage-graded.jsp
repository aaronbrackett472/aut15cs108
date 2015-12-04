<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

if (session.getAttribute("currentQuiz") == null) {
	out.println("Quiz not found. Please enable cookie.");
	out.close();
} else {
	Quiz currentQuiz = (Quiz)session.getAttribute("currentQuiz");
	int quizId = currentQuiz.getId();
	int currentIndex = Integer.parseInt(request.getParameter("question"));

	int totalScore = (Integer)request.getAttribute("totalScore");
	int perfectScore = (Integer)request.getAttribute("perfectScore");
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
    			
<%

 
	Question currentQuestion = currentQuiz.getQuestionAtIndex(currentIndex);
	int questionId = currentQuestion.getQuestionId();
	
	out.println("<div style=\"padding-top: 40px;\">");
	
	if(currentQuestion.getType().equals("Question-Response")){
		QuestionResponse q = new QuestionResponse(connection, questionId);
		out.println(q.getQuestionHTML(currentIndex));
		//out.println(q.getResponseInputHTML());
		
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		/* if (currentScore == 0)out.print("<div class=\"row\"><div class=\"bg-danger\">");
		else if (currentScore < currentQuestion.getScore())out.print("<div class=\"row\"><div class=\"bg-warning\">");
		else out.print("<div class=\"row\"><div class=\"bg-success\">"); */
		
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
		
		
	}
	
	if(currentQuestion.getType().equals("Fill in the Blank")){
		FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	if(currentQuestion.getType().equals("Picture Response")){
		PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	
	if(currentQuestion.getType().equals("Multiple Choice")){
		MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		//out.println(q.getResponseInputHTML());
		int currentScore = (Integer)request.getAttribute("score-" + questionId);
		String currentResponse = (String)request.getAttribute("response-" + questionId);
		if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
		else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	}
	
	out.println("</div>");
	

%>
	<form action="oneperpage.jsp" name="quiz-response" method="GET">
		<input type="hidden" name="question" value="<%= currentIndex+1 %>" />
		<div class="add-class-container" style="text-align:center;">
      	<button type="submit" value="Submit">Next</button>
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