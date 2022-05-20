import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import user.UserBean;
import dao.Dao;

public class LoginServlet extends HttpServlet {
	private String username;
	private String password;

	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		username = request.getParameter("username");
		password = request.getParameter("password");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<?xml version = \"1.0\"?>");

		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD " +
					"XHTML 1.0 Strict//EN\" \"http://www.w3.org" +
					"/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		out.println("<html xmlns = \"http://www.w3.org/1999/xhtml\">");

		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"css/general.css\">");
		out.println("<title>Login Page</title>");
		out.println("</head>");

		out.println("<body>");
		
		UserBean user = new UserBean();
		
		HttpSession session = request.getSession(true);
		Connection connection = (Connection) session.getAttribute("connection");
		
		try {
			if (Dao.validate(connection ,user, username, password)) {
				out.println("<p> Username: " + username + "</p>");
				out.println("<p> Password: " + password + "</p>");

				session.setAttribute("currentUser", user);
				response.sendRedirect("index.jsp");

			} else {
				out.println("<p>Username or password not correct</p>");
			}
		} catch ( SQLException e ) {
            out.println("An error has occured. Please try again.");
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }

		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}