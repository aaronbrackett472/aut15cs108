<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, account.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(request.getParameter("id") == null){
	out.println("Invalid Quiz ID supplied");
} else {
	int quizId = Integer.parseInt( request.getParameter("id") );
	
	//ServletContext context = request.getServletContext();
	Quiz currentQuiz = new Quiz(quizId);
%>
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Quiz: <% out.print(currentQuiz.getName()); %></title>
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
    <div>
    	<div id="main-browse-container">
  			<div id="browse-container">
  
    			<div id="browse-results-container">
    			<form action="QuizGrader" name="quiz-response" method="POST">
<%

 for (int i = 0; i < currentQuiz.getNumQuestions(); i++) {
	Question currentQuestion = currentQuiz.getQuestionAtIndex(i);
	
	out.println("<div style=\"padding-top: 40px;\">");
	if(currentQuestion.getType().equals("Response")){
		QuestionResponse q = new QuestionResponse(currentQuestion.getQuestionId());
		
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Blank")){
		FillInTheBlank q = new FillInTheBlank(currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	if(currentQuestion.getType().equals("Picture")){
		PictureResponse q = new PictureResponse(currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	
	if(currentQuestion.getType().equals("MultipleChoice")){
		MultipleChoice q = new MultipleChoice(currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
		out.println(q.getResponseInputHTML());
	}
	
	out.println("</div>");
	
}  
%>
	<div class="add-class-container">
      <button type="submit" value="Submit">Grade My Quiz!</button>
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
%>