<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="dao.Dao,java.util.*,ctf.CtfBean, hint.HintBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">
	
	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>
	
	<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	CtfBean ctf = Dao.getCtfById(id); 
	%>
	
	<script>
		function printHint(testo) {
		  document.getElementById("hint").innerHTML = testo;
		}
	</script>
	
	<head>
		<link rel="stylesheet" href="css/general.css">
		<link rel="stylesheet" href="css/challenge.css">
		<title>Challenge</title>
	</head>
	
	<body>
		<div class="ctf_box">
			<div class="header">
				<div class="title">
					<h3><%= ctf.getTitle() %></h3>
					<p style="font-size:13px;">Author: <%=ctf.getCreator()%></p>
				</div>
				<div class="points">
					<p>Points: <%= ctf.getDifficulty()*10 %> </p>
				</div>
			</div>
			
			<hr>
			
			<div class="body">
				<div class="description">
					<h4>Description</h4>
					<p><%= ctf.getDescription() %></p>
				</div>
				
				<% int cnt = 0; %>
				<% String testo; %>
				
				
				<div class="hints">
					<h4>Hints</h4>
					
					<it:iterate list="<%= Dao.getHints(id) %>">
						<% HintBean hint = (HintBean) pageContext.getAttribute("item"); %>
						<% 
						cnt = cnt + 1;
						testo = hint.getTesto(); 
						%>
						
					
						<div class="hints_but">
							<button class="hint" onclick="printHint('<%= testo %>')" > <%= cnt %> </button>
						</div>
						
					</it:iterate>
					
					<p id="hint"></p>	
					
				</div>
			</div>
			
			<div class="stats">
				<%
				int n = Dao.getResolvers(id);
				int m = Dao.getAttempts(id);	
				%>
				
				<%=n%> solves / <%=m%> attempts (<%= m!=0 ?(float) n / m : 0%>%)
			</div>
			
			<div class="flag">
				<input type="text" placeholder="openCTF{flag}" name="flag">
					
				<button type="submit">Submit</button>
			</div>
		
		</div>
	</body>
	
</html>