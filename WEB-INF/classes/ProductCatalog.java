
import java.util.*;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalog 
{
	private String id;	
	private String name;
	private String manufacturer;
	private float price;
	private String imagepath;
	private String condition;
	private String retailer;
	private float retailerDiscount;
	private float manufacturerRebate;
	private String category;
	private int quantity = 0;
	private int orgQuantity=0;
	private String onSale;
	private Date orderTime;
	
	public ProductCatalog(){}
	
	public ProductCatalog(String ID, String Name)
	{
		id=ID;
		name=Name;
	}
	
	public ProductCatalog(String ID, String Name, String Manufacturer, float Price, String ImagePath, String Condition, String Retailer, float RetailerDiscount, float ManufacturerRebate, String Category, int Quantity,int orgQuantity, String onSale)
	{
		this.id = ID;
		this.name = Name;
		this.manufacturer = Manufacturer;
		this.price = Price;
		this.imagepath = ImagePath;
		this.condition = Condition;
		this.retailer = Retailer;	
		this.retailerDiscount = RetailerDiscount;
		this.manufacturerRebate = ManufacturerRebate;
		this.category = Category;
		this.quantity = Quantity;
		this.orgQuantity = orgQuantity;
		this.onSale = onSale;
	}
	
	public ProductCatalog(String ID, String Name, String Manufacturer, float Price, String ImagePath, String Condition, String Retailer, float RetailerDiscount, float ManufacturerRebate, String Category, int Quantity,int orgQuantity, String onSale, Date orderTime)
	{
		this.id = ID;
		this.name = Name;
		this.manufacturer = Manufacturer;
		this.price = Price;
		this.imagepath = ImagePath;
		this.condition = Condition;
		this.retailer = Retailer;	
		this.retailerDiscount = RetailerDiscount;
		this.manufacturerRebate = ManufacturerRebate;
		this.category = Category;
		this.quantity = Quantity;
		this.orgQuantity = orgQuantity;
		this.onSale = onSale;
		this.orderTime = orderTime;
	}
	
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getonSale() {
		return onSale;
	}

	public void setonSale(String onSale) {
		this.onSale = onSale;
	}
	
	public int getorgQuantity() {
		return orgQuantity;
	}

	public void setorgQuantity(int orgQuantity) {
		this.orgQuantity = orgQuantity;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public float getRetailerDiscount() {
		return retailerDiscount;
	}

	public void setRetailerDiscount(float retailerDiscount) {
		this.retailerDiscount = retailerDiscount;
	}

	public float getManufacturerRebate() {
		return manufacturerRebate;
	}

	public void setManufacturerRebate(float manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}