import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import user.UserBean;
import dao.Dao;

public class ManagementServlet extends HttpServlet {
	private int id;
	private UserBean user;
	private int mng;
	private String username;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {	
		HttpSession session = request.getSession();
		user = (UserBean) session.getAttribute("currentUser");
		
		mng = Integer.parseInt(request.getParameter("mng"));
		
		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		}
		username = request.getParameter("username");
		
		try {
		if (user.getAdmin()) {
			if (mng == 1) {	
					Dao.removeCTF(id);
					response.sendRedirect("Home");
				} else if (mng == 2) {
					Dao.removeWriteup(id, username);
					response.sendRedirect("Writeups?id="+id);
				} else if (mng == 3) {
					Dao.ban(username);
					response.sendRedirect("Profile?username="+username+" (Banned)");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}