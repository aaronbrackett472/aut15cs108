<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="messaging.*, java.util.*, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	HttpSession ses = request.getSession();
/* 	User us = (User) ses.getAttribute("user");
 */	User us = new User("alfonce");
	String receiverName = (String) request.getParameter("to");
	String user = us.getUserName();
	Message m = (Message) ses.getAttribute("message");
	String action = request.getParameter("action");

	String title = "Message Status";
 	String type = request.getParameter("type");
 	String subject = "";
	String body = "";
	String time = "";
	
	 if (m != null) {
		subject = m.getSubject();
		body = m.getMessageBody();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		time = sdf.format(m.getDateSent());
	} 
	String message = "";
	if (action.equals("Save")) {
		message = "Your message has been saved.";
	} else if (action.equals("Discard")) {
		message = "Your message has been discarded.";
	} else {
		if (type.equals("note")) {
			message = "Your message has been sent.";
		} else if (type.equals("challenge")) {
			message = "Your challenge has been sent.";
		} else { //type == friend
			message = "Your request has been sent to " + receiverName + " for approval.";
		}
	}
	ses.removeAttribute("message");
	ses.removeAttribute("sent");
	ses.removeAttribute("draft");
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
	height: 596px;
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

.message {
	color: black;
	font-weight: bold;
	position: relative;
	left: 20px;
	top: 20px;
}

.link {
	text-decoration: none;
}

.status {
	color: blue;
	font-weight: bold;
	position: relative;
	left: 20px;
	top: 20px;
}

.body {
	color: black;
	position: relative;
	left: 20px;
	top: 20px;
}

.quizlinks {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	color: black;
	position: relative;
	left: 20px;
	top: 20px;
}
</style>
<script type="text/javascript">
	function discardMessage() {
		document.getElementById('subject').value = "";
		document.getElementById('message').value = "";
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
		<label class="status"><%=message%></label><br /> <br />
		<%
			if(m != null &&  !action.equals("Discard")) {
		%>
		<label class="message">On <%=time%> you wrote:
		</label><br /> <br /> <br /> <label class="message">Subject: <%=subject%></label><br />
		<br /> <label class="message">Message:</label> <br /> <br /> <label
			class="body"><%=body%></label><br /> <br />
		<%
			}
		%>
	</div>

</body>
</html>