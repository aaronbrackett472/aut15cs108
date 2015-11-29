<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, messaging.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%

	HttpSession ses = request.getSession();
	User us = new User("alfonce");
/* 	User us = (User) ses.getAttribute("user");
 */	String receiverName = (String) request.getParameter("to");
	String user = us.getUserName();
	Message m = (Message) ses.getAttribute("message");
	String action = request.getParameter("action");
	String title = "New Message";
	String subject = "";
	if ( m!= null) {
		subject = "Re:" +m.getSubject();	
	}
%>
<title><%=title%></title>
<style type="text/css">
#apDiv1 {
	position: absolute;
	width: 1250px;
	height: 100px;
	z-index: 1;
	left: 0px;
	top: 0px;
	background-color: #048;
}

#apDiv2 {
	position: absolute;
	width: 815px;
	height: 800px;
	z-index: 3;
	left: 0px;
	top: 100px;
	background-color: #E0E0E0;
	color: #99F;
}

.heading {
	font-family: "Comic Sans MS", cursive;
	font-size: 28px;
	font-weight: 100;
	position: relative;
	left: 30px;
	top: 20px;
	color: white;
}

.userlinks {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	position: relative;
	left: 20px;
	top: 20px;
}

.message {
	color: black;
	font-weight: bold;
	position: relative;
	left: 20px;
	top: 20px;
}

.messagefield {
	position: relative;
	left: 20px;
	top: 20px;
}

.send {
	position: relative;
	left: 20px;
	top: 20px;
}
</style>


<script type="text/javascript">
	function discardMessage() {
		document.getElementById('subject').value = "";
		document.getElementById('body').value = "";
		var div = document.getElementById('apDiv3');
		div.removeChild(document.getElementById('form1'));
		document.getElementById('heading').innerHTML = "Message Discarded";
		var label = document.createElement('label');
		label.className = 'message';
		label.innerHTML = "<br></br>You message has been discarded.";
		div.appendChild(label);
	}
</script>
</head>
<body>
	<div id="apDiv1">
		<label class="heading" id="heading"><strong><%=title%></strong></label>
	</div>
	<div id="apDiv2">
		<form id="form1" name="form1" method="post" action="MessageServlet">
			<input type="hidden" name="type" value="note"></input> <label
				class="message"><b>From:</b></label> <input class="messagefield"
				type="text" name="fromname" id="from" value="<%=user%>" size=30 />
			<label class="message"><b>To:</b></label> <input class="messagefield"
				type="text" name="toname" id="toname" value="<%=receiverName%>"
				size=30 /> <input type="hidden" name="to" value="<%=receiverName%>"></input>
			<p>
				<label class="message"><b>Subject</b></label>
			</p>
			<p>
				<input class="messagefield" type="text" name="subject" id="subject"
					value="<%=subject%>" size=100 />
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