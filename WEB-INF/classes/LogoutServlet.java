import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
	
	public class LogoutServlet extends HttpServlet
	{
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException 
		{
			request.getSession().invalidate();			
			response.sendRedirect("home");
		}
	}