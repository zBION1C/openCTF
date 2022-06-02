<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,dao.Dao,java.sql.*,java.util.*,ctf.CtfBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	String orderby = request.getParameter("orderby");
	String filter = request.getParameter("search");
	%>

	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/home.css">
		<link rel="stylesheet" href="css/topnav.css">

		<title>Home</title>
	</head>

	<body>	
		<%@ include file="topnav.jsp" %>
		
		<div class=main>	
			<div class="ctf_list_box">
				<table class="ctf_list">
					<thead>
						<th onclick="window.location='Home?orderby=titolo';"> Name </th>
						<th onclick="window.location='Home?orderby=utente';"> Author </th>
						<th onclick="window.location='Home?orderby=categoria';"> Category </th>
						<th onclick="window.location='Home?orderby=difficolta';"> Difficulty </th>
						<th onclick="window.location='Home?orderby=data';"> Date </th>
					</thead>

				<it:iterate list="<%= Dao.getCTF(orderby, filter) %>">
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
		</div>

	</body>

</html>