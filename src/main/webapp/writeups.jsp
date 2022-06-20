<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="dao.Dao, java.util.*, user.UserBean, writeup.WriteupBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">
	
	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>
	
	<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	UserBean user = (UserBean) session.getAttribute("currentUser");
	Boolean isAdmin = user.getAdmin();
	%>
	
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/writeups.css">
		<link rel="stylesheet" href="css/topnav.css">
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
		
		<div class="ws_box">
			<table class="ws_list">
				<thead>
					<th> Name </th>
					<th> Author </th>
					<th> Date </th>
					<% if (isAdmin) { %>
						<th>Manage Writeups</th>
					<% } %>
				</thead>
	
				<it:iterate list="<%= Dao.getWriteups(id) %>">
				<% 
				WriteupBean w = (WriteupBean) pageContext.getAttribute("item"); 
				String path = "challenge/" + id + "/writeups/" + w.getName();
				%>
					<tr style="text-align: center;">
						<td><a href=<%=path %>> <%= w.getName() %> </a></td>
						<td><%= w.getUser() %></td>
						<td><%= w.getTs() %></td>
						<% if (isAdmin) { %>
							<td><a href="Management?mng=2&id=<%= id %>&username=<%= w.getUser() %>">REMOVE</a></td>
						<% } %>
					</tr>
				</it:iterate>
			</table>
		</div>
	</body>
	
</html>