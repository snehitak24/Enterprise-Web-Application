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
import java.nio.file.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;
import java.util.*;
import java.text.*;
import com.mongodb.*;


public class DataExplorationUtility extends HttpServlet {
	String usertype=null;
	String querytype=null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Statement stmt = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();


		HtmlUtility htmlUtil = new HtmlUtility(request,out);
try{
		htmlUtil.printHtml("header.html");
		htmlUtil.printHtml("data.html");
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
	}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HashMap<String, Float> productDetails = new HashMap<String, Float>();
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		querytype = request.getParameter("querytype").trim();
		//out.println("Q: "+querytype);
		try{
		if(querytype.equals("op1"))
		{
			totalreviewedperstate();
			response.sendRedirect("/csj/index3.html");
		}
		else if(querytype.equals("op2")){
			//productDetails = objSqlDataStore.SalesBar();
			htmlUtil.printHtml("header.html");
			//htmlUtil.printSalesContent1(productDetails);
			htmlUtil.printHtml("leftnavigationbar.html");
			htmlUtil.printHtml("footer.html");
		}
		else if(querytype.equals("op3")){
			totalreviewedperstateratingf();
			response.sendRedirect("/csj/index4.html");
		}
    else if(querytype.equals("op4")){
      productDetails = avgProductPrices();
      response.sendRedirect("/csj/index0.html");
    }
    else if(querytype.equals("op5")){
      productDetails = totalProductPrices();
      response.sendRedirect("/csj/index1.html");
    }
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}

	public HashMap<String, Float> avgProductPrices()throws SQLException
	{
		HashMap<String, Float> hm = new HashMap<String, Float>();
String st=null;
		try
		{


			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String salesQuery = "select  State,AVG(Orderprice) as Average from smartp.orders group by(State);";
			rs = stmt.executeQuery(salesQuery);

			//objBufferedWriter.write("state"+","+"value");
			 BufferedReader br = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/csj/data4.csv"));
			while(rs.next()){


					String state=rs.getString("State");
					Float price=rs.getFloat("Average");

String replacement=state+","+price;
String content=state+","+"0";

						byte[] bytes = Files.readAllBytes(Paths.get("C:/apache-tomcat-7.0.34/webapps/csj/data4.csv"));
						String s = new String(bytes);
						String str= br.readLine();

						if(s.indexOf(content)!=-1){
							st=s.replace(content,replacement);
						}

					hm.put(state, price);

			}
			FileWriter objFileWriter = new FileWriter("C:/apache-tomcat-7.0.34/webapps/csj/data6.csv", true);
			BufferedWriter objBufferedWriter = new BufferedWriter(objFileWriter);
			objBufferedWriter.write(st);
objBufferedWriter.close();
		}
		catch(Exception ex)
		{
			System.out.println("avgProductPrices()- " + ex.toString());
		}
		finally
		{
			conn.close();

		}

			return hm;
	}

	public HashMap<String, Float> totalProductPrices()throws SQLException
	{
		HashMap<String, Float> hm = new HashMap<String, Float>();
		try
		{
			FileWriter objFileWriter = new FileWriter("C:/apache-tomcat-7.0.34/webapps/csj/data5.csv", true);
			BufferedWriter objBufferedWriter = new BufferedWriter(objFileWriter);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String salesQuery = "select SUM(Orderprice) as Total_Price, State from smartp.orders group by(State);";
			rs = stmt.executeQuery(salesQuery);
			objBufferedWriter.write("state"+","+"value");

			while(rs.next()){


					String state=rs.getString("State");
					Float price=rs.getFloat("Total_Price");

					objBufferedWriter.newLine();
					objBufferedWriter.write(state+","+price);
					hm.put(state, price);

			}
					objBufferedWriter.close();
		}
		catch(Exception ex)
		{
			System.out.println("avgProductPrices()- " + ex.toString());
		}
		finally
		{
			conn.close();
		}
		return hm;
	}

