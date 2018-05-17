import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class CheckoutServlet extends HttpServlet
{
	public HashMap<String, ProductCatalog> hm = null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	ProductCatalog pc = null;
	String username = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HtmlUtility htmlUtil = new HtmlUtility(request,out);
		htmlUtil.printHtml("header.html");
		htmlUtil.printCheckoutContent();
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		try
		{
			String creditCard = request.getParameter("cardnumber");
			String state = request.getParameter("state");
			hm = WfCache.showCart();

			if(request.getSession().getAttribute("username") != null)
			{
				username = request.getSession().getAttribute("username").toString();
			}

			if(objSqlDataStore.insertOrderDetails(hm,state,creditCard,username))
			{
				WfCache.clearCart();

				if(request.getSession().getAttribute("cartcount") != null)
				{
					request.getSession().removeAttribute("cartcount");
				}

				response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Order Successful</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>" + "Order Successfully placed." + "</h2>");
				out.println("<br/>");
				out.println("<a href='home'> Go to Home Page </a>");
				out.println("</body>");
				out.println("</html>");
				out.close();
			}
			else
			{
				response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Order Unsuccessful</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>" + "Order not placed." + "</h2>");
				out.println("<br/>");
				out.println("<a href='cart'> Go to cart </a>");
				out.println("</body>");
				out.println("</html>");
				out.close();
			}
		}
		catch(Exception ex)
		{

		}
	}
}
