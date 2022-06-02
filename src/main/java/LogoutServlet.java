import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LogoutServlet extends HttpServlet {
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		request.getSession(false).invalidate();
		
		response.sendRedirect("index.jsp");
	}
	
}