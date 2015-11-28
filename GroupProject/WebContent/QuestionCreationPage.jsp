<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Your Own Quiz!</title>
</head>
<body>
	<form name="Question">
		What type of Question do you want to make?
    	<input type="radio" name="type" value="Question-Response">Question-Response
   	 	<input type="radio" name="type" value="Fill in the Blank">Fill in the Blank
   		<input type="radio" name="type" value="Multiple Choice">Multiple Choice
    	<input type="radio" name="type" value="Picture Response">Picture Response
   		<input type="radio" name="type" value="Matching">Matching
   		<div id="Question"></div>
   		<input type="hidden" id="type">
    </form>
    <script src="QuestionCreation.js"></script>
</body> 
</html>