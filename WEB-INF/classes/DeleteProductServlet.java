import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import javax.xml.*;
import java.io.File;
import java.util.HashMap;	
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.IOException;
import javax.xml.*;
import java.sql.*;

	
	public class DeleteProductServlet extends HttpServlet
	{
		public String producttype;
		public String filePath;
		
		public static HashMap<String, ProductCatalog> hm_prod = new HashMap<String, ProductCatalog>();
				
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{		
			try
			{
				PrintWriter out = response.getWriter();		
				HtmlUtility htmlUtil = new HtmlUtility(request,out);		
				htmlUtil.printHtml("header.html");		
				htmlUtil.printDeleteProductContent();
				htmlUtil.printHtml("leftnavigationbar.html");
				htmlUtil.printHtml("footer.html");	
			}
			catch(SQLException ex)
			{
				
			}
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
			{
				try
				{
					String productId = request.getParameter("productid");
					ProductCatalog pc = new ProductCatalog();
					MySqlDataStoreUtilities objSql = new MySqlDataStoreUtilities();	
					
					if(productId.toLowerCase().startsWith("ph"))
					{
						WfCache.phoneCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("hdd"))
					{
						 WfCache.hddCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("lp"))
					{
						WfCache.laptopCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("sp"))
					{
						 WfCache.speakerCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("ac"))
					{
						 WfCache.accessoryCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("hp"))
					{
						 WfCache.headphoneCatalog.remove(productId);
					}
					else if(productId.toLowerCase().startsWith("sm"))
					{
						 WfCache.smartwatchCatalog.remove(productId);
					}
					WfCache.deleteProducts(productId);
					
					PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Product Deleted</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>" + "Product Deleted Successfully." + "</h2>");
					out.println("<br/>");
					out.println("<a href='home'> Home Page </a>");			
					out.println("</body>");
					out.println("</html>");	
					
				}
				
				catch(SQLException ex)
				{
					
				}
			}
	}