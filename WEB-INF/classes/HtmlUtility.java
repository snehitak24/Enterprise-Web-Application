import java.io.IOException;
import java.sql.SQLException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;


public class HtmlUtility {

	private HttpServletRequest request = null;
	private PrintWriter out = null;
	private String pHtml = null;
	public String username = null;
	public String cartcount = null;
	public String usertype = null;
	public  HashMap<String, ProductCatalog> hm = null;
	ProductCatalog pc = null;


	public HtmlUtility(HttpServletRequest request, PrintWriter out)
	{
		this.request = request;
		this.out = out;
	}

	public String fetchHtmlContent(String filePath)
	{
		String htmlContent = null;
		String htmlLine;
		StringBuffer strBuffer = new StringBuffer();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while((htmlLine = br.readLine()) != null) {
				strBuffer.append(htmlLine);
			}
			htmlContent = strBuffer.toString();
		} catch (IOException e) {
			htmlContent = "Error in reading file " + filePath;
		}
		return htmlContent;
	}

	public String modifyHtml(String oldHtml, String newHtml, String toChange) {
		oldHtml = oldHtml.replaceAll(toChange, newHtml);
		return oldHtml;
	}

	public void printHtml(String fileName)
	{
		if(request.getSession().getAttribute("username") != null)
		{
			username = request.getSession().getAttribute("username").toString();
		}

		if(request.getSession().getAttribute("cartcount") != null)
		{
			cartcount = request.getSession().getAttribute("cartcount").toString();
		}

		if(request.getSession().getAttribute("usertype") != null)
		{
			usertype = request.getSession().getAttribute("usertype").toString();
		}

		if(fileName.equalsIgnoreCase("header.html"))
		{
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.HEADER_FILEPATH));
			StringBuilder dynamicHtml = new StringBuilder();

			dynamicHtml.append("<li><a href='home'>Home</a></li>"); //Check the selected class
			dynamicHtml.append("<li><a href='phone'>Phones</a></li>");
			dynamicHtml.append("<li><a href='speaker'>Speakers</a></li>");
			dynamicHtml.append("<li><a href='laptop'>Laptops</a></li>");
			dynamicHtml.append("<li><a href='headphone'>HeadPhones</a></li>");
			dynamicHtml.append("<li><a href='hdd'>ExternalHDD</a></li>");
			dynamicHtml.append("<li><a href='smartwatch'>SmartWatches</a></li>");
			dynamicHtml.append("<li><a href='trending'>Trending</a></li>");
			if(cartcount != null) {
				dynamicHtml.append("<li style='float: right;'><a href='cart'>Cart("+cartcount+")</a></li>");
			}
			else {
				dynamicHtml.append("<li style='float: right;'><a href='cart'>Cart</a></li>");
			}

			if(username == null) {
				dynamicHtml.append("<li style='float: right;'><a href='signup'>Signup</a></li>");
				dynamicHtml.append("<li style='float: right;'><a href='login'>Login</a></li>");
			}
			else {
				dynamicHtml.append("<li style='float:right;'><a href='logout'>Logout</a></li>");
				dynamicHtml.append("<li style='float:right;font-weight: bold;'>Welcome, "+ username +"</li>");
			}


			pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#HeaderList");
			out.append(pHtml);
		}
		else if(fileName.equalsIgnoreCase("leftnavigationbar.html"))
		{
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.LEFTNAVIGATIONBAR_FILEPATH));
			StringBuilder dynamicHtml = new StringBuilder();

			if(username != null)
			{
				if(usertype.equalsIgnoreCase("storemanager"))
				{
					dynamicHtml.append("<li><a href='addproduct'>Add Product</a></li>");
					dynamicHtml.append("<li><a href='updateproduct'>Update Product</a></li>");
					dynamicHtml.append("<li><a href='deleteproduct'>Delete Product</a></li>");
					dynamicHtml.append("<li><a href='dataanalytics'>Data Analytics</a></li>");
					dynamicHtml.append("<li><a href='inventory'>Inventory</a></li>");
					dynamicHtml.append("<li><a href='salesreport'>Sales Report</a></li>");
					dynamicHtml.append("<li><a href='dataexplore'>Data Exploration</a></li>");
				}
				else if(usertype.equalsIgnoreCase("salesman"))
				{
					dynamicHtml.append("<li><a href='user?action=newcustomer'>Create new Customer</a></li>");
				}
				else
				{
					if(usertype.equalsIgnoreCase("customer"))
					{
						dynamicHtml.append("<li><a href='orders'>My Orders</a></li>");
					}
					dynamicHtml.append("<li><a href='phone'>Phones</a></li>");
					dynamicHtml.append("<li><a href='speaker'>Speakers</a></li>");
					dynamicHtml.append("<li><a href='laptop'>Laptops</a></li>");
					dynamicHtml.append("<li><a href='headphone'>HeadPhones</a></li>");
					dynamicHtml.append("<li><a href='hdd'>ExternalHDD</a></li>");
					dynamicHtml.append("<li><a href='smartwatch'>SmartWatches</a></li>");
					dynamicHtml.append("<li><a href='accessory'>Accessories</a></li>");
				}
			}
			else
			{
				dynamicHtml.append("<li><a href='phone'>Phones</a></li>");
				dynamicHtml.append("<li><a href='speaker'>Speakers</a></li>");
				dynamicHtml.append("<li><a href='laptop'>Laptops</a></li>");
				dynamicHtml.append("<li><a href='headphone'>HeadPhones</a></li>");
				dynamicHtml.append("<li><a href='hdd'>ExternalHDD</a></li>");
				dynamicHtml.append("<li><a href='smartwatch'>SmartWatches</a></li>");
				dynamicHtml.append("<li><a href='accessory'>Accessories</a></li>");
			}

			pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#NavigationPanel");
			out.append(pHtml);
		}
		else if(fileName.equalsIgnoreCase("home_cara.html")){
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.HOME_CARA));
			out.append(fetchHtml.toString());
		}

		else if(fileName.equalsIgnoreCase("inventory.html")){
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.INVENTORY));
			out.append(fetchHtml.toString());
		}

		else if(fileName.equalsIgnoreCase("sales.html")){
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.SALES));
			out.append(fetchHtml.toString());
		}
		else if(fileName.equalsIgnoreCase("visualization-chart-demo.html")){
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.BARCHART));
			out.append(fetchHtml.toString());
		}
		else if(fileName.equalsIgnoreCase("data.html")){
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.DATA));
			out.append(fetchHtml.toString());
		}
		else
		{
			//Footer.html case
			StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.FOOTER_FILEPATH));
			out.append(fetchHtml.toString());
		}
	}

	public void printAddProductContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.ADD_PRODUCT_CONTENT_FILEPATH));
		pHtml = fetchHtml.toString();
		out.append(pHtml);
	}


	public void printDeleteProductContent()	throws IOException, SQLException
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.DELETE_PRODUCT_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		hm = WfCache.getAllProducts();
		pc = new ProductCatalog();

		for(String key: hm.keySet())
		{
			pc = hm.get(key);

			dynamicHtml.append("<tr>");
			dynamicHtml.append("<td>");
			dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("<td align='center'>");
			dynamicHtml.append("<form method = 'post' action = 'deleteproduct'>");
			dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
			dynamicHtml.append("<input type = 'submit' name = 'Delete' value = 'Delete'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("</tr>");
		}
		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#ProductsList");
		out.append(pHtml);
	}

	public void printUpdateProductContent()	throws IOException, SQLException
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.UPDATE_PRODUCT_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		hm = WfCache.getAllProducts();
		pc = new ProductCatalog();

		for(String key: hm.keySet())
		{
			pc = hm.get(key);

			dynamicHtml.append("<tr>");
			dynamicHtml.append("<td>");
			dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("<td align='center'>");
			dynamicHtml.append("<form method = 'post' action = 'updateproduct'>");
			dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
			dynamicHtml.append("<input type = 'submit' name = 'Update' value = 'Update'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("</tr>");
		}
		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#ProductsList");
		out.append(pHtml);
	}

	public void printUpdateProductContent1(String productId)throws IOException, SQLException
	{
		StringBuilder dynamicHtml = new StringBuilder();

		hm = WfCache.getAllProducts();
		pc = new ProductCatalog();
		pc = hm.get(productId);

		dynamicHtml.append("<section id='content'>");
		dynamicHtml.append("<table>");
		dynamicHtml.append("<form method='post' action='updateproduct1'>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Product ID:</td><td><input type='text' name='productid'	value='"+pc.getId()+"' readonly></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Category:</td><td><input type='text' name='category' value='"+pc.getCategory()+"' readonly></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Product Name:</td><td><input type='text' name='productname' value='"+pc.getName()+"' ></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Price:</td><td><input type='text' name='price' value='"+pc.getPrice()+"' ></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Image Path:</td> <td><input type='text' name='imagepath' value='"+pc.getImagepath()+"' ></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Condition:</td><td><input type='text' name='condition'	value='"+pc.getCondition()+"' ></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>Retailer:</td><td><input type='text' name='retailer' value='"+pc.getRetailer()+"' ></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td align='center' colspan='2'><input type='submit' value='Update1'></td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("</form>");
		dynamicHtml.append("</table>");
		dynamicHtml.append("</section>");

		pHtml = dynamicHtml.toString();
		out.append(pHtml);
	}

	public void printHomeContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.HOME_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		dynamicHtml.append("<h1 style='float: center; color:red'>Welcome to SmartPortables Online Shopping</h1>");
		dynamicHtml.append("<img src='images/phone/iphone2.jpg' alt='pic1' width='200' height='250' style='float:left;'/>");
		dynamicHtml.append("<img src='images/phone/samsung.jpg' alt='pic2' width='200' height='250' style='float:center;'/>");
		dynamicHtml.append("<img src='images/laptop/dell.jpg' alt='pic3' width='200' height='250' style='float:right;'/>");

		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#Content");
		out.append(pHtml);
	}

	public void printProductContent(String productType, String productId)
	{
		try{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.PRODUCT_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		if(productType.equalsIgnoreCase("phone"))
		{
			hm = WfCache.isPhoneCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("smartwatch"))
		{
			hm = WfCache.isSmartWatchCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("laptop"))
		{
			hm = WfCache.isLaptopCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("hdd"))
		{
			hm = WfCache.isExternalCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("headphone"))
		{
			hm = WfCache.isHeadPhoneCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("speaker"))
		{
			hm = WfCache.isSpeakerCatalogFetched();
		}
		else
		{
			hm = WfCache.isAccessoryCatalogFetched();
		}

		pc = new ProductCatalog();

		if(productId == null || productId.equals(""))
		{
			//out.println("p"+productId);
			for(String key: hm.keySet())
			{
				pc = hm.get(key);

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<img src = '"+ pc.getImagepath() +"' width = '250' height = '250' alt = 'phone'>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
				dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
				dynamicHtml.append("<p> Condition: "+ pc.getCondition()+ "</p>");
				dynamicHtml.append("<p> Price: "+ pc.getPrice()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center'>");
				dynamicHtml.append("<form method = 'post' action = '"+productType+"'>");
				dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = '"+productType+"' value = 'Add to Cart'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("<form method = 'get' action = 'writereview'>");
				dynamicHtml.append("<input type='hidden' name = 'productid1' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Write Review' value = 'Write Review'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("<form method = 'get' action = 'readreview'>");
				dynamicHtml.append("<input type='hidden' name = 'productname' value='"+pc.getName()+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Read Review' value = 'Read Review'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
			}
		}
		else
		{
			//out.println("p"+productId);
			productId = productId.toUpperCase();
			pc = hm.get(productId);

			dynamicHtml.append("<tr>");
			dynamicHtml.append("<td>");
			dynamicHtml.append("<img src = '"+ pc.getImagepath() +"' width = '250' height = '250' alt = 'phone'>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("<td>");
			dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
			dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
			dynamicHtml.append("<p> Condition: "+ pc.getCondition()+ "</p>");
			dynamicHtml.append("<p> Price: "+ pc.getPrice()+ "</p>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("<td align='center'>");
			dynamicHtml.append("<form method = 'post' action = '"+productType+"'>");
			dynamicHtml.append("<input type='hidden' name = 'productid' value='"+productId+"'/>");
			dynamicHtml.append("<input type = 'submit' name = '"+productType+"' value = 'Add to Cart'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("<form method = 'get' action = 'writereview'>");
			dynamicHtml.append("<input type='hidden' name = 'productid1' value='"+productId+"'/>");
			dynamicHtml.append("<input type = 'submit' name = 'Write Review' value = 'Write Review'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("<form method = 'get' action = 'readreview'>");
			dynamicHtml.append("<input type='hidden' name = 'productname' value='"+pc.getName()+"'/>");
			dynamicHtml.append("<input type = 'submit' name = 'Read Review' value = 'Read Review'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("</tr>");
		}
		pHtml = modifyHtml(fetchHtml.toString(),productType,"#productType");
		pHtml = modifyHtml(pHtml,dynamicHtml.toString(),"#ProductsList");
		out.append(pHtml);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void printSignupContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.SIGNUP_CONTENT_FILEPATH));
		pHtml = fetchHtml.toString();
		out.append(pHtml);
	}

	public void printDataContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.DATA_CONTENT_FILEPATH));
		pHtml = fetchHtml.toString();
		out.append(pHtml);
	}

	public void printLoginContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.LOGIN_CONTENT_FILEPATH));
		pHtml = fetchHtml.toString();
		out.append(pHtml);
	}



	public void printProductContent(String productType)	throws IOException
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.PRODUCT_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		if(productType.equalsIgnoreCase("phone"))
		{
			hm = WfCache.isPhoneCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("speaker"))
		{
			hm = WfCache.isSpeakerCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("hdd"))
		{
			hm = WfCache.isExternalCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("laptop"))
		{
			hm = WfCache.isLaptopCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("headphone"))
		{
			hm = WfCache.isHeadPhoneCatalogFetched();
		}
		else if(productType.equalsIgnoreCase("smartwatch"))
		{
			hm = WfCache.isSmartWatchCatalogFetched();
		}
		else
		{
			hm = WfCache.isAccessoryCatalogFetched();
		}

		pc = new ProductCatalog();

		for(String key: hm.keySet())
			{
				pc = hm.get(key);

				System.out.println(pc.getImagepath());

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<img src = '"+ pc.getImagepath() +"' width = '250' height = '250' alt = 'phone'>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
				dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
				dynamicHtml.append("<p> Condition: "+ pc.getCondition()+ "</p>");
				dynamicHtml.append("<p> Price: "+ pc.getPrice()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center'>");
				dynamicHtml.append("<form method = 'post' action = '"+productType+"'>");
				dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = '"+productType+"' value = 'Add to Cart'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("<form method = 'get' action = 'writereview'>");
				dynamicHtml.append("<input type='hidden' name = 'productid1' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Write Review' value = 'Write Review'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("<form method = 'get' action = 'readreview'>");
				dynamicHtml.append("<input type='hidden' name = 'productname' value='"+pc.getName()+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Read Review' value = 'Read Review'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
			}
			//System.out.println(dynamicHtml.toString());
		pHtml = modifyHtml(fetchHtml.toString(),productType,"#productType");
		//System.out.println(pHtml);


		pHtml = modifyHtml(pHtml,dynamicHtml.toString(),"#ProductsList");
		System.out.println(pHtml);
		out.append(pHtml);
	}

	public void printCartContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.CART_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		hm = WfCache.showCart();

		pc = new ProductCatalog();

		if(hm != null)
		{
			for(String key: hm.keySet())
			{
				pc = hm.get(key);

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p> Price: "+ pc.getPrice()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p> Quantity: "+ pc.getQuantity()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<form method = 'post' action = 'cart'>");
				dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Delete' value = 'Delete Item'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
			}
			dynamicHtml.append("<tr>");
			dynamicHtml.append("<td align='center' colspan='5'>");
			dynamicHtml.append("<form method = 'get' action = 'checkout'>");
			dynamicHtml.append("<input type = 'submit' name = 'Checkout' value = 'Checkout'>");
			dynamicHtml.append("</form>");
			dynamicHtml.append("</td>");
			dynamicHtml.append("</tr>");
		}
		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#CartList");
		out.append(pHtml);
	}

	public void printInventoryContent(HashMap<String, ProductCatalog> hm)
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.inventoryop1));
		StringBuilder dynamicHtml = new StringBuilder();
		pc = new ProductCatalog();
		//out.println("hm");
		dynamicHtml.append("<tr>");
		dynamicHtml.append("<th align='center'><b>Product name</b></th>");
		dynamicHtml.append("<th align='center'><b>Product price</b></th>");
		//dynamicHtml.append("<th align='center'><b>Product quantity</b></th></tr>");

		if(hm != null)
		{
			int count=0;
			for(String key: hm.keySet())
			{
				pc = hm.get(key);
				if(pc.getonSale()!=null && count==0){
					dynamicHtml.append("<th align='center'><b>Product on sale</b></th></tr>");
					count=1;
				}
				else if(pc.getManufacturerRebate()>0 && count==0){
					dynamicHtml.append("<th align='center'><b>Manufacture Rebate in %</b></th></tr>");
					count=1;
				}
				else if(count==0){
					dynamicHtml.append("<th align='center'><b>Product quantity</b></th></tr>");
					count=1;
				}

			}
		}



		if(hm != null)
		{

			for(String key: hm.keySet())
			{
				pc = hm.get(key);

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td align='center'>");
				dynamicHtml.append("<p>"+ pc.getName() +"</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center'>");
				dynamicHtml.append("<p>"+ pc.getPrice()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center'>");

				if(pc.getonSale()!=null)
					dynamicHtml.append("<p>"+ pc.getonSale()+ "</p>");
				else if(pc.getManufacturerRebate()>0)
					dynamicHtml.append("<p>"+ pc.getManufacturerRebate()+ "</p>");
				else{
				dynamicHtml.append("<p>"+ pc.getorgQuantity()+ "</p>");
				}

				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
			}
		}
		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#Inventory");
		out.append(pHtml);
	}
