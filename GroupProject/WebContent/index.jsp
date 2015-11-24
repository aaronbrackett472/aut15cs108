<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/main.css">
<title>Cardinal Quiz</title>
</head>
<body>
	<header id="site-header">
		<div id="site-banner">
			<div id="classpath-title">CardinalQuiz</div>
			<div id="login-container">
       			Welcome Back, pat!
       		</div>
       	</div>
      
      <nav>
      	<a class="nav-item" href="/GroupProject/index.jsp">Home</a>
        <a class="nav-item" href="/GroupProject/browse.jsp">Browse Quizzes</a>
        <a class="nav-item" href="/GroupProject/notifications.jsp">Notifications</a>
        <a class="nav-item" href="/GroupProject/friends.jsp">Friends</a>
       <form id="login-form" method="POST" action="/logout/" class="ng-pristine ng-valid">
               <button type="submit" class="auth-btn">Logout</button>
              <input type="hidden" name="csrfmiddlewaretoken" value="nBuwSaDva0fVGB5YLyVgaPsX9cRbUmZV">
       </form>
       </nav>

    </header>
    <main>
    <div>
    	<div id="main-browse-container">
  <div id="browse-container">
  
    <div id="browse-results-container">
    <div class="result-selected-class">Latest Quizzes</div>	
      <input ng-change="searchChange()" ng-model="query" id="search-box" placeholder="Search by keywords or class code (i.e. CS106A)" class="ng-pristine ng-untouched ng-valid">
      <ul id="browse-results-list">
        <!-- ngRepeat: class in classes | filter:query | orderBy:'code' --><li class="browse-result ng-scope" ng-repeat="class in classes | filter:query | orderBy:'code'" ng-click="toggleResult($event)">
          <div class="title">Math 51 Midterm 1 Quiz</div>
          <div ng-show="class.description" class="description ng-binding">Created by Pat. Taken 25 times</div>
        </li><!-- end ngRepeat: class in classes | filter:query | orderBy:'code' --><li class="browse-result ng-scope" ng-repeat="class in classes | filter:query | orderBy:'code'" ng-click="toggleResult($event)">
          <div class="title ng-binding">Physics 43 Final Quiz</div>
          <div ng-show="class.description" class="description ng-binding">Created by Pat. Taken 37 times</div>
        </li><!-- end ngRepeat: class in classes | filter:query | orderBy:'code' --><li class="browse-result ng-scope" ng-repeat="class in classes | filter:query | orderBy:'code'" ng-click="toggleResult($event)">
          <div class="title ng-binding">CS103 Midterm Quiz</div>
          <div ng-show="class.description" class="description ng-binding">Created by Pat. Taken 88 times</div>
        </li><!-- end ngRepeat: class in classes | filter:query | orderBy:'code' --><li class="browse-result ng-scope" ng-repeat="class in classes | filter:query | orderBy:'code'" ng-click="toggleResult($event)">
          <div class="title ng-binding">CS107 Final Quiz</div>
          <div ng-show="class.description" class="description ng-binding">Created by Pat. Taken 22 times</div>
        </li><!-- end ngRepeat: class in classes | filter:query | orderBy:'code' -->
      </ul>
    </div>
  </div>
  <div id="result-info-container">
    <div class="result-selected-class">Announcements</div>
    <div>
      <div class="result-relation-title">ClassQuiz is Now Live! (12/4)</div>
      <div class="prereqs">
        <ul>
          <div class="placeholder-text">
            After weeks of hard work, CardinalQuiz is now live. We hope everyone enjoys it!  
          </div>
        </ul>
      </div>

      <div class="result-relation-title">Updated Site Rules (11/23)</div>
      <div class="leads-to-classes">
        <ul>
          <div class="placeholder-text">
            Please don't post any NSFW quiz on CardinalQuiz!
          </div>
        </ul>
      </div>
    </div>
    
    <div class="ng-hide" style="padding-top: 40px;">
    <div class="result-selected-class">Previous Quizzes</div>	
      <div class="placeholder-container">
      	You haven't done any quiz. Perhaps you want to change that?
      </div>
    </div>
    <div class="add-class-container">
      <button ng-click="addClassToTree()">Take a Random Quiz</button>
    </div>
    
    <div class="ng-hide" style="padding-top: 40px;">
    <div class="result-selected-class">Achievements</div>	
      <div class="placeholder-container">
      	You don't have any achievements
      </div>
    </div> 
    
  </div>
  
</div>
    </div>
    </main>
</body>
</html>