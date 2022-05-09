import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
	private String username;
	private String password;
	private Connection connection;
	private PreparedStatement getUser;

	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/openCTF", "nicholas", "Nicholas_Montana8");
			// PreparedStatement to add one to vote total for a
			// specific animal
			getUser = connection.prepareStatement("SELECT * FROM Utente WHERE username = ? AND password = ?");
		}
		catch ( Exception exception ) {
			exception.printStackTrace();
			throw new UnavailableException( exception.getMessage() );
		}

	}

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
		
		try {
			getUser.setString(1, username);
			getUser.setString(2, password);
			ResultSet result = getUser.executeQuery();
			if (!result.next()) {
				out.println("1");
				out.println("<p>Username or password not correct</p>");
			}
			else if (username.equals(result.getString(1)) && password.equals(result.getString(2))) {
				out.println("<p> Username: " + username + "</p>");
				out.println("<p> Password: " + password + "</p>");
			} else {
				out.println("2");
				out.println("<p>Username or password not correct</p>");
			}
		} 
		catch ( SQLException sqlException ) {
			sqlException.printStackTrace();
			out.println( "<title>Error</title></head>" );
			out.println( "<body><p>Database error occurred. " );
			out.println( "Try again later.</p></body></html>" );
			out.close();
		}

		out.println("</body>");

		out.println("</html>");
		out.close();
	}
}