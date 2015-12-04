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
	String pagen = request.getParameter("page");
	int pagenum = 1;
	if (pagen != null) {
		pagenum = Integer.parseInt(pagen);
	}
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String userName = (String)session.getAttribute("loggedin_user");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");		
	User us = new User(userName, connection);
	AccountManager account = new AccountManager(connection);
	List<String> users = account.getAllUsers();
	String user = (String) session.getAttribute("loggedin_user");
	int numUsers = users.size();
	int numReqs = manager.numRequests(user);
	int numMsgs = manager.getNumMessages(user);
%>
</head>
<body>
	<jsp:include page="header.jsp"/> 	
	<div>
	<h4 style="text-align:left;float:left;"><a href="AllNoteMessages.jsp">Messages <%if (numMsgs >0) { %>(<%=numMsgs%>)<%}%></a> &bull; 
	    <span style="font-weight:normal;"><a href="AllFriendRequests.jsp">Friend Requests <%if (numReqs >0) { %>(<%=numReqs%>)<%}%></a></span>	    
	</h4>
	</div>
	<form id="searchform" method="post" action="finduser.jsp">
		  	<label class="search">Search:
		  	<input type="text" name="search" size=40 maxlength=255></input> </label>
	</form>
	<div style="float:right;padding-left:15px;padding-top:5px">
		<form method="post" action="compose.jsp">
			<input type="submit" style="margin:15px;font-size:17px;font-weight:bold" value="Compose" />
		</form>
	</div>
	<div id="users">
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
					String friend = users.get(i);
					if (!userName.equals(friend)) {
						String friendLink = "userpage.jsp?who=" + friend;
		%><label class="user"><a href=<%=friendLink%>><%=friend%></a></label><br />
		<%
			if (!us.checkFriend(friend) && us != null) {
							String addFriend = "FriendRequestServlet?to=" + friend + "&from=" + userName;
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
		<br></br> <label class="page"><a href=<%=prevLink%>>Previous</a></label>
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

					String friendUsername = users.get(i);
					if (!userName.equals(friendUsername)) {
						String friendLink = "userpage.jsp?who=" + friendUsername;
						if (us == null)
							friendLink = "sitehome.html";
			
		%><br /> <br /> <label class="user"><a href=<%=friendLink%>><%=friendUsername%></a></label><br></br>
		<%
			if (!us.checkFriend(friendUsername) && us != null) {
							String addFriend = "FriendRequestServlet?to=" + friendUsername + "&from=" + userName;
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
		
		String sendNote = "Send"

	</div>  

</body>
</html>