import java.io.*;
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

public class WfCache
{
	public static HashMap<String, ProductCatalog> phoneCatalog = null;
	public static HashMap<String, ProductCatalog> speakerCatalog = null;
	public static HashMap<String, ProductCatalog> laptopCatalog = null;
	public static HashMap<String, ProductCatalog> headphoneCatalog = null;
	public static HashMap<String, ProductCatalog> hddCatalog = null;
	public static HashMap<String, ProductCatalog> smartwatchCatalog = null;
	public static HashMap<String, ProductCatalog> accessoryCatalog = null;
	public static HashMap<String, ProductCatalog> cartData = null;
	public static HashMap<String, Users> wfUserData = null;
	public static HashMap<String, ProductCatalog> allProductsCatalog = null;
 
	
	
	public WfCache()
	{
		
	}
		
		
		public static void deleteProducts(String productID)throws IOException, SQLException
	{		
		MySqlDataStoreUtilities objSql1 = new MySqlDataStoreUtilities();			
		objSql1.deleteProductDetails(productID);
	}
		
	public static HashMap<String, ProductCatalog> getAllProducts()throws IOException, SQLException
	{
		
			allProductsCatalog = new HashMap<String, ProductCatalog>();
			
			allProductsCatalog.putAll(isPhoneCatalogFetched());
			allProductsCatalog.putAll(isSpeakerCatalogFetched());
			allProductsCatalog.putAll(isLaptopCatalogFetched());
			allProductsCatalog.putAll(isHeadPhoneCatalogFetched());
			allProductsCatalog.putAll(isAccessoryCatalogFetched());
			allProductsCatalog.putAll(isExternalCatalogFetched());
			allProductsCatalog.putAll(isSmartWatchCatalogFetched());
		
		return allProductsCatalog;
	}
	
	
	
	public static HashMap<String, ProductCatalog> isPhoneCatalogFetched()throws IOException
	{	
		try{
		
		if(phoneCatalog == null)
		{			
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			phoneCatalog = SAXInterface(Constants.PhoneXML);
				
		
			for(String key: phoneCatalog.keySet()){
				ProductCatalog p  = phoneCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return phoneCatalog;
	}
	
	public static HashMap<String, ProductCatalog> isSpeakerCatalogFetched()throws IOException
	{
		try{
		
		if(speakerCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			speakerCatalog = SAXInterface(Constants.SpeakerXML);
		
		
			for(String key: speakerCatalog.keySet()){
				ProductCatalog p  = speakerCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return speakerCatalog;
	}
	
	public static HashMap<String, ProductCatalog> isLaptopCatalogFetched()throws IOException
	{
		try{
		
		if(laptopCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			laptopCatalog = SAXInterface(Constants.LaptopXML);
		
		
			for(String key: laptopCatalog.keySet()){
				ProductCatalog p  = laptopCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return laptopCatalog;
	}
	
	public static HashMap<String, ProductCatalog> isHeadPhoneCatalogFetched()throws IOException
	{
		try{
		
		if(headphoneCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			headphoneCatalog = SAXInterface(Constants.HeadPhoneXML);
				
			for(String key: headphoneCatalog.keySet()){
				ProductCatalog p  = headphoneCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return headphoneCatalog;
	}
	
	public static HashMap<String, ProductCatalog> isAccessoryCatalogFetched()throws IOException
	{
		try{
		
		if(accessoryCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			accessoryCatalog = SAXInterface(Constants.AccessoryXML);
		
		
			for(String key: accessoryCatalog.keySet()){
				ProductCatalog p  = accessoryCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return accessoryCatalog;
	}
	
	public static void addToCart(ProductCatalog objPC)
	{
		if(cartData == null)
		{
			cartData = new HashMap<String, ProductCatalog>();			
		}
		cartData.put(objPC.getId(), objPC);
	}
	
	public static HashMap<String, ProductCatalog> showCart()
	{		
		
		return cartData;
	}
	
	public static HashMap<String, ProductCatalog> deleteCart(String ID)
	{
		if(cartData != null)
		{			
			cartData.remove(ID);			
		}
		return cartData;
	}	
	
	public static void clearCart()
	{
		if(cartData != null)
		{			
			cartData.clear();			
		}
	}
	
	public static HashMap<String, ProductCatalog> SAXInterface(String filePath)throws IOException
	{
		SAXProductHandler saxHandler = new SAXProductHandler();
		HashMap<String, ProductCatalog> pc = new HashMap<String, ProductCatalog>();
		try
		{
			pc = saxHandler.readDataFromXML(filePath);
		}
		catch(SAXException e)
		{
			System.out.println("Error - SAXException");
		}
		catch(ParserConfigurationException e)
		{
			System.out.println("Error - ParserConfigurationException");
		}
		return pc;
	}
	
	public static HashMap<String, ProductCatalog> isExternalCatalogFetched()throws IOException
	{
		try{
		
		if(hddCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			hddCatalog = SAXInterface(Constants.ExternalXML);
		
		
			for(String key: hddCatalog.keySet()){
				ProductCatalog p  = hddCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return hddCatalog;
	}
	
	public static HashMap<String, ProductCatalog> isSmartWatchCatalogFetched()throws IOException
	{
		try{
		
		if(smartwatchCatalog == null)
		{
			//deleteProducts(null);
			MySqlDataStoreUtilities objSqlDataStore = new MySqlDataStoreUtilities();
			smartwatchCatalog = SAXInterface(Constants.SmartWatchXML);
		
		
			for(String key: smartwatchCatalog.keySet()){
				ProductCatalog p  = smartwatchCatalog.get(key);
				objSqlDataStore.insertProductDetails(p);
			}
		}}
			catch(Exception e){
				e.printStackTrace();
			}
		return smartwatchCatalog;
	}
	
}

