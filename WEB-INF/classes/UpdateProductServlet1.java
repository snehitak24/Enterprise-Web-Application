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

	
	public class UpdateProductServlet1 extends HttpServlet
	{
		public String producttype;
		public String filePath;
		
		public static HashMap<String, ProductCatalog> hm_prod = new HashMap<String, ProductCatalog>();
		
		
				
		public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
			{
				try
				{
					ProductCatalog pc = new ProductCatalog();
					MySqlDataStoreUtilities objSql = new MySqlDataStoreUtilities();						
					
					pc.setId(request.getParameter("productid"));
					pc.setCategory(request.getParameter("category"));
					pc.setName(request.getParameter("productname"));
					//pc.setManufacturer(request.getParameter("manufacturer"));
					pc.setPrice(Float.parseFloat(request.getParameter("price")));
					pc.setImagepath(request.getParameter("imagepath"));
					pc.setCondition(request.getParameter("condition"));
					pc.setRetailer(request.getParameter("retailer"));
					
					System.out.println(request.getParameter("category"));
					System.out.println(pc.getCategory());
					
					if(pc.getCategory().equals("phone"))
					{
						WfCache.phoneCatalog.remove(request.getParameter("productid"));
						WfCache.phoneCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("speaker"))
					{
						WfCache.speakerCatalog.remove(request.getParameter("productid"));
						WfCache.speakerCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("laptop"))
					{
						WfCache.laptopCatalog.remove(request.getParameter("productid"));
						WfCache.laptopCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("headphone"))
					{
						WfCache.headphoneCatalog.remove(request.getParameter("productid"));
						WfCache.headphoneCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("accessory"))
					{
						WfCache.accessoryCatalog.remove(request.getParameter("productid"));
						WfCache.accessoryCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("smartwatch"))
					{
						WfCache.smartwatchCatalog.remove(request.getParameter("productid"));
						WfCache.smartwatchCatalog.put(pc.getId(),pc);
					}
					if(pc.getCategory().equals("hdd"))
					{
						WfCache.hddCatalog.remove(request.getParameter("productid"));
						WfCache.hddCatalog.put(pc.getId(),pc);
					}
					WfCache.deleteProducts(pc.getId());
					objSql.insertProductDetails(pc);
					
					PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Product Updated</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>" + "Product Successfully Updated." + "</h2>");
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