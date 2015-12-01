<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, messaging.*, java.sql.Timestamp, account.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	HttpSession ses = request.getSession();
	User us = new User("nzioka");

	/*  	User us = (User)ses.getAttribute("user");
	 */ String receiverName = (String) request.getParameter("to");
	String username = us.getUserName();
	Message m = (Message) ses.getAttribute("message");
	String action = request.getParameter("action");
	String title = "New Message";
	String subject = "";
	if (m != null) {
		subject = "Re:" + m.getSubject();
	}
	List<User> friends = new ArrayList<User>();
	for (int i = 0; i < 10; i++) {
		friends.add(us);
	}
%>
<jsp:include page="cssfile.jsp" />
<title><%=title%></title>
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
	<jsp:include page="copyheader.jsp" />
	<div id="apDiv1">
		<label class="heading" id="heading"><strong><%=title%></strong></label>
	</div>
	<jsp:include page="notifications.jsp" />
	<div id="apDiv2">
		<form id="form1" name="form1" method="post" action="MessageServlet">
			<input type="hidden" name="type" value="note"></input> <label
				class="message"><b>From:</b></label> <input class="messagefield"
				type="text" name="fromname" id="from" value="<%=username%>" size=30 />
			<label class="message"><b>To:</b></label> <input class="messagefield"
				type="text" name="toname" id="toname" value="<%=receiverName%>"
				size=30 /> <input type="hidden" name="to" value="<%=receiverName%>"></input>
			<p>
				<label class="message"><b>Subject</b></label>
			</p>
			<p>
				<input class="messagefield" type="text" name="subject" id="subject"
					value="<%=subject%>" size=75 />
			</p>
			<p>
				<label class="message"><b>Message</b></label>
			</p>
			<p>
				<textarea class="messagefield" name="body" id="body" cols="90"
					rows="22"></textarea>
			</p>
			<input class="send" type="submit" name="action" id="action"
				value="Send" /> <input class="send" type="submit" name="action"
				id="action" value="Save" /> <input class="send" type="button"
				name="discard" id="discard" value="Discard"
				onclick="discardMessage();" />
		</form>
	</div>

</body>
</html>