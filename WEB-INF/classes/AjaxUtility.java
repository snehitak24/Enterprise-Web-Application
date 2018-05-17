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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.*;

public class AjaxUtility extends HttpServlet
{
  Connection conn = null;
PreparedStatement pst = null;
ResultSet rs = null;
Statement stmt = null;

	public HashMap<String, ProductCatalog> hm = null;

  public static HashMap<String,ProductCatalog> getData(){
    HashMap<String, ProductCatalog> hm = new HashMap<String, ProductCatalog>();
    try{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartp","root","root");
			stmt = conn.createStatement();

			String selectQuery = "SELECT * FROM smartp.productdetails;";
			rs = stmt.executeQuery(selectQuery);

			while(rs.next())
			{
				ProductCatalog pCatalog = new ProductCatalog(rs.getString("productID"), rs.getString("productname"));
				hm.put(rs.getString("productID"), pCatalog);
      }
    }
    return hm;
  }

  public StringBuffer readData(String searchId){

    HashMap<String, ProductCatalog> hm;
    hm=getData();

    Iterator it = hm.entrySet().iterator();
    while(it.hasNext()){

      Map.entry pi = (Map.Entry)it.next();
      ProductCatalog p = (ProductCatalog)pi.getValue();

      if(p.getName().toLowerCase().startsWith(searchId)){
        sb.append("<product>");
        sb.append("<id>"+p.getId()+"</id>");
        sb.append("<productName>"+p.getName()+"</productName>");
        sb.append("</product>")
      }
    }

    return sb;
  }



}
