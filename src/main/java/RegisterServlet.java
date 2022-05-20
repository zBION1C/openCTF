import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import dao.Dao;

public class RegisterServlet extends HttpServlet {
	private String username;
	private String password, snd_password;

	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		username = request.getParameter("username");
		password = request.getParameter("password");
		snd_password = request.getParameter("snd_password");


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
		
		HttpSession session = request.getSession(true);
		Connection connection = (Connection) session.getAttribute("connection");
		
		try {
			if (username.equals("") || password.equals("")){
				out.println("<p>Please fill all the fields of the form.</p>");
			}
			else if (!password.equals(snd_password)) {
				out.println("Passwords do not match.");
			} else {
				Dao.register(connection, username, password);
				out.println("<p>Your account has been registered.</p>");
			}
		} catch ( SQLException e ) {
            String state = e.getSQLState();
            if (state.equals("23000")) {
            	out.println("Username already exists.");
            }
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }


		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}