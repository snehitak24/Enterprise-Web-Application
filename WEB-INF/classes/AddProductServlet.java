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

	
	public class AddProductServlet extends HttpServlet
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
				htmlUtil.printAddProductContent();
				htmlUtil.printHtml("leftnavigationbar.html");
				htmlUtil.printHtml("footer.html");	
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
			{
				try
				{
					ProductCatalog objProductCatalog = new ProductCatalog();
					response.setContentType("text/html");  
				PrintWriter out = response.getWriter();				
				MySqlDataStoreUtilities objSql = new MySqlDataStoreUtilities();					
				objProductCatalog.setId("11");
				objProductCatalog.setRetailer(request.getParameter("retailer"));
				objProductCatalog.setManufacturer(request.getParameter("manufacturer"));
				objProductCatalog.setName(request.getParameter("name"));
				objProductCatalog.setImagepath(request.getParameter("image"));
				objProductCatalog.setCondition(request.getParameter("condition"));
				objProductCatalog.setPrice(Float.valueOf(request.getParameter("price")));
				
				producttype = request.getParameter("product_type");
				objProductCatalog.setCategory(producttype);
				
				
					if(producttype.equalsIgnoreCase("phone"))
					{
						objProductCatalog.setId("PH"+objProductCatalog.getName());
						WfCache.phoneCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("accessory"))
					{
						objProductCatalog.setId("AC"+objProductCatalog.getName());
						WfCache.accessoryCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("headphone"))
					{
						objProductCatalog.setId("HP"+objProductCatalog.getName());
						WfCache.headphoneCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("laptop"))
					{
						objProductCatalog.setId("LP"+objProductCatalog.getName());
						WfCache.laptopCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("speaker"))
					{
						objProductCatalog.setId("SP"+objProductCatalog.getName());
						WfCache.speakerCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("hdd"))
					{
						objProductCatalog.setId("HDD"+objProductCatalog.getName());
						WfCache.hddCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					else if(producttype.equalsIgnoreCase("smartwatch"))
					{
						objProductCatalog.setId("SM"+objProductCatalog.getName());
						WfCache.smartwatchCatalog.put(objProductCatalog.getId(),objProductCatalog);
						WfCache.getAllProducts();
						objSql.insertProductDetails(objProductCatalog);
					}
					
					out.println(
			"<html>"+
			"<body>"+
			"<h3>Product is successfully added</a></h3>"+
			"<a href='home'> Home Page </a>"
			);
				}
				
				catch(SQLException ex)
				{
					
				}
			}
	}