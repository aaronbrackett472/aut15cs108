<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Administrator's Panel</title>
	<link rel="shortcut icon" href="favicon.ico">
</head>
<body>
	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:50%;">
  <div id="result-info-container" style="width:100%;">
    <div class="result-selected-class">Administrator Dashboard</div>	
      <div id="browse-results-list">
        <div class="prereqs"><div class="placeholder-text">
    	<a href="AdminAnnouncementListServlet">Make/Edit/Delete Announcements</a>
    	</div></div>
    	<div style="padding-top: 40px;"></div>
    	<div class="prereqs"><div class="placeholder-text">
		<a href="ReportedQuizListServlet">Review Reported Quizzes</a>
		</div></div>
</ul>
</div>
</div>
</div>
</div>
</main>
</body>
</html>