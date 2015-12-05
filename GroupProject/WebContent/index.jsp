<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="qanda.*, database.*, account.*, administration.Announcement, java.util.List" %>
<%
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String username = (String)session.getAttribute("loggedin_user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico">
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
		
		if (messageToDisplay.equals("suspended")) {
			out.print(Util.showErrorMessage("Your Account is Suspended. Please Contact the Administrator."));
		} 
	} 
	%>
	
    <main>
    <div>
    	<div id="main-browse-container">
  <div id="browse-container">
  
    <div id="browse-results-container">
    <div class="result-selected-class">Latest Quizzes!</div>	
      <form action="BrowseQuizzesServlet" method="get">
      	<input name="keyword" id="search-box" placeholder="Search by keyword" class="">
      </form>
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
    
        <%
        	List<Announcement> recentAnnouncements = Announcement.getRecentAnnouncements(connection, 10);
        	for (Announcement a: recentAnnouncements) {
        %>   
    
      <div class="result-relation-title"><%= a.header %> (<%= a.createdAt.toString() %>) by <%= a.author %></div>
      <div class="prereqs">
        <ul>
          <div class="placeholder-text">
            <%= a.body %>  
          </div>
        </ul>
      </div>
      <%
        	}
      %>
    </div>
    
    <div style="padding-top: 40px;">
    <div class="result-selected-class">Last 10 Quizzes You Took</div>	
      <div class="placeholder-container">
      <%
      if (username == null) {
      	out.println("You are not logged in. Please log in to see previous quizzes taken")	;
      } else {
    	  History historyForHomepage = new History(new DatabaseConnection());
    	  List<HistoryItem> prevQuiz = historyForHomepage.getHistoryByUsernameWithLimit(username, 10);
    	  if ( prevQuiz.size() == 0 ) {
    		  out.println("You haven't done any quiz. Perhaps you want to change that?");
    	  } else {
    		  for(HistoryItem hItem: prevQuiz) {
    			  Quiz qItem = new Quiz(connection, hItem.getQuizId());
      %>
      	<div><a href="quizsummary.jsp?id=<%= hItem.getQuizId() %>"><%= qItem.getName() %></a> taken on <%= hItem.getDateTaken() %>, Score: <%= hItem.getScore() %>/<%= hItem.getMaxScore() %>, Finished in <%= hItem.getMinuteTaken() %> seconds</div>
      	<%
      		}
      	}
      }
      	%>
      </div>
    </div>
    
    <div style="padding-top: 40px;">
    <div class="result-selected-class">Previous Quizzes You Created</div>	
      <div class="placeholder-container">
      <%
      if (username == null) {
      	out.println("You are not logged in. Please log in to see previous quizzes taken")	;
      } else {
    	  
    	  List<Quiz> createdQuizzes = Quiz.getRecentQuizzesByCreator(connection, username, 5);
    	  if ( createdQuizzes.size() == 0 ) {
    		  out.println("You haven't created any quiz. Perhaps you want to change that?");
    	  } else {
    		  for(Quiz qItem: createdQuizzes) {
      %>
      	<div><a href="quizsummary.jsp?id=<%= qItem.getId() %>"><%= qItem.getName() %></a> created on <%= qItem.getCreationDate() %></div>
      	<%
      		}
      	}
      }
      	%>
      </div>
    </div>
    
    
    <%
    if (username != null) {
    %>
    <form action="TakeRandomQuiz" name="quiz-response" method="GET">
    	<div class="add-class-container">
      		<button>Take a Random Quiz</button>
    	</div>
    </form>
    <% 
    }
    %>
    
    <div style="padding-top: 40px;">
    <div class="result-selected-class">Achievements</div>	
      <div class="placeholder-container">
      <%
      if (username == null) {
      	out.println("You are not logged in. Please log in to see your achievements");
      } else {
    	  Achievement ac = new Achievement(connection);
    	  List<AchievementItem> acItems = ac.getAchievementByUser(username);
    	  if ( acItems.size() == 0 ) {
    		  out.print("You don't have any achievements :(");
    	  } else {
    		  for(AchievementItem aItem: acItems) {
      %>
      	<div><div class="result-relation-title"><%= aItem.getAchievementName() %> (unlocked on <%= aItem.getDateAcquired() %>)</div>
      	<div class="prereqs">
        <ul>
          <div class="placeholder-text">
            <%= aItem.getDescription() %>  
          </div>
        </ul>
      </div>
      	
      	 
      	<%
      		}
      	}
      }
      	%>
      </div>
    </div> 
    
  </div>
  
</div>
</div>
</main>
</body>
</html>