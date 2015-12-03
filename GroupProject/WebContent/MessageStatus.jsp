<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="messaging.*, java.util.*, java.text.SimpleDateFormat, java.sql.Timestamp, account.*, database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
	ServletContext context = request.getServletContext();
	String user = (String) session.getAttribute("loggedin_user");
	String receiverName = (String) request.getParameter("to");
	Message m = (Message) ses.getAttribute("message");
	String action = request.getParameter("action");
	MessageManager manager = (MessageManager) context.getAttribute("messageManager");
	int numReqs = manager.numRequests(user);
	int numMsgs = manager.getNumMessages(user);
	String message = "";
	if (request.getAttribute("successMessage") != null) {
		message  = (String) request.getAttribute("successMessage");
	} else if (request.getAttribute("failureMessage") != null) {
		message  = (String) request.getAttribute("failureMessage");
	} else if (request.getAttribute("delete") != null) {
		message  = (String) request.getAttribute("delete");
	} else if (request.getAttribute("read") != null) {
		message  = (String) request.getAttribute("read");
	} else if (request.getAttribute("unread") != null) {
		message  = (String) request.getAttribute("unread");
	} else if (request.getAttribute("send") != null) {
		message = (String)request.getAttribute("send");
	}
	ses.removeAttribute("successMessage");
	ses.removeAttribute("failureMessage");
	ses.removeAttribute("unread");
	ses.removeAttribute("read");
	ses.removeAttribute("delete");
	ses.removeAttribute("request");
	ses.removeAttribute("send");
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
	
	<div id="apDiv2">
		<label class="status"><%=message%></label><br /> <br />
	</div>
</body>
</html>