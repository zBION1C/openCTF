import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import user.UserBean;
import dao.Dao;
import org.apache.commons.fileupload.*;

public class AddWriteupServlet extends HttpServlet {
	private String name;
	private UserBean user;
	private Integer ctf;
	private Integer id;
	List items;
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		HttpSession session = request.getSession();
		user = (UserBean) session.getAttribute("currentUser");

		id = Integer.parseInt(request.getParameter("id"));
		
		if (user != null) {
			boolean isMultipart = FileUpload.isMultipartContent(request);
			DiskFileUpload upload = new DiskFileUpload();
			
			upload.setSizeThreshold(5000);
			upload.setSizeMax(500000);
			
			try {
				items= upload.parseRequest(request);
			} catch (FileUploadException e1) {
				e1.printStackTrace();
			}
			
			Iterator itr = items.iterator();
		
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				try {
					upload.setRepositoryPath(getServletContext().getRealPath("/")+"challenge/" + id + "/writeups");
					new File(getServletContext().getRealPath("/")+ "challenge/" + id + "/writeups").mkdir();
					File fullFile = new File(item.getName());
					File SavedFile = new File(getServletContext().getRealPath("/")+ "challenge/" + id + "/writeups/" + fullFile.getName());
					item.write(SavedFile);
					Dao.addWriteup(fullFile.getName(), user.getUsername(), id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			response.sendRedirect("index.jsp");
			
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<?xml version = \"1.0\"?>");

			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD " +
						"XHTML 1.0 Strict//EN\" \"http://www.w3.org" +
						"/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
			out.println("<html xmlns = \"http://www.w3.org/1999/xhtml\">");

			out.println("<head>");
			out.println("<link rel=\"stylesheet\" href=\"css/general.css\">");
			out.println("<title>Error</title>");
			out.println("</head>");

			out.println("<body>");
			out.println("<h5>You have to be logged in to add a Writeup.</h5>");
			out.println("</body>");
			
		}
		
	}
	
	
}