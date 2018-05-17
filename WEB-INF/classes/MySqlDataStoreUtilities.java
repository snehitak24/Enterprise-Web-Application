import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;
import java.util.*;
import java.text.*;

public class MySqlDataStoreUtilities
{
	Connection conn = null;
	HashMap<String, Users> hmUsers = null;
	HashMap<String, ProductCatalog> hmProductCatalog = new HashMap<String, ProductCatalog>();
	Users objUser = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	int orgQuantity=100;
	ProductCatalog pc = null;
	Statement stmt = null;
	int productid=0;

	public MySqlDataStoreUtilities()
	{

	}


	public HashMap<String, Users> selectUserDetails() throws SQLException
	{
		try
		{

			hmUsers = new HashMap<String, Users>();

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();
			String selectQuery = "SELECT * FROM userdetails";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				objUser = new Users();
				objUser.setuserId(rs.getString("userid"));
				objUser.setPassword(rs.getString("password"));
				objUser.setUsertype(rs.getString("usertype"));
				objUser.setFirstName(rs.getString("firstname"));
				objUser.setLastName(rs.getString("lastname"));
				objUser.setPhone(rs.getString("phone"));
				objUser.setAddress(rs.getString("address"));
				objUser.setCity(rs.getString("city"));
				objUser.setState(rs.getString("state"));
				objUser.setCountry(rs.getString("country"));
				objUser.setZipcode(rs.getString("zipcode"));

				hmUsers.put(rs.getString("userid"), objUser);
			}
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(SQLException se)
		{	//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{	//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt!=null)
                stmt.close();
			}
			catch(SQLException se2)
			{

			}

			try
			{
				if(conn!=null)
                conn.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}
		return hmUsers;
	}

	public void insertUserDetails(Users user)throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			String insertQuery = "INSERT INTO userdetails(userid,password,usertype,firstname,lastname,phone,address,city,state,country,zipcode) " + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";

			pst = conn.prepareStatement(insertQuery);

			pst.setString(1,user.getuserId());
			pst.setString(2,user.getPassword());
			pst.setString(3,user.getUsertype());
			pst.setString(4,user.getFirstName());
			pst.setString(5,user.getLastName());
			pst.setString(6,user.getPhone());
			pst.setString(7,user.getAddress());
			pst.setString(8,user.getCity());
			pst.setString(9,user.getState());
			pst.setString(10,user.getCountry());
			pst.setString(11,user.getZipcode());

			pst.execute();

