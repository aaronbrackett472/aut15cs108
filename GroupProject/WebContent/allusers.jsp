<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, account.*, database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<%
	//display all users (can select from here to send friend request/message etc))
	String pagen = request.getParameter("page");
	int pagenum = 1;
	if (pagen != null) {
		pagenum = Integer.parseInt(pagen);
	}
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String userName = (String)session.getAttribute("loggedin_user");

	User us = new User(userName, connection);
	List<User> users = new ArrayList<User>();
	//need a way to get the all user accounts
	/* 	AccountManager manager = (AccountManager)request.getAttribute("user");
	 */ String title = "welcome " + userName;
	User us2 = new User("nyeti");
	User us3 = new User("nzioka");
	User us4 = new User("kevo");
	users.add(us2);
	users.add(us3);
	users.add(us4);
	
	for (int i = 0; i < 50; i++) {
		users.add(us2);
	}
	int numUsers = users.size();
%>
<link rel="stylesheet" type="text/css" href="messaging.css">
<title><%=title%></title>
</head>
<body>
	<jsp:include page="header.jsp"/>

	<div id="apDiv1">
		<label class="heading"><strong><%=title%></strong></label>
		<form id="searchform" method="post" action="searchresults.jsp?page=1">
			<label class="search">Search: <input type="text"
				name="search" size=40 maxlength=255></input>
			</label>
		</form>
	</div>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<div id="show">
		<%
			if (userName != null) {
				String accountLink = "userhome.jsp";
		%>
		<label class="userlinks"><a href=<%=accountLink%>>My
				account</a><br /> <br /> <a href="sitehome.jsp?action=logout">Sign
				out</a></label><br /> <br />
		<%
			}
		%>
		<label class="userlinks"><b><a href="showquizzes.jsp">View
					Quizzes</a></b></label>
	</div>
  
	<div id="show2">
		<%
			String nextLink = "allusers.jsp?page=" + (pagenum + 1);
			String prevLink = "allusers.jsp?page=" + (pagenum - 1);

			int usersPerPage = 10;
			int numDisplayed = pagenum * usersPerPage;

			if (pagenum > 1) {
		%><label class="page"><a href=<%=prevLink%>>Previous</a></label>
		<%
			}
			if (numDisplayed < numUsers) {
		%><label class="page"><a href=<%=nextLink%>>Next</a></label>
		<%
			}
		%><br></br>
		<%
			int userEnd = 0;
			if (users != null) {
				int userStart = (pagenum - 1) * usersPerPage;
				userEnd = (numDisplayed > numUsers) ? userStart + Math.abs(numUsers - userStart) / 2 + 1
						: userStart + 5;
				int limit = Math.min(numUsers, userEnd);

				for (int i = userStart; i < limit; i++) {
					User usr = users.get(i);
					String friend = usr.getUserName();
					if (!userName.equals(friend)) {
						String friendLink = "userpage.jsp?who=" + friend;
						if (userName == null)
							friendLink = "sitehome.html";

						String imageLink = usr.getImageFile();
						if (imageLink == null)
							imageLink = "user.png";
		%><label class="user"><a href=<%=friendLink%>><%=friend%></a></label><br />
		<br /> <img class="image" src=<%=imageLink%> width=81 height=91
			align="middle"></img><br /> <br />
		<%
			if (!us.checkFriend(friend) && us != null) {
							String addFriend = "SendFriendRequest.jsp?to=" + friend;
		%>
		<label class="user"><a class="link" href=<%=addFriend%>>Add
				<%=friend%> as a friend
		</a></label><br /> <br />
		<%
			}
		%>
		<br></br>
		<%
			}
				}
			}

			if (pagenum > 1) {
		%>
		<br></br> <label class+="page"><a href=<%=prevLink%>>Previous</a></label>
		<%
			}
			if (numDisplayed < numUsers) {
		%><br></br> <label class="page"><a href=<%=nextLink%>>Next</a></label>
		<%
			}%>
	</div>
	 <div id="apDiv4">
		<%
			if (users != null) {
				int userStart = userEnd;
				userEnd = numDisplayed;
				int limit = Math.min(numUsers, userEnd);

				for (int i = userStart; i < limit; i++) {
					User usr = users.get(i);
					String friendUsername = usr.getUserName();
					if (!userName.equals(friendUsername)) {
						String friendLink = "userpage.jsp?who=" + friendUsername;
						if (us == null)
							friendLink = "sitehome.html";
						String imageLink = usr.getImageFile();
						if (imageLink == null)
							imageLink = "user.png";
		%><br /> <br /> <label class="user"><a href=<%=friendLink%>><%=friendUsername%></a></label><br></br>
		<img class="image" src=<%=imageLink%> width=81 height=91
			align="middle"></img><br></br> <br />
		<%
			if (!us.checkFriend(friendUsername) && us != null) {
							String addFriend = "SendFriendRequest.jsp?to=" + friendUsername;
		%>
		<label class="user"><a class="link" href=<%=addFriend%>>Add
				<%=friendUsername%> as a friend
		</a></label><br /> <br />
		<%
			}
		%>
		<br></br>
		<%
			}
				}
			}
		%>

	</div>  

</body>
</html>