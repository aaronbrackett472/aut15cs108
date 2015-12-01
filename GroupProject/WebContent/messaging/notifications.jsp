<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "account.*, messaging.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
HttpSession ses = request.getSession();
/* User us = (User) ses.getAttribute("user");
 */
 User us = new User("nzioka");
int[] messages = {0, 0, 0}; //notes, friendrequests, challenge
ServletContext ctx = getServletContext();
MessageManager manager = (MessageManager) ctx.getAttribute("messageManager");
messages[0] = manager.numMessages(us, "note");
messages[1] = manager.numMessages(us, "friendrequest");
messages[2] = manager.numMessages(us, "challenge");
String allNotes = "AllNoteMessages.jsp";
String friendrequests = "AllFriendRequests.jsp";
String challenges = "AllChallengeMessages.jsp";
String sentLink = "AllSentMessages.jsp";
String draftsLink = "AllDraftMessages.jsp";
String accountLink = "userhome.jsp";
String friendsLink = "friendlist.jsp";

%>

<div id="notifications">
		<br /> <br /> <br /> 
		<label class="userlinks">
			<a class="link" href=<%=allNotes%>>Inbox (<%=messages[0]%>)</a><br /> <br /> 
			<a class="link" href=<%=friendrequests%>>Friend requests (<%=messages[1]%>)</a><br /> <br />
			 <a class="link" href=<%=challenges%>>Challenges (<%=messages[2]%>)</a><br />
			<br /> <a class="link" href=<%=sentLink%>>Sent Messages</a><br /> <br />
			<a class="link" href=<%=draftsLink%>>Drafts</a><br /> <br /> <br />
			<br /> <br /> <a class="link" href=<%=friendsLink%>>Friends</a><br />
			<br /> <a class="link" href=<%=accountLink%>>My account</a><br /> <br />
			<a class="link" href="sitehome.jsp?action=logout">Sign out</a><br />
			<br /> </label>

	</div>