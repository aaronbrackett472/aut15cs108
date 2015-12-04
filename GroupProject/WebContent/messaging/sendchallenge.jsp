<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, messaging.*, java.sql.Timestamp, account.*, database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	DatabaseConnection connection = (DatabaseConnection) context.getAttribute("databaseconnection");
	String username = (String) session.getAttribute("loggedin_user");
	String receiverName = (String) request.getParameter("to");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");
 	String action = request.getParameter("action");
 	String msg_id = (String)request.getParameter("msg_id");
 	int id = 0;
 	String challenge_id = (String)request.getParameter("challenge_id");
 	if (challenge_id != null) {
 		id = Integer.parseInt(challenge_id);
 	}
 	int numMsgs = manager.getNumMessages(username);
 	int numReqs = manager.numRequests(username);
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
	<h4 style="text-align:left;float:left;"><span style="font-weight:normal;"><a href="AllNoteMessages.jsp">Messages<%if (numMsgs >0) { %>(<%=numMsgs%>)<%}%></a></span> &bull; 
	    <a href="AllFriendRequests.jsp "">Friend Requests<%if (numReqs >0) { %>(<%=numReqs%>)<%}%></a> 
	</h4>
	
	<div style="float:right;padding-left:15px;padding-top:5px">
		<form method="post" action="compose.jsp">
			<input type="submit" style="margin:15px;font-size:17px;font-weight:bold" value="Compose" />
		</form>
	</div>
	
	<div id="apDiv2">
		<form id="form1" name="form1" method="post" action="MessageServlet">
			<input type="hidden" name="type" value="challenge"></input> <label style="text-align:left;float:left;"
				class="message"><b>From:</b></label> <input class="messagefield"
				type="text" name="fromname" id="from" value="<%=username%>" size=30 />
			<label class="message"><b>To:</b></label> <input class="messagefield"
				type="text" name="toname" id="toname" value="<%=receiverName%>"
				size=30 /> <input type="hidden" name="to" value="<%=receiverName%>"></input>
			<p>
				<label class="message"><b>Optional Message</b></label>
			</p>
			<p>
				<textarea class="messagefield" name="body" id="body" cols="90"
					rows="22"></textarea>
			</p>
			<input class="send" type="submit" name="action" id="action"
				value="challenge" /> <input class="send" type="button"
				name="discard" id="discard" value="Discard"
				onclick="discardMessage();" />
			<input type="hidden" name="id" value=<%=id%>>
		</form>
	</div>
</body>
</html>