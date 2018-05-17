import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
import java.util.*;

public class LoginServlet extends HttpServlet {
	
	HashMap<String, Users> hmUsers = null;
	MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
	String username = null,password= null,usertype = null;
	boolean flag = false;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();		
		HtmlUtility htmlUtil = new HtmlUtility(request,out);		
		htmlUtil.printHtml("header.html");		
		htmlUtil.printLoginContent();
		htmlUtil.printHtml("leftnavigationbar.html");
		htmlUtil.printHtml("footer.html");		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
	{
		try {
			java.io.PrintWriter out = response.getWriter();
			 username = request.getParameter("username").trim();
			 password = request.getParameter("password");
			 usertype = request.getParameter("user_type").toLowerCase().trim();
			//out.println(usertype);
			hmUsers = objSqlDataStore.selectUserDetails();
			//hmUsers = readFile(Constants.UserDetailFile);
			
			//java.io.PrintWriter out = response.getWriter();
			for(String key : hmUsers.keySet())
			{
				out.println(key+" "+hmUsers.get(key).getPassword());
				if(key.equals(username) && hmUsers.get(key).getPassword().equals(password)){
					flag=true;
					
					if(username.equals("Storemanager") && usertype.equals("storemanager"))
					{						
						HttpSession session=request.getSession(); 
						session.setAttribute("username",username); 
						session.setAttribute("usertype", usertype);
						response.sendRedirect("/csj/store");
					}
					else if(username.equals("Salesman") && usertype.equals("salesman"))
					{
						
						HttpSession session=request.getSession();
						session.setAttribute("username",username);
						session.setAttribute("usertype", usertype);
						response.sendRedirect("/csj/sales");
					}
					else if(usertype.equals("customer"))
					{
						HttpSession session=request.getSession();
						session.setAttribute("username",username);
						session.setAttribute("usertype", usertype);
						response.sendRedirect("/csj/customer");
					}
						
				}
				
			
			}
			if(!flag)
			{
				
						response.setContentType("text/html");
						
						out.println("<html>");
						out.println("<head>");
						out.println("<title>Login Servlet Result</title>");
						out.println("</head>");
						out.println("<body>");
						out.println("<h2>" + "Login Failed. Incorrect Credentials" + "</h2>");
						out.println("<br/>");
						out.println("<a href='home'> Home Page </a>");
						out.println("<br/>");
						out.println("<a href='login'> Login Page </a>");
						out.println("</body>");
						out.println("</html>");
						out.close();
					
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			
		}
	}
	
	public HashMap<String, String> readFile(String filePath) throws IOException {

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
}
