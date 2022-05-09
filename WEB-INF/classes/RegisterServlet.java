import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
	private String username;
	private String password, snd_password;
	private Connection connection;
	private PreparedStatement addUser;

	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/openCTF", "nicholas", "Nicholas_Montana8");
			// PreparedStatement to add one to vote total for a
			// specific animal
			addUser = connection.prepareStatement("INSERT INTO Utente (username, password, data) VALUES (?, ?, NOW())");
		}
		catch ( Exception exception ) {
			exception.printStackTrace();
			throw new UnavailableException( exception.getMessage() );
		}

	}

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
		
		try {
			if (username.equals("") || password.equals("")){
				out.println("<p>Please fill all the fields of the form.</p>");
			}

			else if (!password.equals(snd_password)) {
				out.println("Passwords do not match.");
			} else {
				addUser.setString(1, username);
				addUser.setString(2, password);
				addUser.executeUpdate();
				out.println("<p>Your account has been registered.</p>");
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