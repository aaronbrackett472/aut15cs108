<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Your Own Quiz!</title>
</head>
<body>
	<form name="Question" id="QuestionForm" action="QuestionCreateServlet" method = "post">
		What type of Question do you want to make?
    	<br>
    	<input type="radio" name="type" value="0">Question-Response
    	<br>
   	 	<input type="radio" name="type" value="1">Fill in the Blank
    	<br>
   		<input type="radio" name="type" value="2">Multiple Choice
    	<br>
    	<input type="radio" name="type" value="3">Picture Response
    	<br>
   		<input type="radio" name="type" value="4">Matching
    	<br>
   		<input type="hidden" id="type">
   		<br>
   		<div id="QuestionData"></div>
    </form>
    <script src="QuestionCreation.js"></script>
</body> 
</html>