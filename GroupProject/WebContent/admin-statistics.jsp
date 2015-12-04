<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="include.jsp"/>
	<title>Site Statistics</title>
</head>
<body>

	<jsp:include page="header.jsp"/>
    <main>
<div>
<div id="main-browse-container-center" style="width:80%;">
  <div id="result-info-container" style="width:100%;">
      <div class="result-selected-class">Site Statistics</div>	
      <div id="browse-results-list">
<p><%= request.getAttribute("userCount") %> users.</p>
<p><%= request.getAttribute("quizzesTakenCount") %> quizzes taken.</p>
</div>
</div>
</div>
</div>
</main>
</body>
</html>