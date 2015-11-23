<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if(request.getParameter("id") == null){
	out.println("Invalid Quiz ID supplied");
} else {
	String quizId = request.getParameter("id");
	ServletContext context = request.getServletContext();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><% out.print(quizId); %></title>
</head>
<body>

</body>
</html>

<%
}
%>