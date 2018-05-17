import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WriteReviewServlet extends HttpServlet 
{
	public HashMap<String, ProductCatalog> hm = null;
	Review rv = null;	
	ProductCatalog pc = null;	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String productID = request.getParameter("productid1");
		String category = null;
		if(productID.startsWith("PH"))
		{
			category = "phone";
			hm = WfCache.isPhoneCatalogFetched();
		}
		else if(productID.startsWith("SM"))
		{
			category = "smartwatch";
			hm = WfCache.isSmartWatchCatalogFetched();
		}
		else if(productID.startsWith("HD"))
		{
			category = "hdd";
			hm = WfCache.isExternalCatalogFetched();
		}
		else if(productID.startsWith("HE"))
		{
			category = "headphone";
			hm = WfCache.isHeadPhoneCatalogFetched();
		}
		else if(productID.startsWith("SP"))
		{
			category = "speaker";
			hm = WfCache.isSpeakerCatalogFetched();
		}
		else if(productID.startsWith("AC"))
		{
			category = "accessory";
			hm = WfCache.isAccessoryCatalogFetched();
		}
		else
		{
			category = "laptop";
			hm = WfCache.isLaptopCatalogFetched();
		}
		
		pc = hm.get(productID);
		
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printWriteReviewContent(pc,category);
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try 
		{
			rv  = new Review();
			
			
			rv.setProductModelName(request.getParameter("productmodelname"));
			rv.setCategory(request.getParameter("category"));
			rv.setProductPrice(request.getParameter("productprice"));
			rv.setRetailerName(request.getParameter("retailername"));
			rv.setRetailerZip(request.getParameter("retailerzip"));
			rv.setRetailerCity(request.getParameter("retailercity"));
			rv.setRetailerState(request.getParameter("retailerstate"));
			rv.setProductOnSale(request.getParameter("productonsale"));
			rv.setUserID(request.getParameter("userid"));
			rv.setManufacturerRebate(request.getParameter("manufacturerrebate"));
			rv.setUserOccupation(request.getParameter("useroccupation"));
			rv.setReviewRating(request.getParameter("reviewrating"));
			rv.setUserAge(request.getParameter("userage"));
			rv.setManufacturerName(request.getParameter("manufacturername"));
			rv.setReviewText(request.getParameter("reviewtext"));
			rv.setUserGender(request.getParameter("usergender"));		
			
			MongoDBDataStoreUtilities.insertReview(rv);
						
			response.setContentType("text/html");
			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Review Servlet Result</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>" + "Review Submitted Successfully." + "</h2>");
			out.println("<br/>");
			out.println("<a href='home'> Home Page </a>");			
			out.println("</body>");
			out.println("</html>");
			out.close();
		}		
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
}