	public static HashMap<String, Integer> totalreviewedperstate()
{
	HashMap<String, Integer> hmReview=new HashMap<String, Integer>();
try{
	FileWriter objFileWriter = new FileWriter("C:/apache-tomcat-7.0.34/webapps/csj/data1.csv", true);
	BufferedWriter objBufferedWriter = new BufferedWriter(objFileWriter);
	MongoClient mango;
	mango = new MongoClient("localhost", 27017);

	DB db = mango.getDB("CustomerReviews");
	DBCollection myReviews = db.getCollection("myReviews");


	objBufferedWriter.write("state"+","+"total"+","+"min"+","+"avg"+","+"max");
	// DBObject group1 = new BasicDBObject();
	// group1.put("_id", new BasicDBObject("retailerstate", "$_id.retailerstate"));
	// group1.put("total" , new BasicDBObject("$sum",1));
	DBObject groupFields = new BasicDBObject("_id", 0);
	groupFields.put("_id", "$retailerstate");
	groupFields.put("total", new BasicDBObject("$sum", 1));
	groupFields.put("avg", new BasicDBObject("$avg", "$reviewrating"));
	groupFields.put("min", new BasicDBObject("$min", "$reviewrating"));
	groupFields.put("max", new BasicDBObject("$max", "$reviewrating"));

	DBObject group = new BasicDBObject("$group", groupFields);

	AggregationOutput aggregate = myReviews.aggregate(group);

	String reviewstate = null;
	int reviewstateCount;
	for (DBObject result : aggregate.results()) {
		BasicDBObject bobj = (BasicDBObject) result;
		System.out.println(bobj.getString("_id"));
		System.out.println(bobj.getString("total"));
		System.out.println(bobj.getString("avg"));
		System.out.println(bobj.getString("min"));
		System.out.println(bobj.getString("max"));

		reviewstate = bobj.getString("_id");
		if(reviewstate == null || reviewstate.isEmpty()) {
			reviewstateCount=Integer.parseInt(bobj.getString("total"));
			reviewstate = "Not Defined";
			hmReview.put(reviewstate, reviewstateCount);
		} else {
			reviewstateCount=Integer.parseInt(bobj.getString("total"));
			hmReview.put(bobj.getString("_id"), reviewstateCount);
			objBufferedWriter.newLine();
			objBufferedWriter.write(bobj.getString("reviewstate")+","+bobj.getString("total")+","+bobj.getString("min")+","+bobj.getString("avg")+","+bobj.getString("max"));
		}
		//System.out.println(reviewstateCount);

	}
	objBufferedWriter.close();
}
	catch(Exception e)
	{}
	return hmReview;
}
	public static HashMap<String, Integer> totalreviewedperstateratingf()
{
	HashMap<String, Integer> hmReview=new HashMap<String, Integer>();
	try{
	FileWriter objFileWriter = new FileWriter("C:/apache-tomcat-7.0.34/webapps/csj/data3.csv", true);
	BufferedWriter objBufferedWriter = new BufferedWriter(objFileWriter);
	MongoClient mango;
	mango = new MongoClient("localhost", 27017);

	DB db = mango.getDB("CustomerReviews");
	DBCollection myReviews = db.getCollection("myReviews");
	objBufferedWriter.write("state"+","+"total"+","+"rating");


	// DBObject group1 = new BasicDBObject();
	// group1.put("_id", new BasicDBObject("retailerstate", "$_id.retailerstate"));
	// group1.put("total" , new BasicDBObject("$sum",1));
	DBObject groupFields = new BasicDBObject("_id", 0);
	groupFields.put("_id",new BasicDBObject("retailerstate","$retailerstate")
							.append("reviewrating","$reviewrating"));
	groupFields.put("total", new BasicDBObject("$sum", 1));
	//groupFields.put("reviewrating", new BasicDBObject("$sum", 1);


	DBObject group = new BasicDBObject("$group", groupFields);
	DBObject match = new BasicDBObject("$match", new BasicDBObject("_id.reviewrating", '5'));


	AggregationOutput aggregate = myReviews.aggregate(group,match);

	String reviewstate = null;
	int reviewstateCount;
	 BufferedReader br = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/csj/data3.csv"));
	for (DBObject result : aggregate.results()) {
		BasicDBObject bobj = (BasicDBObject) result;
		System.out.println(bobj.getString("_id"));
		System.out.println(bobj.getString("total"));
		//System.out.println(bobj.getString("total"));


		byte[] bytes = Files.readAllBytes(Paths.get("C:/apache-tomcat-7.0.34/webapps/csj/data3.csv"));
		String s = new String(bytes);
		if(s.indexOf(bobj.getString("_id.retailerstate")) != -1){
			while ((s = br.readLine()) != null) {
				String replacement=bobj.getString("_id.retailerstate")+","+bobj.getString("total")+","+bobj.getString("_id.reviewrating");
					s.replaceAll(bobj.getString("_id.retailerstate")+","+"0", replacement);
					// do something with the resulting line
			}
		}
else{
		objBufferedWriter.newLine();
		objBufferedWriter.write(bobj.getString("_id.retailerstate")+","+bobj.getString("total")+","+bobj.getString("_id.reviewrating"));
}
		// reviewstate = bobj.getString("_id");
		// if(reviewstate == null || reviewstate.isEmpty()) {
		// 	reviewstateCount=Integer.parseInt(bobj.getString("total"));
		// 	reviewstate = "Not Defined";
		// 	hmReview.put(reviewstate, reviewstateCount);
		// } else {
		// 	reviewstateCount=Integer.parseInt(bobj.getString("total"));
		// 	hmReview.put(bobj.getString("_id"), reviewstateCount);
		// }
		//System.out.println(reviewstateCount);
	}
	objBufferedWriter.close();
}
catch(Exception e)
{}
		return hmReview;
}
}
