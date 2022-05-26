<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,java.sql.*,java.util.*,dao.Dao"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	%>

	<head>
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/profile.css">
		<title>Profile</title>
	</head>
	
	<body>
		<div class="main">
			<div class="header">
				<h3><%=user.getUsername()%></h3>
				<p style="font-size:13px;">signed in: <%= user.getDate() %></p>
			</div>
			<hr>
			<div class="stats">
				<div class="points">
					<h3>Statistics</h3>
					<p>Binary Exploitation : <%= Dao.getUserResolvedCat(user.getUsername(), "Binary Exploitation") %></p>
					<p>Reverse Engineering : <%= Dao.getUserResolvedCat(user.getUsername(), "Reverse Engineering") %></p>
					<p>Web Exploitation : <%= Dao.getUserResolvedCat(user.getUsername(), "Web Exploitation") %></p>
					<p>Forensic : <%= Dao.getUserResolvedCat(user.getUsername(), "Forensic") %></p>
					<p><b>Total points : <%= Dao.getUserPoints(user.getUsername()) %></b></p>
				</div>
			
				<div class="misc">
					<h3>Other Statistics</h3>
					<p>Number of Attempted challenges: <%= Dao.getUserAttempts(user.getUsername())%> </p>
					<p>Number of resolved challenges: <%= Dao.getUserResolved(user.getUsername()) %></p>
					<p>Number of <span class="highlight">"first bloods"</span> : <%= Dao.getFirstBloods(user.getUsername()) %></p>
				</div>
			
			</div>
		
		</div>
	</body>

</html>