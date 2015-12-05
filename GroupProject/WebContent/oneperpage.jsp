<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletContext context = request.getServletContext();
DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");

Quiz currentQuiz;
int currentIndex;

if (session.getAttribute("currentQuiz") == null) {
	out.println("Quiz not found. Please enable cookie.");
} else {
	currentQuiz = (Quiz)session.getAttribute("currentQuiz");
	currentIndex = Integer.parseInt(request.getParameter("question"));
	int quizId = currentQuiz.getId();
	
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
    			<form action="OnePerPageGrader" name="quiz-response" method="POST">
<%
	Question currentQuestion = currentQuiz.getQuestionAtIndex(currentIndex);
	
	out.println("<div style=\"padding-top: 40px;\">");
	if(currentQuestion.getType().equals("Question-Response")){
		QuestionResponse q = new QuestionResponse(connection, currentQuestion.getQuestionId());
		
		out.println(q.getQuestionHTML(currentIndex));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Fill in the Blank")){
		FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Picture Response")){
		PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		out.println(q.getResponseInputHTML());
	}
	
	
	if(currentQuestion.getType().equals("Multiple Choice")){
		MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Matching")){
		MatchingQuestion q = new MatchingQuestion(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(currentIndex));
		out.println(q.getResponseInputHTML());
	}
	
	out.println("</div>");
	

%>
	<div class="add-class-container" style="text-align:center;">
      <button type="submit" value="Submit">Next</button>
      <input type="hidden" name="question" value="<%=currentIndex %>" />
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