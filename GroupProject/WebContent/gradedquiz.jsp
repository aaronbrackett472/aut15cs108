<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*, java.util.Arrays, java.util.List" %>

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
	int totalScore = (Integer)session.getAttribute("totalScore");
	int perfectScore = (Integer)session.getAttribute("perfectScore");
	int minuteTaken = (Integer)session.getAttribute("minuteTaken");
	List<AchievementItem> unlockedAchievements = (List<AchievementItem>)session.getAttribute("unlockedAchievements");

%>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico">
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
  			<div style="padding-top: 20px;"></div>
  			<div class="result-selected-class">Time Spent: <% out.print(minuteTaken); %> Seconds</div>
  			<div style="padding-top: 20px;"></div>
  			<% if(unlockedAchievements.size() > 0) {%>
  				<div class="result-selected-class">You Have Unlocked New Achievements!</div>
  			<% for(AchievementItem a: unlockedAchievements) { %>
  				<p> <%= a.getAchievementName() %>: <%= a.getDescription() %></p>
  			<% } %>
  			<div style="padding-top: 20px;"></div>
  			<% } %>
    			
<%

 for (int i = 0; i < currentQuiz.getNumQuestions(); i++) {
	Question currentQuestion = currentQuiz.getQuestionAtIndex(i);
	int questionId = currentQuestion.getQuestionId();
	
	out.println("<div style=\"padding-top: 40px;\">");
	
	if(currentQuestion.getType().equals("Question-Response")){
		QuestionResponse q = new QuestionResponse(connection, questionId);
		out.println(q.getQuestionHTML(i));
	}
	
	if(currentQuestion.getType().equals("Fill in the Blank")){
		FillInTheBlank q = new FillInTheBlank(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
	}
	
	if(currentQuestion.getType().equals("Picture Response")){
		PictureResponse q = new PictureResponse(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
	}
	
	
	if(currentQuestion.getType().equals("Multiple Choice")){
		MultipleChoice q = new MultipleChoice(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
	}
	
	if(currentQuestion.getType().equals("Matching")){
		MatchingQuestion q = new MatchingQuestion(connection, currentQuestion.getQuestionId());
		out.println(q.getQuestionHTML(i));
	}
	
	int currentScore = (Integer)session.getAttribute("score-" + questionId);
	String currentResponse = Arrays.toString((String[])session.getAttribute("response-" + questionId));
	if (currentScore == 0)out.print(Util.showErrorMessage("Your answer " + currentResponse + " is incorrect!", 9));
	else out.print(Util.showSuccessMessage("Your answer " + currentResponse + " is correct!", 9));
	
	out.println("</div>");
	
}  
%>
	<form action="index.jsp" name="quiz-response" method="GET">
	<div class="add-class-container" style="text-align:center;">
      <button type="submit" value="Submit">Back to Home</button>
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