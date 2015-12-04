<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="container">
	<div class="nav-bar">
		<div class="profile-nav">Browse Profiles</div>
		<div class="quiz-nav">Browse Quizzes</div>
		<div class="create-nav">Create a Quiz!</div>
		<div class="search">
			<form action="UserHomePage.jsp">
				<input type="text" id="searchText" name="userName">
				<input type="submit" value="Search Friends">
			</form>
			<script type="text/javascript">
				$("#searchText").keyup(function() {
					var searchTerm = $(this).val();
					if (searchTerm == "") {
						$("#searchResults").hide();
					} else {
						$("#searchResults").show();
						$.get("SearchResponse.jsp", {searchTerm: searchTerm}, function(data) {
							$("#searchResults").html(data);
						});
					}
				});
			</script>
		</div>
		<div id='searchResults'>	
		</div>
		<% String viewer = "nzioka"; %>
		<div class="logout">Logout</div>
		<div class="homepage-nav">Your Profile</div>
	</div>
	<p> Send note to <%=request.getParameter("userName")%></p>
	<form action="SendMessageServlet" method="post">
	<textarea name="message" cols="30" rows="7">Enter your message here...</textarea><br>
	<input name="type" type="hidden" value="note">
	<input name="toUser" type="hidden" value=<%=request.getParameter("userName")%>>
	<input type="submit" value="Send">
	</form>
</div>
<script>
$(function() {
	
	$('.profile-nav').click(function() {
    	window.location = 'BrowseProfiles.jsp';
    });
    
    $('.quiz-nav').click(function() {
    	window.location = 'BrowseQuizzes.jsp';
    });
    
    $('.create-nav').click(function() {
    	window.location = 'QuizCreation.jsp';
    });
    
    $('.homepage-nav').click(function() {
    	window.location = 'UserHomePage.jsp?userName=<%= viewer %>';
    });
    
    $('.logout').click(function() {
    	$.ajax({
            type:'post',
            url:"LogoutServlet"   
		});
    	window.location = 'Login.jsp';
    	
    });
});
</script>
</body>
</html>