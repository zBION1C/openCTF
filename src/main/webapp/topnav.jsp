<div class="topnav">
	<a class="active" href="index.jsp">Home</a>
	<% if (user != null) { %>
	<a href="Profile">Profile</a>
	<a href="addCtf.html">Add CTF</a>
	<a href="#daje">Logout</a>
	<%} else {%>
	<a href="login.html">Login</a>
	<a href="register.html">Register</a>
	<%}%>
	
	<div class="search-container">
	<form action="/Search">
		<input type="text" placeholder="Search..." name="search">
		<button type="submit"><i class="fa fa-search"></i></button>
	</form>
	</div>
</div>