<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    
<%@ page language="java" import="user.UserBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	%>
	
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/addCtf.css">
		<link rel="stylesheet" href="css/topnav.css">
		<title>Add your challenge</title>
	</head>
	
	<body>
		<%@ include file="topnav.jsp" %>
		
		<div class="box">
			<h3>Submit your own CTF!</h3>
			<hr>
			<form action="/openCTF/AddCtf" method="post" enctype="multipart/form-data">
				<p>Title: <input type="text" name="title" placeholder="Title of your CTF (at least 4 chars)"></p>
				<p>Difficulty: <select name="difficulty">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
				</select></p>
				<p>Description: <textarea name="description" placeholder="Description of your CTF (at least 20 chars)"></textarea></p>
				<p>Flag: <input type="text" name="flag" placeholder="Flag of your CTF (at least 20 chars)"></p>
				<p>Category: <select name="category">
					<option value="Reverse Engineering">Reverse Engineering</option>
					<option value="Binary Exploitation">Binary Exploitation</option>
					<option value="Forensic">Forensic</option>
					<option value="Web Exploitation">Web Exploitation</option>
				</select></p>
				<p>Hints: <textarea name="hints" placeholder="If you are uploading multiple hints, please separate them with '#'"></textarea></p>
				<p>Files : <input type="file" name="files" multiple></p>
				<button type="submit">Submit</button>
			</form>
		</div>
	</body>
</html>