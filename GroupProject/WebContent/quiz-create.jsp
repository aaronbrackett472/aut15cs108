<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Quiz</title>
</head>
<body>
<h1>Create a New Quiz</h1>
<form action="QuizCreateServlet" method="post">
<p>Name: <input type="text" name="name" /></p><br>
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
<input type="submit" value="Create"/>
</form>
</body>
</html>