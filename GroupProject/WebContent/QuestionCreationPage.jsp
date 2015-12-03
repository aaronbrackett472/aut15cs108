<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Your Own Quiz!</title>
</head>
<body>
	<form name="DoneEditing" id="Done/AddButtons" action="index.jsp">
		<input type="submit" value="Done Editing">
		<button type="button" id="addQuestion">Add a Question</button>
	</form>
	<form name="Question" id="QuestionForm" action="QuestionCreateServlet" method = "post">
   		<div id="RadioButtons"></div>
   		<div id="QuestionData"></div>
   		<input type="hidden" id="type">
   		<input type="hidden" name="quizID" value="<%= request.getAttribute("quizID") %>">
    </form>
    <script src="QuestionCreation.js"></script>
</body> 
</html>