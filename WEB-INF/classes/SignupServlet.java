import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet {

	HashMap<String, Users> hmUsers = null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	boolean flag = true;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printSignupContent();
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException	
	{
		try
		{
			Users objUser = new Users();
			//HashMap<String, String> hmUsers = new HashMap<String, String>();
			
			String usrid = request.getParameter("email").toLowerCase().trim();
			//String userType = request.getParameter("customer").toLowerCase().trim();
			objUser.setFirstName(request.getParameter("firstname"));
			objUser.setLastName(request.getParameter("lastname"));
			objUser.setuserId(usrid);
			objUser.setPassword(request.getParameter("password"));
			objUser.setPhone(request.getParameter("phone"));
			objUser.setUsertype("customer");
			objUser.setAddress(request.getParameter("address"));
			objUser.setCity(request.getParameter("city"));
			objUser.setCountry(request.getParameter("country"));
			objUser.setState(request.getParameter("state"));
			objUser.setZipcode(request.getParameter("zipcode"));

			hmUsers = objSqlDataStore.selectUserDetails();
			
			//hmUsers = readFile(Constants.UserDetailFile);
			
			
			for(String key : hmUsers.keySet())
			{
				if((key.equals(usrid)))
				{
					response.setContentType("text/html");
					java.io.PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Signup Servlet Result</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>" + "Signup Failed. User already exists!!" + "</h2>");
					out.println("<br/>");
					out.println("<a href='home'> Home Page </a>");
					out.println("</body>");
					out.println("</html>");
					out.close();
					flag = false;
				}
			}			
			if(flag)
			{
				//String UserData = objUser.getFirstName().trim() + "," + objUser.getLastName().trim() + "," + objUser.getuserId().trim() + ","
				//	+ objUser.getPassword().trim() + "," + objUser.getPhone().trim()+","+Constants.CUSTOMER;
				//writeFile(Constants.UserDetailFile, UserData);
				objSqlDataStore.insertUserDetails(objUser);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head> </head>");
				out.println("<body>");
				out.println("<h1> Welcome, " + objUser.getFirstName().trim() + "</h1>");
				out.println("<br/>");
				out.println("<a href='home'> Home Page </a>");
				out.println("</body>");
				out.println("</html>");				
			}
		}
		catch(Exception ex)
		{
			System.out.println("SignupServlet- " + ex.toString());
		}
		finally{
			
		}
	}
	
	/*public HashMap<String, String> readFile(String filePath) throws IOException {

		String line = null;
		HashMap<String, String> hm = new HashMap<String, String>();

		try {
			FileReader objFileReader = new FileReader(filePath);
			BufferedReader objBufferedReader = new BufferedReader(objFileReader);

			while ((line = objBufferedReader.readLine()) != null) {
				
				String[] s = null;
				s = line.split(",");
				hm.put(s[2].trim(), s[3].trim());
			}
			objBufferedReader.close();

			return hm;

		} finally {

		}
	}

	public void writeFile(String filePath, String userData) throws IOException {
		try {
			FileWriter objFileWriter = new FileWriter(filePath, true);
			BufferedWriter objBufferedWriter = new BufferedWriter(objFileWriter);
			objBufferedWriter.newLine();	
			objBufferedWriter.write(userData);
			objBufferedWriter.close();
		} finally {
		}
	}*/
}