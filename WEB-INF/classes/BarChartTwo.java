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
import javax.servlet.annotation.WebServlet;

@WebServlet("/barcharttwo")
public class BarChartTwo extends HttpServlet {
	String usertype=null;
	String querytype=null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		
		
		request.getSession().setAttribute("username", "Storemanager");
		request.getSession().setAttribute("usertype", "storemanager");
		
		try{
		
			List<ProductCatalog> list = getProductData();
			Gson gson = new Gson();

			String jsonString = gson.toJson(list);
			response.setContentType("application/json");

			response.getWriter().write(jsonString);
			//htmlUtil.printHtml("visualization-chart-demo.html");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public List<ProductCatalog> getProductData(){
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();
		try{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		
		productDetails = objSqlDataStore.fetchSalesOrderDetails();
		
			for(String key: productDetails.keySet()){
				list.add(productDetails.get(key));
			}
			return list;
		}
		catch(Exception e){e.printStackTrace();}
		return list;
	}
	
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			productDetails = objSqlDataStore.fetchProductDetails();
			for(String key: productDetails.keySet()){
				list.add(productDetails.get(key));
			}
			htmlUtil.printHtml("header.html");
			Gson gson = new Gson();

			String jsonString = gson.toJson(list);

			//response.setContentType("application/json");

			//response.getWriter().write(jsonString);
			htmlUtil.printHtml("visualization-chart-demo.html");
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
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
	}*/

}