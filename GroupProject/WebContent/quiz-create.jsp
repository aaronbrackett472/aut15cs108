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
    <div class="result-selected-class">Create a new Quiz!</div>
<form action="QuizCreateServlet" method="post">
<p><input type="text" name="name" class="med-box" placeholder="Quiz Name" /></p>
<p><input type="text" name="description" class="med-box" placeholder="Quiz Description" /></p><br>
<p>Choose a quiz question order:</p>
<input type="radio" name="questionOrder" value="ordered" checked> Ordered<br>
<input type="radio" name="questionOrder" value="random"> Random<br>
<p>Choose a quiz style:</p>
<input type="radio" name="quizStyle" value="singlePage" checked> Single page for all questions<br>
<input type="radio" name="quizStyle" value="multiplePages"> One question per page<br>
<p>Choose when to present question solutions:</p>
<input type="radio" name="correctionStyle" value="immediate" checked> Immediately<br>
<input type="radio" name="correctionStyle" value="end"> At the end<br>
<p>Enable practice mode?</p>
<p><small>(This allows a user to take the quiz without their score being recorded)</small></p>
<input type="radio" name="practiceModeEnabled" value="yes"> Yes<br>
<input type="radio" name="practiceModeEnabled" value="no" checked> No<br>
<p>Click "Create" to add questions!</p>

		<div class="add-class-container" style="text-align:center;">
      	<button type="submit">Create</button>
      	</div>

</form>
</div>
</div>
</div>
</main>
<%
}
%>
</body>
</html>