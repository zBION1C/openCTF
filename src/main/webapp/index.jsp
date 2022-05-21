<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,dao.Dao,java.sql.*,java.util.*,ctf.CtfBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
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
			
			<it:iterate list="<%= Dao.getCTF() %>">
				<% CtfBean ctf = (CtfBean) pageContext.getAttribute("item"); %>
				<tr style="text-align: center;" onclick="window.location='Challenge?id=<%=ctf.getId()%>';">
					<td><%= ctf.getTitle() %></td>
					<td><%= ctf.getCreator() %></td>
					<td><%= ctf.getCategory() %></td>
					<td><%= ctf.getDifficulty() %></td>
					<td><%= ctf.getDate() %></td>
				</tr>
			</it:iterate>
			
			</table>	
	
		</div>

	</body>

</html>