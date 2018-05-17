import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class ExternalServlet extends HttpServlet 
{
	public HashMap<String, ProductCatalog> hm = null;
	//MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	ProductCatalog pc = null;
	ProductCatalog pc1 = null;
	public HashMap<String, ProductCatalog> hm1 = null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try{
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printProductContent("hdd",id);
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try 
		{
			String productID = request.getParameter("productid");
			if(Servlet_UpdateCart(productID))
			{
				if(request.getSession().getAttribute("cartcount") != null)
				{
					int cartcount = Integer.parseInt(request.getSession().getAttribute("cartcount").toString()) + 1;
					request.getSession().setAttribute("cartcount", cartcount);
				}
				else
				{
					request.getSession().setAttribute("cartcount", 1);
				}				
			}
			response.sendRedirect("hdd");
		}		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public boolean Servlet_UpdateCart(String productID)throws IOException 
	{
		hm = WfCache.isExternalCatalogFetched();
		pc = hm.get(productID);
		
		hm1 = WfCache.showCart();
		
		if(hm1 != null)
		{
			if(hm1.containsKey(productID))
			{
				pc1 = hm1.get(productID);
				if(pc1 != null)
				{
					int qty = pc1.getQuantity() + 1;
					pc1.setQuantity(qty);
					WfCache.addToCart(pc1);
					return false;
				}
			}
			else
			{
				int qty = 1;
				pc.setQuantity(qty);
				WfCache.addToCart(pc);								
			}
		}
		else
		{
			int qty = 1;
			pc.setQuantity(qty);
			WfCache.addToCart(pc);			
		}
		return true;
	}
}