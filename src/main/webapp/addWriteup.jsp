<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="dao.Dao, java.util.*, user.UserBean, writeup.WriteupBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	%>
	
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/topnav.css">
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
	</body>
	
</html>