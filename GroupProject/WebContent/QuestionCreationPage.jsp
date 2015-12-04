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
    <div class="result-selected-class">Add Questions to your Quiz</div>


	<form name="DoneEditing" id="Done/AddButtons" action="index.jsp">		
		<div class="add-class-container" style="text-align:center;">
			<button type="submit">Done Editing</button>
			<button type="button" id="addQuestion">Add a Question</button>
		</div>
	</form>
	<form name="Question" id="QuestionForm" action="QuestionCreateServlet" method = "post">
   		<div id="RadioButtons"></div>
   		<div id="QuestionData"></div>
   		<input type="hidden" id="type">
   		<input type="hidden" name="quizID" value="<%= request.getAttribute("quizID") %>">
    </form>
    <script src="QuestionCreation.js"></script>
    </div>
</div>
</div>

<%
}
%>

</body> 
</html>