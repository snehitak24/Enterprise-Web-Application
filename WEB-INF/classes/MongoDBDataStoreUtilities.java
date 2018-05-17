import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;


public class MongoDBDataStoreUtilities
{
	static DBCollection myReviews;
	Review rv1 = null;
	
	public static void getConnection()
	{
		MongoClient mango;
		mango = new MongoClient("localhost", 27017);
		
		DB db = mango.getDB("CustomerReviews");
		myReviews = db.getCollection("myReviews");
	}
	
	public static void insertReview(Review rv)
	{
		try
		{
			if(rv != null)
			{
				getConnection();
				
				BasicDBObject doc = new BasicDBObject("title", "myReviews").append("productmodelname", rv.getProductModelName())
						.append("category", rv.getCategory()).append("productprice", rv.getProductPrice()).append("retailername", rv.getRetailerName())
						.append("retailerzip", rv.getRetailerZip()).append("retailercity", rv.getRetailerCity()).append("retailerstate", rv.getRetailerState())
						.append("productonsale", rv.getProductOnSale()).append("userid", rv.getUserID()).append("manufacturerrebate", rv.getManufacturerRebate())
						.append("useroccupation", rv.getUserOccupation()).append("reviewrating", rv.getReviewRating()).append("userage", rv.getUserAge())
						.append("manufacturername", rv.getManufacturerName()).append("reviewtext", rv.getReviewText()).append("usergender", rv.getUserGender());

				myReviews.insert(doc);
			}
		}
		catch(MongoException ex)
		{
			 ex.printStackTrace();
		}
	}
	
	public static DBCursor fetchReview(String productName)
	{
		DBCursor cursor = null;
		try
		{
			getConnection();
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("productmodelname", productName);
			
			cursor = myReviews.find(searchQuery);						
		}
		catch(MongoException ex)
		{
			 ex.printStackTrace();
		}
		return cursor;
	}	
}