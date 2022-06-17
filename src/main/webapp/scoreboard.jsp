<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,dao.Dao,java.sql.*,java.util.*"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>
	
	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	%>
	
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/topnav.css">
		<link rel="stylesheet" href="css/scoreboard.css">
		<title>Scoreboard</title>
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
		<div class="sc_box">
			<table class="sc_list">
				<thead>
					<th> Position </th>
					<th> Username </th>
					<th> Score </th>
				</thead>
				
				<% int c = 1; %>
				<it:iterate list="<%= Dao.getScoreboard() %>">
				<% 
				UserBean u = (UserBean) pageContext.getAttribute("item");
				%>
					<tr style="text-align: center;">
						<td><%= c++ %>°</td>
						<td><%= u.getUsername() %></td>
						<td><%= u.getPoints() %></td>
					</tr>
				</it:iterate>
			</table>
		</div>
	</body>
	
</html>