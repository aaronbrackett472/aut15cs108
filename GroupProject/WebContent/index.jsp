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
	if (request.getParameter("message") != null){
		String messageToDisplay = request.getParameter("message");
		if (messageToDisplay.equals("accountcreated")) {
			out.print(Util.showSuccessMessage("Account Created Successfully. Please Log In to Begin Using CardinalQuiz!"));
		}
		if (messageToDisplay.equals("badlogin")) {
			out.print(Util.showErrorMessage("Invalid Username/Password"));
		} 
	} 
	%>
	
    <main>
    <div>
    	<div id="main-browse-container">
  <div id="browse-container">
  
    <div id="browse-results-container">
    <div class="result-selected-class">Latest Quizzes!</div>	
      <input id="search-box" placeholder="Search by keywords or class code" class="">
      <ul id="browse-results-list">
        <%
        	List<Quiz> recentQuizzes = Quiz.getRecentQuizzes(connection, 10);
        	for (Quiz q: recentQuizzes) {
        %>
        
        <li class="browse-result">
          <div class="title"><a href="quizsummary.jsp?id=<% out.print(q.getId()); %>"><% out.print(q.getName()); %></a></div>
          <div class="description">Created by <% out.print(q.getCreator()); %> on <% out.print(q.getCreationDate()); %>. Taken <% out.print(q.getTakenCount()); %> times</div>
        </li>
        <%
        	}
        %>
      </ul>
    </div>
  </div>
  <div id="result-info-container">
    <div class="result-selected-class">Announcements</div>
    <div>
      <div class="result-relation-title">ClassQuiz is Now Live! (12/4)</div>
      <div class="prereqs">
        <ul>
          <div class="placeholder-text">
            After weeks of hard work, CardinalQuiz is now live. We hope everyone enjoys it!  
          </div>
        </ul>
      </div>

      <div class="result-relation-title">Updated Site Rules (11/23)</div>
      <div class="leads-to-classes">
        <ul>
          <div class="placeholder-text">
            Please don't post any NSFW quiz on CardinalQuiz!
          </div>
        </ul>
      </div>
    </div>
    
    <div style="padding-top: 40px;">
    <div class="result-selected-class">Previous Quizzes</div>	
      <div class="placeholder-container">
      	You haven't done any quiz. Perhaps you want to change that?
      </div>
    </div>
    <div class="add-class-container">
      <button>Take a Random Quiz</button>
    </div>
    
    <div style="padding-top: 40px;">
    <div class="result-selected-class">Achievements</div>	
      <div class="placeholder-container">
      	You don't have any achievements
      </div>
    </div> 
    
  </div>
  
</div>
</div>
</main>
</body>
</html>