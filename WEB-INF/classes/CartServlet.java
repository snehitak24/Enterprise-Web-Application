import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class CartServlet extends HttpServlet 
{
	public HashMap<String, ProductCatalog> hm = null;
	
	ProductCatalog pc = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printCartContent();
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try 
		{
			String productID = request.getParameter("productid");
			hm = WfCache.deleteCart(productID);
			
			if(request.getSession().getAttribute("cartcount") != null)
			{
				int cartcount = Integer.parseInt(request.getSession().getAttribute("cartcount").toString()) - 1;
				request.getSession().setAttribute("cartcount", cartcount);
			}
			
			response.sendRedirect("cart");
		}		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}