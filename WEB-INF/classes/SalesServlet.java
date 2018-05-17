import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;


public class SalesServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	{	
	
			response.setContentType("text/html");
			PrintWriter out= response.getWriter();
	
			ServletContext sc=request.getSession().getServletContext();
			String username = request.getSession().getAttribute("userid").toString();	
			
	String docType = "<!doctype html>\n";
		out.println(docType+"<html>"+"<head>"+"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
			"<title>Smart Portables</title>"+
			"<link rel='stylesheet' href='styles.css' type='text/css' />"+
			"</head>"+"<body>"+
	"<div id='container'>"+
		"<header>"+
			
			"<h1><a href='/'>Smart<span>Portables</span></a></h1>"+"<h2>The best place for buying portables</h2>"+
		"</header>"+
		"<nav>"+
			"<ul>"+	
				"<li><a href='index.html'>Home</a></li>"+
				"<li style='float:right;'><a href='/csj/logout'>Logout</a></li>"+			
				"<li style='float:right;'>Welcome, "+username+"</a></li>");			
				
out.println(" </ul>"+
		"</nav>"+

		"<div id='body'>"+
			"<section id='content'>"+				
			"</section>"+
			"<aside class='sidebar'>"+
				 "<ul>"+
					"<li>"+
						"<h4>Products</h4>"+
						"<ul>"+
							"<li><a href='index.html'>Home Page</a></li>"+
							"<li><a href='registercustomer.html'>Add Customer account</a></li>"+
							"<li><a href='#'>Add Customer Orders</a></li>"+
              "<li><a href='#'>Update Customer Order</a></li>"+ 
						"<li><a href='#'>Delete Customer Order</a></li>"+		
						"</ul>"+
					"</li>"+
				
					"<li>"+
						"<h4>About us</h4>"+
						"<ul>"+
							"<li class='text'>"+
								"<p style='margin: 0;'>Best Deal is an ecommerce company which sells different types of electronics products such as Smartphones, Laptops, Speakers and External HDD. Enjoy your shopping.</p>"+
							"</li>"+
						"</ul>"+
					"</li>"+			
				"</ul>"+	
			" </aside>"+
			"<div class='clear'></div>"+
		"</div>"+		
"<footer>"+
	"<div class='footer-content'>"+
		"<ul class='endfooter'>"+
			"<li><h4>Contact us</h4></li>"+
			"<li>Best Deal</li>"+
			"<li>Chicago, IL, US</li>"+
			"<li>Email: ngupta32@hawk.iit.edu</li>"+
			"<li>+1(312)753-8501</li>"+
		"</ul>"+		
		"<div class='clear'></div>"+
	"</div>"+	
" </footer>"+
"</div>"+
"</body>"+
"</html>");	
	}
}