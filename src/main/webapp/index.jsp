<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,dao.Dao,java.sql.*,java.util.*,ctf.CtfBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	session.setAttribute("connection", Dao.connect());
	%>

	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/home.css">

		<title>Home</title>
	</head>

	<body>
		<div class=main>
			<div class="topnav">
				<a class="active" href="index.jsp">Home</a>
				<% if (user != null) { %>
				<a href="#profile">Profile</a>
				<%} else {%>
				<a href="login.html">Login</a>
				<a href="register.html">Register</a>
				<%}%>
	
	  			<div class="search-container">
	    		<form action="/Search">
	     			<input type="text" placeholder="Search.." name="search">
	      			<button type="submit"><i class="fa fa-search"></i></button>
	    		</form>
	  			</div>
			</div>
		
			<table class="ctf_list">
				<thead>
					<th> Name </th>
					<th> Author </th>
					<th> Category </th>
					<th> Difficulty </th>
					<th> Date </th>
				</thead>
			
			<it:iterate list="<%= Dao.getCTF((Connection)session.getAttribute(\"connection\")) %>">	
				<tr style="text-align: center;" onclick="window.location='Challenge?id=<%=pageContext.getAttribute("id")%>';">
					<td><%= pageContext.getAttribute("title") %></td>
					<td><%= pageContext.getAttribute("creator") %></td>
					<td><%= pageContext.getAttribute("category") %></td>
					<td><%= pageContext.getAttribute("difficulty") %></td>
					<td><%= pageContext.getAttribute("date") %></td>
					</a>
				</tr>
				
				<!--
				<a class="description"><%= pageContext.getAttribute("description") %></a>
				<div class="hints">Hints</div>
				-->
			</it:iterate>
			
			</table>	
	
		</div>

	</body>

</html>