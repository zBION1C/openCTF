
<div class="topnav">
	<a class="active" href="index.jsp">Home</a>
	<% if (user != null) { %>
	<a href="Profile?username=<%=user.getUsername()%>">Profile</a>
	<a href="Addctf">Add CTF</a>
	<a href="Scoreboard">Scoreboard</a>
	<a href="Logout">Logout</a>
	<%} else {%>
	<a href="login.html">Login</a>
	<a href="register.html">Register</a>
	<a href="Scoreboard">Scoreboard</a>
	<%}%>
	
	<div class="search-container">
	<form action="Home" method="GET">
		<input type="text" placeholder="Search..." name="search">
		<button type="submit"><i class="fa fa-search"></i></button>
	</form>
	</div>
</div>