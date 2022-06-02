<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,java.sql.*,java.util.*,dao.Dao"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	String username = (String) request.getParameter("username");
	String date;
	
	if (username != null) {
		date = Dao.getUserDate(username);
	}
	else {
		username = user.getUsername();
		date = user.getDate();
	}
	
	String[] dateonly = date.split(" ");
	date = dateonly[0];
	
	%>

	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/profile.css">
		<link rel="stylesheet" href="css/topnav.css">
		<title>Profile</title>
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
		
		<div class="main">
			<div class="header">
				<h3><%= username %></h3>
				<p style="font-size:13px;">signed in: <%= date %></p>
			</div>
			<hr>
			<div class="stats">
				<div class="points">
					<h3>Statistics</h3>
					<p>Binary Exploitation : <%= Dao.getUserResolvedCat(username, "Binary Exploitation") %></p>
					<p>Reverse Engineering : <%= Dao.getUserResolvedCat(username, "Reverse Engineering") %></p>
					<p>Web Exploitation : <%= Dao.getUserResolvedCat(username, "Web Exploitation") %></p>
					<p>Forensic : <%= Dao.getUserResolvedCat(username, "Forensic") %></p>
					<p><b>Total points : <%= Dao.getUserPoints(username) %></b></p>
				</div>
			
				<div class="misc">
					<h3>Other Statistics</h3>
					<p>Number of attempted challenges: <%= Dao.getUserAttempts(username)%> </p>
					<p>Number of resolved challenges: <%= Dao.getUserResolved(username) %></p>
					<p>Number of <span class="highlight">"first bloods"</span> : <%= Dao.getFirstBloods(username) %></p>
				</div>
			
			</div>
		
		</div>
	</body>

</html>