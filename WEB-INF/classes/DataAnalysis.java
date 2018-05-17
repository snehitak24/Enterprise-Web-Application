import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;


public class DataAnalysis extends HttpServlet 
{
	public HashMap<String, ProductCatalog> hm = null;
	public HashMap<String, Review> hmReview = null;
	Review rv = null;	
	ProductCatalog pc = null;
	DBCollection myReviews;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{		
			//String productName = request.getParameter("productname");
		
			MongoClient mango;
			mango = new MongoClient("localhost", 27017);
			
			DB db = mango.getDB("CustomerReviews");
			myReviews = db.getCollection("myReviews");
			
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put("productmodelname", productName);

			//DBCursor cursor = myReviews.find(searchQuery);
			
			DBCursor cursor = myReviews.find();
			PrintWriter writer = response.getWriter();
			
			writer.println("<!DOCTYPE html>");
			writer.println("<html lang='en'>");
			writer.println("<head>");
			writer.println("<meta charset='UTF-8'>");
			writer.println("    <title></title>");
			writer.println("</head>");
			writer.println("<body>");
			while(cursor.hasNext()!=false){
				DBObject dob = cursor.next();
				
			writer.println("<table border='1'>");
			writer.println("<tr><td>Product Model</td> <td>"+dob.get("productmodelname")+"</td></tr>");
			writer.println("<tr><td>Category</td><td>"+dob.get("category")+"</td></tr>");
			writer.println("<tr><td>Price</td><td>"+dob.get("productprice")+"</td></tr>");
			writer.println("<tr><td>Retailer Name</td><td>"+dob.get("retailername")+"</td></tr>");
			writer.println("<tr><td>Retailer Zip</td><td>"+dob.get("retailerzip")+"</td></tr>");
			writer.println("<tr><td>Retailer City</td><td>"+dob.get("retailercity")+"</td></tr>");
			writer.println("<tr><td>Retailer State</td><td>"+dob.get("retailerstate")+"</td></tr>");
			writer.println("<tr><td>Product on Sale</td><td>"+dob.get("productonsale")+"</td></tr>");
			writer.println("<tr><td>User ID</td><td>"+dob.get("userid")+"</td></tr>");
			writer.println("<tr><td>manufacturer Rebate</td><td>"+dob.get("manufacturerrebate")+"</td></tr>");
			writer.println("<tr><td>User Occupation</td><td>"+dob.get("useroccupation")+"</td></tr>");
			writer.println("<tr><td>Review Rating</td><td>"+dob.get("reviewrating")+"</td></tr>");
			writer.println("<tr><td>User Age</td><td>"+dob.get("userage")+"</td></tr>");
			writer.println("<tr><td>Manufacturer Name</td><td>"+dob.get("manufacturername")+"</td></tr>");
			writer.println("<tr><td>Review Text</td><td>"+dob.get("reviewtext")+"</td></tr>");
			writer.println("<tr><td>User Gender</td><td>"+dob.get("usergender")+"</td></tr>");	
			writer.println("</table>");
			}
			writer.println("");
			writer.println("<form method='get' action='home'>");
			writer.println("<input type = submit value ='Home Page'>");
			writer.println("</form>");
			writer.println("</body>");
			writer.println("</html>");
		
		}
		
		catch(MongoException ex)
		{
			 ex.printStackTrace();
		}
		/*String productName = request.getParameter("productname");
		hmReview = new HashMap<String, Review>();
		rv = new Review();
		
		DBCursor dbCursor = MongoDBDataStoreUtilities.fetchReview(productName);
		
		while(dbCursor.hasNext()!=false)
		{
			DBObject dob = dbCursor.next();
			
			rv.setProductModelName(dob.get("productmodelname").toString());
			rv.setCategory(dob.get("category").toString());
			rv.setProductPrice(dob.get("productprice").toString());
			rv.setRetailerName(dob.get("retailername").toString());
			rv.setRetailerZip(dob.get("retailerzip").toString());
			rv.setRetailerCity(dob.get("retailercity").toString());
			rv.setRetailerState(dob.get("retailerstate").toString());
			rv.setProductOnSale(dob.get("productonsale").toString());
			rv.setUserID(dob.get("userid").toString());
			rv.setManufacturerRebate(dob.get("manufacturerrebate").toString());
			rv.setUserOccupation(dob.get("useroccupation").toString());
			rv.setReviewRating(dob.get("reviewrating").toString());
			rv.setUserAge(dob.get("userage").toString());
			rv.setManufacturerName(dob.get("manufacturername").toString());
			rv.setReviewText(dob.get("reviewtext").toString());
			rv.setUserGender(dob.get("usergender").toString());		
			
			hmReview.put(dob.get("productmodelname").toString(),rv);			
		}			
				
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printReadReviewContent(hmReview);
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");	 */
	}
}