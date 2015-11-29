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
	if (request.getParameter("error") != null){
		String messageToDisplay = request.getParameter("error");
		if (messageToDisplay.equals("duplicateusername")) {
			out.print(Util.showErrorMessage("Username already exists! Please pick a different one"));
		} 
	} 
	%>
    <main>
    <div>
<div id="main-browse-container-center">
  <div id="result-info-container" style="width:100%;">
    <div class="result-selected-class">Create an Account</div>
    <div>
      <div class="prereqs">
          <div class="placeholder-text">
            Creating an account is super simple. We require everyone to have an account before using CardinalQuiz to prevent Spam.
          </div>
      </div>
      <div style="padding-top: 40px;"></div>
	<form action="CreateAccountServlet" method="POST" style="text-align:center;">
      <div class="result-relation-title"><input type="text" name="username" class="big-box" placeholder="Username (alphanumeric only)" class="">
      </div>

	<div class="result-relation-title"><input type="password" name="password" class="big-box" placeholder="Password" class="">
      </div>
      <div class="add-class-container" style="text-align:center;">
      <button type="submit">Submit</button>
      </div>
    
    </form>
    </div>
    
  </div>
  
</div>
</div>
</main>
</body>
</html>