			pst.close();
			conn.close();
		}
		catch(SQLException se)
		{	//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{	//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(pst!=null)
                pst.close();
			}
			catch(SQLException se2)
			{

			}

			try
			{
				if(conn!=null)
                conn.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}
	}

	public boolean insertOrderDetails(HashMap<String, ProductCatalog> orderDetails,String state, String creditcard, String user)throws SQLException
	{
		try
		{
			int newOrderNo = getLastOrderNo() + 1;

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");

			pc = new ProductCatalog();

			String insertQuery = "INSERT INTO orders(Id,Productid,Productname,Username,Creditcard,Orderprice,Quantity,Retailer,State,Manufacturer,orderTime) " +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?);";

			if(orderDetails != null)
			{
				for(String key: orderDetails.keySet())
				{
					pc = orderDetails.get(key);

					pc.setOrderTime(new java.util.Date());
					pst = conn.prepareStatement(insertQuery);
					pst.setInt(1,newOrderNo);
					pst.setString(2,pc.getId());
					pst.setString(3,pc.getName());
					pst.setString(4,user);
					pst.setString(5,creditcard);
					pst.setFloat(6,pc.getPrice());
					pst.setInt(7,pc.getQuantity());
					pst.setString(8,pc.getRetailer());
					pst.setString(9,state);
					pst.setString(10,pc.getManufacturer());
					//-------
					pst.setDate(11,new java.sql.Date(pc.getOrderTime().getTime()));
					//-------
					pst.execute();
					quantityUpdate(pc.getQuantity(),pc.getId());
				}

			}
		}
		catch(Exception ex)
		{
			System.out.println("insertOrderDetails()- " + ex.toString());
			ex.printStackTrace();
			return false;
		}
		finally
		{
			pst.close();
			conn.close();
		}
		return true;
	}


	public HashMap<String, ArrayList<ProductCatalog>> fetchEveryDaySales()throws SQLException
	{
		HashMap<String, ArrayList<ProductCatalog>> hm = new HashMap<String, ArrayList<ProductCatalog>>();
		try
		{


			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();
			pc = new ProductCatalog();

			String salesQuery = "SELECT orders.orderTime, productdetails.productId, productprice, productdetails.productname, product_quantity-sales AS salesp FROM smartp.productdetails, smartp.orders where productdetails.productId = orders.Productid;";
			rs = stmt.executeQuery(salesQuery);


			while(rs.next()){

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String date=df.format(rs.getDate("orderTime"));

				ProductCatalog pCatalog = new ProductCatalog();

				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setQuantity(rs.getInt("salesp"));


				if(hm.containsKey(date)){
					ArrayList<ProductCatalog> list=hm.get(date);
					list.add(pCatalog);
				}
				else{
					ArrayList<ProductCatalog> list = new ArrayList<ProductCatalog>();
					list.add(pCatalog);
					hm.put(date, list);
				}

			}

		}
		catch(Exception ex)
		{
			System.out.println("everydaySalesDetails()- " + ex.toString());
		}
		finally
		{
			conn.close();
		}
		return hm;
	}

	/*public void sales(){
		try{
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");

			stmt = conn.createStatement();
			String selectQuery = "SELECT Productname,SUM(Quantity) c FROM smartp.orders GROUP BY Productname HAVING c >= 1;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next()){
				ProductCatalog p = new ProductCatalog();
				p.setQuantity(rs.getInt("c"));
				p.setName(rs.getString("Productname"));
				hmap.put(p.getName(),p.getQuantity());
			}


			}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			pst.close();
			conn.close();
		}
	}*/


	public void quantityUpdate(int Quantity, String ProductID)throws SQLException{

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");


			String selectQuery = "update productdetails set sales=(sales-"+Quantity+") where productId='"+ProductID.toUpperCase()+"';";
			pst = conn.prepareStatement(selectQuery);
			pst.execute();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			pst.close();
			conn.close();
		}


	}

	public HashMap<String, ProductCatalog> fetchOrderDetails(String user)throws SQLException
	{
		HashMap<String, ProductCatalog> userOrderDetails = new HashMap<String, ProductCatalog>();
		try
		{
			System.out.println(user);


			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT * FROM smartp.orders WHERE Username = '"+user+"';";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("Productid"));
				pCatalog.setName(rs.getString("Productname"));
				pCatalog.setPrice(rs.getFloat("Orderprice"));
				pCatalog.setQuantity(rs.getInt("Quantity"));
				pCatalog.setRetailer(rs.getString("Retailer"));
				pCatalog.setManufacturer(rs.getString("Manufacturer"));


				userOrderDetails.put(rs.getString("Productid"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("insertOrderDetails()- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return userOrderDetails;
	}


	public int getLastOrderNo()throws SQLException
	{
		int lastOrderNo = 0;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");

			String selectQuery = "SELECT MAX(Id) as Id FROM orders";

			pst = conn.prepareStatement(selectQuery);
			rs = pst.executeQuery();

			if(!rs.next())
			{
				return lastOrderNo;
			}
			else
			{
				do
				{
					lastOrderNo = rs.getInt("Id");
				}while(rs.next());
			}
		}
		catch(Exception ex)
		{
			System.out.println("getLastOrderNo()- " + ex.toString());
			return lastOrderNo;
		}
		finally
		{
			rs.close();
			pst.close();
			conn.close();
		}
		return lastOrderNo;
	}

	public void deleteProductDetails(String productID) throws SQLException {
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp", "root", "root");
			stmt = conn.createStatement();

			String deleteQuery=null;

			if(productID == null)
			{
				//deleteQuery = "DELETE from smartp.productdetails";
			}
			else
			{
				deleteQuery = "DELETE from smartp.productdetails where productId = '"+productID+"';";
			}

			stmt.executeUpdate(deleteQuery);

			stmt.close();
			conn.close();
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) { // Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public void insertProductDetails(ProductCatalog product)throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			/*String selectq="select * from smartp.productdetails where productId="+product.getId();
			rs = stmt.executeQuery(selectq);

			if(rs==null){*/
			String insertQuery = "INSERT INTO smartp.productdetails(productId,productname, productprice, product_quantity, onsale, man_rebate, sales) " + "VALUES (?,?,?,?,?,?,?);";

			pst = conn.prepareStatement(insertQuery);

			pst.setString(1,product.getId());
			pst.setString(2,product.getName());
			pst.setString(3,String.valueOf(product.getPrice()));
			pst.setInt(4,product.getorgQuantity());
			pst.setString(5,product.getonSale());
			pst.setString(6,String.valueOf(product.getManufacturerRebate()));
			pst.setInt(7,product.getorgQuantity());

			pst.execute();
			//}
			pst.close();
			conn.close();
		}
		catch(SQLException se)
		{	//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{	//Handle errors for Class.forName
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(pst!=null)
                pst.close();
			}
			catch(SQLException se2)
			{

			}

			try
			{
				if(conn!=null)
                conn.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}

	}

	public HashMap<String, ProductCatalog> fetchProductDetails()throws SQLException
	{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		try
		{
			//System.out.println(user);


			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productId, productname, productprice, sales FROM smartp.productdetails;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setorgQuantity(rs.getInt("sales"));

				productDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("insertProductDetails()- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return productDetails;
	}

	public HashMap<String, ProductCatalog> fetchProductOnSale()throws SQLException
	{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		try
		{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productId, productname, productprice, onsale FROM smartp.productdetails where onsale='yes';";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setonSale(rs.getString("onsale"));

				productDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("ProductsOnsale)- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return productDetails;
	}

	public HashMap<String, ProductCatalog> fetchProductOnRebates()throws SQLException
	{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		try
		{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productId, productname, productprice, man_rebate FROM smartp.productdetails where man_rebate>0;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setManufacturerRebate(rs.getFloat("man_rebate"));

				productDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("ProductsOnsale)- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return productDetails;
	}

	//--------------------------------------------------------------------------------------------------------


	public HashMap<String, ProductCatalog> fetchSalesOrderDetails()throws SQLException
	{
		HashMap<String, ProductCatalog> userOrderDetails = new HashMap<String, ProductCatalog>();
		try
		{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productdetails.productId, productprice, productdetails.productname, product_quantity-sales AS sales FROM smartp.productdetails, smartp.orders where productdetails.productId = orders.Productid;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setQuantity(rs.getInt("sales"));
				//pCatalog.setRetailer(rs.getString("Retailer"));
				//pCatalog.setManufacturer(rs.getString("Manufacturer"));


				userOrderDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("SalesOrderDetails()- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return userOrderDetails;
	}
	/*
	public HashMap<String, ProductCatalog> fetchProductOnSale()throws SQLException
	{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		try
		{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productId, productname, productprice, onsale FROM smartp.productdetails where onsale='yes';";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setonSale(rs.getString("onsale"));

				productDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("ProductsOnsale)- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return productDetails;
	}

	public HashMap<String, ProductCatalog> fetchProductOnRebates()throws SQLException
	{
		HashMap<String, ProductCatalog> productDetails = new HashMap<String, ProductCatalog>();
		try
		{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT productId, productname, productprice, man_rebate FROM smartp.productdetails where man_rebate>0;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog();
				pCatalog.setId(rs.getString("productId"));
				pCatalog.setName(rs.getString("productname"));
				pCatalog.setPrice(rs.getFloat("productprice"));
				pCatalog.setManufacturerRebate(rs.getFloat("man_rebate"));

				productDetails.put(rs.getString("productId"), pCatalog);
			}

			rs.close();
			stmt.close();
			conn.close();
		}
		catch(Exception ex)
		{
			System.out.println("ProductsOnsale)- " + ex.toString());
		}
		finally
		{
			rs.close();
			stmt.close();
			conn.close();
		}
		return productDetails;
	}

	*/





























/* 	public HashMap<String, ProductCatalog> selectCartDetails(String query)
	{
		try
		{
			if(conn == null)
			{
				getConnection();
			}

			String selectQuery = null;

			if(query == null)
			{
				selectQuery = "SELECT * FROM CART";
			}
			else
			{
				selectQuery = "SELECT * FROM CART " + query;
			}


			pst = conn.prepareStatement(selectQuery);
			rs = pst.executeQuery();
			pc = new ProductCatalog();

			if(!rs.next())
			{
				return null;
			}
			else
			{
				do
				{
					pc.setId(rs.getString("id"));
					pc.setName(rs.getString("name"));
					pc.setManufacturer(rs.getString("manufacturer"));
					pc.setPrice(rs.getFloat("price"));
					pc.setRetailer(rs.getString("retailer"));
					pc.setRetailerDiscount(rs.getFloat("retailerDiscount"));
					pc.setManufacturerRebate(rs.getFloat("manufacturerRebate"));
					pc.setCategory(rs.getString("category"));
					pc.setQuantity(rs.getInt("quantity"));

					hmProductCatalog.put(pc.getId(), pc);
				}while(rs.next());
			}
		}
		catch(Exception ex)
		{

		}
		finally
		{
			closeConnection();
		}
		return hmProductCatalog;
	}

	public boolean insertCartDetails(ProductCatalog pc)
	{
		try
		{
			if(conn == null)
			{
				getConnection();
			}

			String query = "Where id = "+pc.getId();

			HashMap<String, ProductCatalog> hmPC = selectCartDetails(query);

			if(hmPC == null)
			{
				//Insert Query
				String insertQuery = "INSERT INTO CART(id,name,manufacturer,price,retailer,retailerDiscount,manufacturerRebate,category,quantity) " + "VALUES (?,?,?,?,?,?,?,?,?);";

				pst = conn.prepareStatement(insertQuery);

				pst.setString(1,pc.getId());
				pst.setString(2,pc.getName());
				pst.setString(3,pc.getManufacturer());
				pst.setFloat(4,pc.getPrice());
				pst.setString(5,pc.getRetailer());
				pst.setFloat(6,pc.getRetailerDiscount());
				pst.setFloat(7,pc.getManufacturerRebate());
				pst.setString(8,pc.getCategory());
				pst.setInt(9,pc.getQuantity());

			}
			else
			{
				//Update Query
				for (ProductCatalog pCatalog : hmPC.values())
				{
					String updateQuery = null;
					if (pCatalog != null)
					{
						int temp = pCatalog.getQuantity() + 1;
						updateQuery = "UPDATE CART SET quantity = "+temp+" WHERE id = "+pCatalog.getId()+";";
					}
				pst = conn.prepareStatement(updateQuery);
				}
				pst.execute();
			}
		}
		catch(Exception ex)
		{
			return false;
		}
		finally
		{
			closeConnection();
		}
		return true;
	} */

	/* public boolean deleteCartDetails(ProductCatalog pc)
	{
		try
		{
			if(conn == null)
			{
				getConnection();
			}

			String deleteQuery = "DELETE FROM CART WHERE id = "+pc.getId()+";";
			pst = conn.prepareStatement(deleteQuery);
			pst.execute();
		}
		catch(Exception ex)
		{
			return false;
		}
		finally
		{
			closeConnection();
		}
		return true;
	} */
}
