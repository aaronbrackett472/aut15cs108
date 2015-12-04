<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Account</title>
<link rel="shortcut icon" href="favicon.ico">
</head>
<body>
<h1 style="text-align:center"> Quiz Base</h1>
<h2><%="The Name " + request.getParameter("username") + " is Already in Use " %> </h2>
<p>Please try another  name and password </p>
<form action ="CreateAccountServlet" method="post">
<p>
Username: <input type="text" name="username" /> <br />
Password: <input type="password" name="password" />
<input type="submit"> <br />
</p>
</form>

</body>
</html>