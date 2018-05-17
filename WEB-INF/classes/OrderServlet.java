import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class OrderServlet extends HttpServlet 
{
	String username = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			MySqlDataStoreUtilities objSQL  = new MySqlDataStoreUtilities();
		
		
			if(request.getSession().getAttribute("username") != null)
			{
				username = request.getSession().getAttribute("username").toString();
			}
			
			HashMap<String, ProductCatalog> hm = objSQL.fetchOrderDetails(username);	
				
			PrintWriter out = response.getWriter();		
			HtmlUtility htmlUtil = new HtmlUtility(request,out);		
			htmlUtil.printHtml("header.html");		
			htmlUtil.printOrderContent(hm);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");	
		
		}
		catch(SQLException ex)
		{
			
		}
			
	}
}