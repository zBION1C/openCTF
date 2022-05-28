<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN\http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">

<%@ page language="java" import="dao.Dao,java.util.*,ctf.CtfBean, hint.HintBean, comment.CommentBean, user.UserBean, file.FileBean"%>

<html xmlns = "http://www.w3.org/1999/xhtml">
	
	<%@taglib uri="/WEB-INF/tlds/iterator.tld" prefix="it" %>
	
	<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	CtfBean ctf = Dao.getCtfById(id); 
	
	UserBean user = (UserBean) session.getAttribute("currentUser");

	Boolean alreadyCompleted = false;
	Boolean resolved = false;
	
	if (user != null) {	
		
		String flag = (String) request.getParameter("flag");
		if (flag != null) {
			resolved = ctf.validateFlag(flag, user);
		}
		
		alreadyCompleted = Dao.alreadyResolved(user.getUsername(), id);
		
		String c = (String)request.getParameter("comment");
		if (c != null && !c.equals("")){
			Dao.putComment(c, user.getUsername(), id);
		}
	}
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
					<p><%= ctf.getDifficulty()*10 %> Points</p>
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
					
					<div class="hints_but">
						<it:iterate list="<%= Dao.getHints(id) %>">
							<% HintBean hint = (HintBean) pageContext.getAttribute("item"); %>
							<% 
							cnt = cnt + 1;
							testo = hint.getTesto(); 
							%>
							
							<button class="hint" onclick="printHint('<%= testo %>')" > <%= cnt %> </button>
						</it:iterate>
					</div>
					
					<p id="hint"></p>	
					
				</div>
			</div>
			
			<div class="file">
				<h5>Download files</h5>
				<it:iterate list="<%= Dao.getFiles(id) %>">
					<% 
					FileBean file = (FileBean) pageContext.getAttribute("item"); 
					String path = "challenge/" + id + "/" + file.getName();
					%>
					<a href=<%= path %> download><%= file.getName() %></a>
				</it:iterate>
			</div>
			
			<div class="stats">
				<%
				int n = Dao.getResolvers(id);
				int m = Dao.getAttempts(id);	
				%>
				
				<%=n%> solves / <%=m%> attempts (<%= m!=0 ?(float) n / m : 0%>%)
			</div>
					
			<% if (user == null) { %>
				<h5>You have to be <a href="login.html">logged</a> in to submit the flag.</h5>
			<% } else if (!alreadyCompleted) { %>
				<div class="flag">
					<form action="Challenge?id=<%= id %>" method="post">
						<input type="text" placeholder="openCTF{flag}" name="flag">
							
						<button type="submit">Submit</button>
					</form>
				</div>
			<% } else { %>
				<h5>You have completed this challenge!</h5>
			<% } %>
			<hr>
			
			<div class="comments">
				<h4>Comments</h4>
				<% if (user != null) { %>
				<form action="Challenge?id=<%= id %>" method="post">
					<textarea name="comment" placeholder="Insert comment here..."></textarea>
					<button type="submit">Submit</button>
				</form>
				<% } %>
				
				<it:iterate list="<%= Dao.getComments(id) %>">
					<% CommentBean comm = (CommentBean) pageContext.getAttribute("item"); %>
					<div id="comm">
					<%= comm.getUtente() %> · <%= comm.getTs() %>
					<p style="padding-left: 5px; margin-bottom: 3px;overflow-wrap: break-word; max-width: 700px;"> <%= comm.getTesto() %></p>
					</div>
				</it:iterate>
				
			</div>
			
		</div>
	</body>
</html>









