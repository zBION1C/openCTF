<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	Integer id = Integer.parseInt(request.getParameter("id"));
	%>
	
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/topnav.css">
		<link rel="stylesheet" href="css/addWriteup.css">
		<title>Add your writeup</title>
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
		
		<div class="box">
			<h3>Submit your writeup</h3>
			<hr>
			<form action="/openCTF/AddWriteup?id=<%=id%>" method="post" enctype="multipart/form-data">
				<p>Writeup : <input type="file" name="file"></p>
				<button type="submit">Submit</button>
			</form>
		</div>
	</body>
	
</html>