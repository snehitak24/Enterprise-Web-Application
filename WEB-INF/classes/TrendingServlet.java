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
import com.mongodb.AggregationOutput;

public class TrendingServlet extends HttpServlet 
{
	DBCollection myReviews;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			HashMap<String, Integer> hmp1 = topZipCodes();
			HashMap<String, Integer> hmp2 = topProducts();
		
			PrintWriter writer = response.getWriter();
			
			writer.println("<!DOCTYPE html>");
			writer.println("<html lang='en'>");
			writer.println("<head>");
			writer.println("<meta charset='UTF-8'>");
			writer.println("<title></title>");
			writer.println("</head>");
			writer.println("<body>");
							
			writer.println("<table border='1'>");
			writer.println("<tr>");
			writer.println("<th colspan='2' align='center'>Top Zip Code By Review</th>");
			writer.println("</tr>");
			writer.println("<tr></tr>");
			writer.println("<tr></tr>");			
			
			for (String key: hmp1.keySet()){					
				
				String value = hmp1.get(key).toString();  
				writer.println("<tr><td>"+key+"</td> <td>"+value+"</td></tr>");
				
				//System.out.println(key + " " + value);
			} 
			writer.println("</table>");
			writer.println("");
			writer.println("<table border='1'>");
			writer.println("<tr>");
			writer.println("<th colspan='2' align='center'>Top Products By Review</th>");
			writer.println("</tr>");
			writer.println("<tr></tr>");
			writer.println("<tr></tr>");
			for (String key: hmp2.keySet()){
				String value = hmp2.get(key).toString();
						
				writer.println("<tr><td>"+key+"</td> <td>"+value+"</td></tr>");
				//System.out.println(key + " " + value);
			}
			writer.println("</table>");
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
	}

	public HashMap<String, Integer> topZipCodes()
	{
		MongoClient mango;
		mango = new MongoClient("localhost", 27017);
			
		DB db = mango.getDB("CustomerReviews");
		myReviews = db.getCollection("myReviews");
		
		HashMap<String, Integer> hmReview=new HashMap<String, Integer>();
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("retailerzip", "$_id");
		projectFields.put("reviewCount", "$count");
		DBObject project = new BasicDBObject("$project", projectFields);

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$retailerzip");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject limit=new BasicDBObject();
		DBObject orderby=new BasicDBObject();
		
		DBObject sort = new BasicDBObject();
		// Specify the field that you want to sort on, and the direction of the sort
		sort.put("reviewCount",-1);
		
		
		//Adding sort object in DbObject
		orderby=new BasicDBObject("$sort",sort);
		limit=new BasicDBObject("$limit",5);
		
		AggregationOutput aggregate = myReviews.aggregate(group, project, orderby, limit);
		
		String reviewCount = null;
		int reviewCountCount;
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			System.out.println(bobj.getString("retailerzip"));
			reviewCount = bobj.getString("reviewCount");
			if(reviewCount == null || reviewCount.isEmpty()) {
				reviewCountCount=0;
			} else {
				reviewCountCount=Integer.parseInt(reviewCount);
			}
			System.out.println(reviewCountCount);
			hmReview.put(bobj.getString("retailerzip"), reviewCountCount);

		}
		
		return hmReview;
	}	

	public HashMap<String, Integer> topProducts()
	{
		MongoClient mango;
		mango = new MongoClient("localhost", 27017);
			
		DB db = mango.getDB("CustomerReviews");
		myReviews = db.getCollection("myReviews");
		
		HashMap<String, Integer> hmReview=new HashMap<String, Integer>();
		
		DBObject projectFields = new BasicDBObject("_id", 0);
		projectFields.put("productmodelname", "$_id");
		projectFields.put("reviewCount", "$count");
		DBObject project = new BasicDBObject("$project", projectFields);

		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productmodelname");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject limit=new BasicDBObject();
		DBObject orderby=new BasicDBObject();
		
		DBObject sort = new BasicDBObject();
		// Specify the field that you want to sort on, and the direction of the sort
		sort.put("reviewCount",-1);
		
		
		//Adding sort object in DbObject
		orderby=new BasicDBObject("$sort",sort);
		limit=new BasicDBObject("$limit",5);
		
		AggregationOutput aggregate = myReviews.aggregate(group, project, orderby, limit);
		
		String reviewCount = null;
		int reviewCountCount;
		for (DBObject result : aggregate.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			System.out.println(bobj.getString("productmodelname"));
			reviewCount = bobj.getString("reviewCount");
			if(reviewCount == null || reviewCount.isEmpty()) {
				reviewCountCount=0;
			} else {
				reviewCountCount=Integer.parseInt(reviewCount);
			}
			System.out.println(reviewCountCount);
			hmReview.put(bobj.getString("productmodelname"), reviewCountCount);
		}
		
		return hmReview;
	}
}