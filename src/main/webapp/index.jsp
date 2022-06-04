<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="user.UserBean,dao.Dao,java.sql.*,java.util.*,ctf.CtfBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>

	<% 
	UserBean user = (UserBean) session.getAttribute("currentUser");
	String orderby = request.getParameter("orderby");
	String search = request.getParameter("search");
	String filterDif = request.getParameter("dif");
	String filterCat = request.getParameter("cat");
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

				<it:iterate list="<%= Dao.getCTF(orderby, search, filterDif, filterCat) %>">
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
				<button id="filterBtn" style="height: 30px; width: 30px;"></button>
			</div>
		</div>
		<div id="myModal" class="modal">
			<div class="modal-content">
				<span class="close">&times;</span>
				<h3 style="margin-top: 9px;">Filter by</h3>
				<form action="Home" method="get">
					<p>Difficulty: <select name="dif">
						<option value="null">Choose your difficulty</option>
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
					<p>Category: <select name="cat">
						<option value="null">Choose your category</option>
						<option value="Reverse Engineering">Reverse Engineering</option>
						<option value="Binary Exploitation">Binary Exploitation</option>
						<option value="Forensic">Forensic</option>
						<option value="Web Exploitation">Web Exploitation</option>
					</select></p>
				<button type="submit">Submit</button>
				</form>
			</div>
		</div>
		<script>
			var modal = document.getElementById("myModal");
			
			var btn = document.getElementById("filterBtn");
			
			var span = document.getElementsByClassName("close")[0];
			
			btn.onclick = function() {
			  modal.style.display = "block";
			}
			
			span.onclick = function() {
			  modal.style.display = "none";
			}
			
			window.onclick = function(event) {
			  if (event.target == modal) {
			    modal.style.display = "none";
			  }
			}
		</script>
	</body>

</html>