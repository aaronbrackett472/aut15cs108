<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="administration.User, java.util.ArrayList, account.*"%>
<%
	String username = (String)session.getAttribute("loggedin_user");
	if (username == null) {
		out.print("You are not logged in!");
		out.close();
	} else {
		
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Manage Users</title>
</head>
<body>

	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Manage Users</div>	
      <div id="browse-results-list">
<table class="table table-hover table-striped" style="width:100%">
	<tr>
		<th>Username</th>
		<th>Admin Status</th>		
		<th>Suspension Status</th>
		<th>Suspension End</th>
		<th>Update</th>
	</tr>
	<%! @SuppressWarnings("unchecked") %>
	<%
	ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
	for (User user : users) {
		out.println("<tr>"
				+ "<td>" + user.username + "</td>"
				+ "<td>" + (user.isAdmin ? "true" : "false") + "</td>"
				+ "<td>" + (user.suspended  ? "true" : "false") + "</td>"
				+ "<td>" + user.suspensionEnd + "</td>"
				+ "<td><form action=\""
				+ (user.suspended ? "AdminUserListServlet" : "admin-suspend-user.jsp")
				+ "\" method=\"post\">"
				+ "<input name=\"username\" type=\"hidden\" value=\"" + user.username + "\">"
				+ (user.suspended ? "<input name=\"unsuspend\" type=\"submit\" value=\"Unsuspend User\">"
						: "<input type=\"submit\" value=\"Suspend User\">")
				+ "</form>"
				+ "<form action=\"AdminUserListServlet\" method=\"post\">"
				+ "<input name=\"username\" type=\"hidden\" value=\"" + user.username + "\">"
				+ (user.isAdmin ? "<input name=\"demote\" type=\"submit\" value=\"Demote from Admin\">"
					: "<input name=\"promote\" type=\"submit\" value=\"Promote to Admin\">")
				+ "</form>"
				+ "<form action=\"AdminUserListServlet\" method=\"post\">"
				+ "<input name=\"username\" type=\"hidden\" value=\"" + user.username + "\">"
				+ "<input name=\"delete\" type=\"submit\" value=\"Delete User\"></form></td></tr>");
	}
	%>
</table>
</div>
</div>
</div>
</div>
</main>

</body>
</html>