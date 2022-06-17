import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import user.UserBean;
import dao.Dao;
import org.apache.commons.fileupload.*;

public class AddCtfServlet extends HttpServlet {
	private int id;
	private String title;
	private Integer difficulty;
	private String description;
	private String flag;
	private String category;
	private UserBean user;
	private String hints;
	List items;
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		
		HttpSession session = request.getSession();
		user = (UserBean) session.getAttribute("currentUser");
		
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
				if (item.isFormField()) {
					String name = item.getFieldName();
					if (name.equals("title")) {
						title = item.getString();
					} else if (name.equals("difficulty")) {
						difficulty = Integer.parseInt(item.getString());
					} else if (name.equals("description")) {
						description = item.getString(); 
					} else if (name.equals("flag")) {
						flag = "openCTF{"+ item.getString() + "}";
					} else if (name.equals("category")) {
						category = item.getString();
					} else if (name.equals("hints")) {
						try {
							id = Dao.addCTF(title, difficulty, description, flag, user.getUsername(), category);
							hints = item.getString();
							if (!hints.equals("")) {
								String[] hintslist = hints.split("#");
								for (String s : hintslist) {
									Dao.addHint(s, id);
								}
							}
						} catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
					}
				} else if (!item.getName().equals("")) {			
					try {
						upload.setRepositoryPath(getServletContext().getRealPath("/")+"challenge/" + id + "/");
						new File(getServletContext().getRealPath("/")+ "challenge/" + id).mkdir();
						File fullFile = new File(item.getName());
						File SavedFile = new File(getServletContext().getRealPath("/")+ "challenge/" + id + "/" + fullFile.getName());
						item.write(SavedFile);
						Dao.addFile(fullFile.getName(), id);
					} catch (Exception e) {
						e.printStackTrace();
					}
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
			out.println("<h5>You have to be logged in to add a CTF.</h5>");
			out.println("</body>");
			
		}
	
	}
}