import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;


public class SalesReportLink extends HttpServlet {
	String usertype=null;
	String querytype=null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printHtml("sales.html");
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		HashMap<String, ArrayList<ProductCatalog>> salesDetails = new HashMap<String, ArrayList<ProductCatalog>>();
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		querytype = request.getParameter("querytype").trim();
		//out.println("Q: "+querytype);
		request.getSession().setAttribute("username", "Storemanager");
		try{
		if(querytype.equals("op1"))
		{
			productDetails = objSqlDataStore.fetchSalesOrderDetails();
			
			htmlUtil.printHtml("header.html");
			htmlUtil.printSalesContent(productDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
		}
		else if(querytype.equals("op2")){
			response.sendRedirect("/csj/chart2.html");
		}
		else if(querytype.equals("op3")){
			PrintWriter out1 = response.getWriter();
			salesDetails = objSqlDataStore.fetchEveryDaySales();
			//for(String key: salesDetails.keySet())
			//out1.println(key);
			//out1.println("1");
			htmlUtil.printHtml("header.html");
			htmlUtil.printEverydaySalesContent(salesDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
			
		}
		
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}

}