<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="database.*, account.*, messaging.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	ServletContext context = request.getServletContext();
	String user = (String) session.getAttribute("loggedin_user");
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");
	int numReqs = manager.numRequests(user);
	int numMsgs = manager.getNumMessages(user);
	String receiver = "";
	String type = "";
 	User currentUser = new User(user, connection);
 	Set<String>list = currentUser.getAllFriends();
 	List<String>friends = new ArrayList<String>(list);
	
%>
</head>

<jsp:include page="cssfile.jsp" />

<body>
	<jsp:include page="header.jsp" />
	<h4 style="text-align: left;">
		<span style="font-weight: normal;"><a
			href="AllNoteMessages.jsp">Messages<%
			if (numMsgs > 0) {
		%>(<%=numMsgs%>)<%
			}
		%></a></span>
		&bull; <a href="AllFriendRequests.jsp "">Friend Requests<%
 	if (numReqs > 0) {
 %>(<%=numReqs%>)<%
 	}
 %></a>
	</h4>
	<form id="form1" name="form1" method="post" action="MessageServlet">
	
	<h4 style="text-align: left;">
	Please choose the type of message you would like to send:
	<select name="type">
		<option value="note">Note Message</option>
		<option value="challenge">Challenge Message</option>
		<option value="friendrequest">Friend Request</option>
 	</select> 
	</h4>
	
	<h4 style="text-align: left;">
	Please choose recipient: 
	<select name="receiver" id="friend_dropdown">
		<%  for(int i = 0; i < friends.size(); i++) {
           	String option = (String)friends.get(i);
   		%>
   		<option value="<%= option %>"><%= option %></option>
  		 <% } %>
	</select>
	<script>
		var dropDown = document.getElementById('friend_dropdown');
		dropDown.onchange = function() {
			var info = dropDown.value;
			dropDown
		};
	</script>
	
	</h4>
	<p>
	Click next to continue
	</p>
	<input class="send" type="submit" name="action" id="action"
				value="compose"/> 
	</form>

</body>
</html