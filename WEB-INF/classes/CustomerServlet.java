import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;


public class CustomerServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException	{	
	
			response.setContentType("text/html");
			
			PrintWriter out= response.getWriter();
			ServletContext sc=request.getSession().getServletContext();
			String username = request.getSession().getAttribute("username").toString();	
			String usertype = request.getSession().getAttribute("usertype").toString();
		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		//htmlUtil.printProductContent("laptop");
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
		
	
	/*		
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
      "<li style='float:right;'><a href='/csj/cart' style='font-size: 12px'>Cart</a></li>"+
			"<li style='float:right;'><a href='checkout.html' style='font-size: 12px'>Checkout</a></li>"+
			"<li style='float:right;'><a href='/csj/logout'>Logout</a></li>"+			
			"<li style='float:right;'>Welcome, "+username+usertype+"</a></li>");			
				
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
							"<li><a href='/csj/phone'>Phones</a></li>"+
							"<li><a href='/csj/speaker'>Speakers</a></li>"+
							"<li><a href='/csj/laptop'>Laptops</a></li>"+
							"<li><a href='/csj/headphone'>HeadPhones</a></li>"+
							"<li><a href='/csj/hdd'>ExternalHDD</a></li>"+
							"<li><a href='/csj/smartwatch'>SmartWatches</a></li>"+
							"<li><a href='/csj/accessory'>Accessories</a></li>"+
						"</ul>"+
					"</li>"+
				
					"<li>"+
						"<h4>About us</h4>"+
						"<ul>"+
							"<li class='text'>"+
								"<p style='margin: 0;'>Best Deal is an ecommerce company which sells different types of electronics products such as Smartphones, Laptops, Speakers and Televisions. Enjoy your shopping.</p>"+
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
			"<li>Smart Portables</li>"+
			"<li>Chicago, IL, US</li>"+
			"<li>+1(312)753-8501</li>"+
		"</ul>"+		
		"<div class='clear'></div>"+
	"</div>"+	
" </footer>"+
"</div>"+
"</body>"+
"</html>");	*/
	}
}