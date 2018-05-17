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


public class DataAnalyticsServlet extends HttpServlet {
	String usertype=null;
	String username=null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		usertype="storemanager";
		username="Storemanager";
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printDataContent();
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
		
		if(username.equals("Storemanager") && usertype.equals("storemanager"))
					{						
						HttpSession session=request.getSession(); 
						//session.setAttribute("username",username); 
						//session.setAttribute("usertype", usertype);
						
					}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/csj/dataanalysis");
	}

}