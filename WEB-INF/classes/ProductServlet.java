import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;
import java.sql.*;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class PhoneServlet extends HttpServlet 
{
	public static HashMap<String, ProductCatalog> hm = new HashMap<String, ProductCatalog>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printProductContent("phone");
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try {

			
			
			hmUsers = objSqlDataStore.selectUserDetails();
			
			if (!hmUsers.containsKey(userID)) 
			{
				response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Servlet Result</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>" + "Login Failed. Incorrect User ID" + "</h2>");
				out.println("<br/>");
				out.println("<a href='home'> Home Page </a>");
				out.println("<br/>");
				out.println("<a href='login'> Login Page </a>");
				out.println("</body>");
				out.println("</html>");
				out.close();

			} 
			else 
			{
				Users user = hmUsers.get(userID);
				
				if(password.equals(user.getPassword()) && userType.equalsIgnoreCase(user.getUsertype()))
				{
					HttpSession session = request.getSession();
					session.setAttribute("username", user.getFirstName());
					session.setAttribute("usertype", userType);
					response.sendRedirect("home");					
				}
				else
				{
					response.setContentType("text/html");
					java.io.PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Login Servlet Result</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>" + "Login Failed. Incorrect Credentials" + "</h2>");
					out.println("<br/>");
					out.println("<a href='home'> Home Page </a>");
					out.println("<br/>");
					out.println("<a href='login'> Login Page</a>");
					out.println("</body>");
					out.println("</html>");
					out.close();
				}				
			}			
		}
		catch(Exception ex)
		{
			
		}
}