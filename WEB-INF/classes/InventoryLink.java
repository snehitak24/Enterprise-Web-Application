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
import com.google.gson.Gson;

public class InventoryLink extends HttpServlet {
	String usertype=null;
	String querytype=null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printHtml("inventory.html");
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();
		
		
		querytype = request.getParameter("querytype").trim();
		//out.println("Q: "+querytype);
		request.getSession().setAttribute("username", "Storemanager");
		request.getSession().setAttribute("usertype", "storemanager");
		try{
		if(querytype.equals("op1"))
		{
			productDetails = objSqlDataStore.fetchProductDetails();
			
			htmlUtil.printHtml("header.html");
			htmlUtil.printInventoryContent(productDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
		}
		else if(querytype.equals("op2")){
			response.sendRedirect("/csj/chart1.html");
		}
		else if(querytype.equals("op3")){
			productDetails = objSqlDataStore.fetchProductOnSale();
			htmlUtil.printHtml("header.html");
			htmlUtil.printInventoryContent(productDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
		}
		else if(querytype.equals("op4")){
			productDetails = objSqlDataStore.fetchProductOnRebates();
			htmlUtil.printHtml("header.html");
			htmlUtil.printInventoryContent(productDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
		}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}

}