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
	String currentUser = (String)session.getAttribute("loggedin_user");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");		
	User us = new User(currentUser, connection);
	AccountManager account = new AccountManager(connection);
	List<String> users = account.getAllUsers();
%>
</head>
<body>
<jsp:include page="header.jsp"/>

<form id="searchform" method="post" action="finduser.jsp">
		  	<label class="search">Search:
		  	<input type="text" name="search" size=40 maxlength=255></input> </label>
</form>
<%
	if (!users.isEmpty()) {
		for (int i = 0; i < users.size(); i++) {
			%>
			<div class="floated_img">
				<p>
   				 <img src="user.png" width=100 height=100 alt="Some image" >
   				 <%
   					String user = users.get(i);
   					if (!currentUser.equals(user)) {
   						String friendLink = "userpage.jsp?who=" + user;
   					%>
   					<a href=<%=friendLink%>><%=user%></a>
   					<% 
   					}
   					if (!us.checkFriend(user)){
   						String sendFriend = "FriendRequestServlet?to=" + user + "&from=" + currentUser;
   						%>
   						<button onclick="location.href = '<%=sendFriend %>';" id="myButton" >Request Friend</button>
   						<%
   						
   					}
   					if (!currentUser.equals(user)) {
   						String sendNote = "SendNote.jsp?receiver=" + user;
   						%>
   							<button onclick="location.href = '<%=sendNote %>';" >Send Message</button>
   						<%
   					}
   				 
   				 %>
   				 </p>
			</div>
			
			<% 
		}
	}

%>

</body>
</html> 