//--------------------------
		public void printSalesContent(HashMap<String, ProductCatalog> hm)
			{
				StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.salesop1));
				StringBuilder dynamicHtml = new StringBuilder();
				pc = new ProductCatalog();
				//out.println("hm");
				dynamicHtml.append("<tr>");
				dynamicHtml.append("<th align='center'><b>Product name</b></th>");
				dynamicHtml.append("<th align='center'><b>Product price</b></th>");
				//dynamicHtml.append("<th align='center'><b>Product quantity</b></th></tr>");

				if(hm != null)
				{
					int count=0;
					for(String key: hm.keySet())
					{
						pc = hm.get(key);
						/*if(pc.getonSale()!=null && count==0){
							dynamicHtml.append("<th align='center'><b>Product sales quantity</b></th></tr>");
							count=1;
						}
						else if(pc.getManufacturerRebate()>0 && count==0){
							dynamicHtml.append("<th align='center'><b>Manufacture Rebate in %</b></th></tr>");
							count=1;
						}
						else*/ if(count==0){
							dynamicHtml.append("<th align='center'><b>Product sales quantity</b></th></tr>");
							count=1;
						}

					}
				}



				if(hm != null)
				{

					for(String key: hm.keySet())
					{
						pc = hm.get(key);

						dynamicHtml.append("<tr>");
						dynamicHtml.append("<td align='center'>");
						dynamicHtml.append("<p>"+ pc.getName() +"</p>");
						dynamicHtml.append("</td>");
						dynamicHtml.append("<td align='center'>");
						dynamicHtml.append("<p>"+ pc.getPrice()+ "</p>");
						dynamicHtml.append("</td>");
						dynamicHtml.append("<td align='center'>");

						/*if(pc.getonSale()!=null)
							dynamicHtml.append("<p>"+ pc.getonSale()+ "</p>");
						else if(pc.getManufacturerRebate()>0)
							dynamicHtml.append("<p>"+ pc.getManufacturerRebate()+ "</p>");
						else*/{
						dynamicHtml.append("<p>"+ pc.getQuantity()+ "</p>");
						}

						dynamicHtml.append("</td>");
						dynamicHtml.append("</tr>");
					}
				}
				pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#Sales");
				out.append(pHtml);
			}


		public void printEverydaySalesContent(HashMap<String, ArrayList<ProductCatalog>> hm)
		{
				StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.salesop1));
				StringBuilder dynamicHtml = new StringBuilder();
				pc = new ProductCatalog();
				//out.println("hm");
				dynamicHtml.append("<tr>");
				dynamicHtml.append("<th align='center'><b>Date</b></th>");
				dynamicHtml.append("<th align='center'><b>Product name</b></th>");
				//dynamicHtml.append("<th align='center'><b>Product price</b></th>");
				//dynamicHtml.append("<th align='center'><b>Product quantity</b></th></tr>");
				dynamicHtml.append("<th align='center'><b>Product sales quantity</b></th></tr>");




				if(hm != null)
				{

					for(String key: hm.keySet())
					{

						ArrayList<ProductCatalog> list = hm.get(key);

						for(ProductCatalog p: list){
						dynamicHtml.append("<tr>");
						dynamicHtml.append("<td align='center'>");
						dynamicHtml.append("<p>"+ key +"</p>");
						dynamicHtml.append("</td>");
						dynamicHtml.append("<td align='center'>");
						dynamicHtml.append("<p>"+ p.getName() +"</p>");
						dynamicHtml.append("</td>");
						dynamicHtml.append("<td align='center'>");
						dynamicHtml.append("<p>"+ p.getQuantity()+ "</p>");
						dynamicHtml.append("</td>");
						dynamicHtml.append("</tr>");
						}
					}
				}
				pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#Sales");
				out.append(pHtml);

		}
	public void printCheckoutContent()
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.CHECKOUT_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();
		float totalCartPrice = 0;
		float totalProductPrice = 0;

		if(username != null && usertype.equalsIgnoreCase("customer"))
		{
			hm = WfCache.showCart();
			pc = new ProductCatalog();

			if(hm != null)
			{
				for(String key: hm.keySet())
				{
					pc = hm.get(key);
					totalProductPrice = pc.getPrice() * pc.getQuantity();
					totalCartPrice = totalCartPrice + totalProductPrice;

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<p>Model: "+ pc.getName() +"</p>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<p>Price: "+ totalProductPrice + "</p>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<p>Quantity: "+ pc.getQuantity()+ "</p>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");
				}

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td align='center' colspan='4'>");
				dynamicHtml.append("<p>Subtotal: "+ totalCartPrice +"</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");

				dynamicHtml.append("<form method = 'post' action = 'checkout'  class='logi'>");
				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td align='center' colspan='1'>");
				dynamicHtml.append("<p>CardNumber: </p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center' colspan='3'>");
				dynamicHtml.append("<input type='text' name='cardnumber' required>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td align='center' colspan='1'>");
				dynamicHtml.append("<p>State: </p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td align='center' colspan='3'>");
				dynamicHtml.append("<input type='text' name='state' required>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");

				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td align='center' colspan='4'>");
				dynamicHtml.append("<input type = 'submit' name = 'Buy' value = 'Buy'>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
				dynamicHtml.append("</form>");
			}
		}
		else
		{
			dynamicHtml.append("<h1 style='float: center; color:red'>Please login</h1>");
			dynamicHtml.append("<a href='login'> Login Page </a>");
			dynamicHtml.append("<br/>");
			dynamicHtml.append("<a href='signup'> Signup Page </a>");
		}
		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#Checkout");
		out.append(pHtml);
	}


	public void printOrderContent(HashMap<String, ProductCatalog> oDetails)
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.ORDER_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();



		if(oDetails != null)
		{
			for(String key: oDetails.keySet())
			{
				pc = new ProductCatalog();
				pc = oDetails.get(key);


				dynamicHtml.append("<tr>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p> Product Brought: "+ pc.getName() +"</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p>Sold by: "+ pc.getRetailer()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p> Price: "+ pc.getPrice()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<p> Quantity Brought: "+ pc.getQuantity()+ "</p>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("<td>");
				dynamicHtml.append("<form method = 'post' action = 'cart'>");
				dynamicHtml.append("<input type='hidden' name = 'productid' value='"+key+"'/>");
				dynamicHtml.append("<input type = 'submit' name = 'Delete' value = 'Delete order'>");
				dynamicHtml.append("</form>");
				dynamicHtml.append("</td>");
				dynamicHtml.append("</tr>");
			}

			pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#UserOrder");
			out.append(pHtml);
		}
	}


	public void printWriteReviewContent(ProductCatalog pc, String category)
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.WRITEREVIEW_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();

		dynamicHtml.append("<form method = 'post' action = 'writereview'>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Product Model Name: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='productmodelname' value='"+pc.getName()+"' readonly>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Category: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='category' value='"+category+"' readonly>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Product Price: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='productprice' value='"+pc.getPrice()+"' readonly>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Retailer Name: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='retailername' value='"+pc.getRetailer()+"' readonly>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Retailer Zip: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='retailerzip'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Retailer City: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='retailercity'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Retailer State: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='retailerstate'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Product on Sale: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='productonsale'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Manufacturer Name: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='manufacturername'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Manufacturer Rebate: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='manufacturerrebate'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("UserID: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='userid'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("UserAge: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='userage'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("UserGender: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='usergender'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("UserOccupation: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='useroccupation'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Review Rating: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='reviewrating'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("Review Text: ");
		dynamicHtml.append("</td>");
		dynamicHtml.append("<td>");
		dynamicHtml.append("<input type='text' name='reviewtext'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");

		dynamicHtml.append("<tr>");
		dynamicHtml.append("<td align='center' colspan='2'>");
		dynamicHtml.append("<input type = 'submit' name = 'Submit Review' value = 'Submit Review'>");
		dynamicHtml.append("</td>");
		dynamicHtml.append("</tr>");
		dynamicHtml.append("</form>");

		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#WriteReview");
		out.append(pHtml);
	}

	public void printReadReviewContent(HashMap<String, Review> hmReview)
	{
		StringBuilder fetchHtml = new StringBuilder(fetchHtmlContent(Constants.READREVIEW_CONTENT_FILEPATH));
		StringBuilder dynamicHtml = new StringBuilder();
		Review rv = null;

		if(hmReview != null)
			{
				for(String key: hmReview.keySet())
				{
					rv = hmReview.get(key);

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Product Model Name: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='productmodelname' value='"+rv.getProductModelName()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Category: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='category' value='"+rv.getCategory()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Product Price: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='productprice' value='"+rv.getProductPrice()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Retailer Name: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='retailername' value='"+rv.getRetailerName()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Retailer Zip: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='retailerzip' value='"+rv.getRetailerZip()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Retailer City: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='retailercity' value='"+rv.getRetailerCity()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Retailer State: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='retailerstate' value='"+rv.getRetailerState()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Product on Sale: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='productonsale' value='"+rv.getProductOnSale()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Manufacturer Name: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='manufacturername' value='"+rv.getManufacturerName()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Manufacturer Rebate: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='manufacturerrebate' value='"+rv.getManufacturerRebate()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("UserID: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='userid' value='"+rv.getUserID()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("UserAge: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='userage' value='"+rv.getUserAge()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("UserGender: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='usergender' value='"+rv.getUserGender()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("UserOccupation: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='useroccupation' value='"+rv.getUserOccupation()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Review Rating: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='reviewrating' value='"+rv.getReviewRating()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					dynamicHtml.append("<tr>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("Review Text: ");
					dynamicHtml.append("</td>");
					dynamicHtml.append("<td>");
					dynamicHtml.append("<input type='text' name='reviewtext' value='"+rv.getReviewText()+"' readonly>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");

					/* dynamicHtml.append("<tr>");
					dynamicHtml.append("<td align='center' colspan='2'>");
					dynamicHtml.append("<input type = 'submit' name = 'Submit Review' value = 'Submit Review'>");
					dynamicHtml.append("</td>");
					dynamicHtml.append("</tr>");	 */
				}
			}

		pHtml = modifyHtml(fetchHtml.toString(),dynamicHtml.toString(),"#ReadReview");
		out.append(pHtml);
	}



}
