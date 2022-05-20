<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="dao.Dao,java.util.*,ctf.CtfBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">
	
	<% 
	CtfBean ctf = Dao.getCtfById(Integer.parseInt(request.getParameter("id"))); 
	%>
	
	<head>
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/challenge.css">
		<title>Challenge</title>
	</head>
	
	<body>
		<div class="ctf_box">
			<div class="ctf_body">
				<h3><%= ctf.getTitle() %></h3>
				<p style="font-size:13px;">Author: <%=ctf.getCreator()%></p>
				<div class="ctf_points">
					<%= ctf.getDifficulty()*10 %>
				</div>
				<hr>
				<div class="ctf_description">
					<h4>Description</h4>
					<%= ctf.getDescription() %>
				</div>
			</div>
			<div class="ctf_stats">
				#STATISTICHE
			</div>
			<div class="ctf_flag">
				<input type="text" placeholder="openCTF{flag}" name="flag">
				<button type="submit">Submit Flag</button>
			</div>
		</div>
	</body>
</html>