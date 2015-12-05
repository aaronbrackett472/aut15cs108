<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, account.*, database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="cssfile.jsp" />
<%
	//display all users (can select from here to send friend request/message etc))
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String currentUser = (String)session.getAttribute("loggedin_user");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");		
	User us = new User(currentUser, connection);
	AccountManager account = new AccountManager(connection);
	String searchString = request.getParameter("search");
	
	List<String> users = new ArrayList<String>();
	if (searchString !=null) {
		users = account.findSearchedUsers(searchString);
	}
%>
</head>
<body>
	<jsp:include page="header.jsp"/> 	
	<form id="searchform" method="post" action="finduser.jsp">
		  	<label class="search">Search:
		  	<input type="text" name="search" size=40 maxlength=255></input> </label>
	</form>
	<div >
		<%
			if (!users.isEmpty()) {
				for (int i = 0; i < users.size(); i++) {
					String username = users.get(i);
					if (!currentUser.equals(username)) {
						String friendLink = "userpage.jsp?who = " +username;
						%><label class="user"><a href=<%=friendLink%>><%=username%></a></label><br />
						<%
					}
					if (!us.checkFriend(username) && us != null) {
						String addFriend = "FriendRequestServlet?to=" + username + "&from=" + currentUser;
						%>
						<label class="user"><a class="link" href=<%=addFriend%>>Add
								<%=username%> as a friend
						</a></label><br /> <br />
						<%
					}
				}
			} else {
				%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<h1 style="text-align:center;color:#BF5555"> No such name found!</h1>
				<%
			}	
		%>
	
	</div>
	
	
	
</body>
</html>