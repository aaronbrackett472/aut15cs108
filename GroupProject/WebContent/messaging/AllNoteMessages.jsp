<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, java.sql.Timestamp, messaging.*, account.*, database.*"%>

<!-- This page lists all the note messages for a particular user
	One way to use this page would be to create a link in the user's home page 
	Each note message has a link that directs to ViewNote page
	Assumes that the user has already logged in, and that the "user" attribute is set
	 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	String user = (String) session.getAttribute("loggedin_user");
	List<Message> messages = null;
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");		
	messages = manager.getUserMessages(user);
	int numMsgs = messages.size();
	int numReqs = manager.numRequests(user);
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat();
%>
<jsp:include page="cssfile.jsp" />

<script type="text/javascript">
	function discardMessage() {
		document.getElementById('subject').value = "";
		document.getElementById('body').value = "";
		var div = document.getElementById('apDiv3');
		div.removeChild(document.getElementById('form1'));
		document.getElementById('heading').innerHTML = "Request Discarded";
		var label = document.createElement('label');
		label.className = 'message';
		label.innerHTML = "<br></br>Your request has been discarded.";
		div.appendChild(label);
	}
</script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<h4 style="text-align:left;float:left;"><a href="AllNoteMessages.jsp">Messages <%if (numMsgs >0) { %>(<%=numMsgs%>)<%}%></a> &bull; 
	    <span style="font-weight:normal;"><a href="AllFriendRequests.jsp">Friend Requests <%if (numReqs >0) { %>(<%=numReqs%>)<%}%></a></span>	    
	</h4>
	<div style="float:right;padding-left:15px;padding-top:5px">
		<form method="post" action="compose.jsp">
			<input type="submit" style="margin:15px;font-size:17px;font-weight:bold" value="Compose" />
		</form>
	</div>
	<hr style="clear:both;"/>
	<%
	
 	if (messages.isEmpty()) {
		%><center><h4> No new mail! </h4></center><% 
	} else {
	%>
	<form method="post" action="MessageServlet">
	<fieldset style="border:0 none;">
	
	<table cellpadding="5" cellspacing="5" border="0" width="100%">
		<tbody>
			<%	
				for(int i = 0; i < messages.size(); i++) {
					Message m = messages.get(i);
					String subject = m.getSubject();
					String sender = m.getSenderName();
					int id = m.getId();
					Timestamp time = m.getDateSent();
					int length = Math.min(50, m.getMessageBody().length());
					String snippet = m.getMessageBody().substring(0, length);
					String display = subject + ":" + snippet;
				%>
				<tr>
					<td align="left" width="7%" >
					<input type="checkbox" id="checkbox<%=i%>" name="check" value="<%=id%>"></td>
					<%if (m.isRead()) { %>
					<td align="left" width="22%"><a href="user/profile.jsp?user=<%=sender %>"><font color="#000000"><b><%=sender%></b></font></a></td>
					<td align="left" width="49%"><a href="ViewNote.jsp?msg_id=<%=id%>"><font color="#000000"><b><%=snippet%></b></font></a></td>
					<td align="right" width="20%"><b><%=sdf.format(time)%></b></td>
					<%} else {%>
						<td align="left" width="22%"><a href="user/profile.jsp?user=<%=sender %>"><font color="#000000"><%=sender%></font></a></td>		
						<td align="left" width="49%"><a href="ViewNote.jsp?msg_id=<%=id%>"><font color="#000000"><%=snippet%></font></a></td>
						<td align="right" width="20%"><%=sdf.format(time)%></td>
					<%}%>
				</tr>
			<% } %>
		</tbody>
	</table>
	</fieldset>
			<hr>
					<div> Select:&nbsp;&nbsp;<a href="javascript:;" onclick="checkAll(this)" >All</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;" id="checknone">None</a>
					<span style="float:right"><b>Selected Messages :&nbsp;&nbsp;&nbsp;</b>
					<select name="update_type">
						<option value="delete">Delete Messages</option>
						<option value="read">Mark as Read</option>
						<option value="unread">Mark as Unread</option>
					</select>
						<input type="hidden" name="quiz_id" value="s">
						<input type="submit" name="inbox_update" value="Update"></span>
					</div>
		<%		
			}
		%>
	</form>
	<script type="text/javascript">
		function checkAll(link) {
			var inputs = document.getElementsByTagName('input');
			for (var i=0; i < inputs.length; i++) {
				if (inputs[i].type == 'checkbox') {
					inputs[i].checked = true;
				}
			}
		}
		$(function () {
			$('#checknone').click(function () {
    			$('fieldset').find(':checkbox').attr('checked', false);
    			return true;
			});
		});
	</script>
</body>
</html>