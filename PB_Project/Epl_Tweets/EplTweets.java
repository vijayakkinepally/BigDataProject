

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DBCursor;

public class EplTweets {
	public static void main(String args[])
	{
	try {
		 
		Mongo mongo = new Mongo("localhost",
				27017); //connecting to mongodb
		DB db = mongo.getDB("epldb"); // creating or getting database from mongodb
	
		DBCollection collection = db.getCollection("epl_arsenaltweets"); // get collection
		
		
		//File Logic writing headers and opening file
		FileWriter fw = new FileWriter("epl_tweet.csv");
	    PrintWriter pw = new PrintWriter(fw);
	    pw.print("UserName");
	    pw.print(",");
	    pw.print("Lang");
	    pw.print(",");
	    pw.print("FollowersCount");
	    pw.print(",");
	    pw.print("Image");
	    pw.print(",");
	    pw.print("Text");
	    pw.println("");    
	 
	    String teamNames[] = {"#Arsenal","#CFC","LFC","Avfc","COYS","lcfc","mufc","saintsfc"};
		
		for(String teamName : teamNames) {
		BasicDBObject regexQuery = new BasicDBObject();
		
		
		
		String query =".*"+teamName+".*";
		
		regexQuery.put("text", 
				new BasicDBObject("$regex", query)
				.append("$options", "i")); 
		
		DBObject fields1 = new BasicDBObject();
		
		fields1.put("text", 2);
		fields1.put("user.name", 1);
		fields1.put("user.followers_count",3);
		fields1.put("user.profile_image_url",4);
		fields1.put("user.lang", 5);
		
		// here the code is select fields where orderby followerscount and by descending order to get the user with max follower count
		DBCursor cursor = collection.find(regexQuery,fields1).sort(new BasicDBObject("user.followers_count",-1)).limit(1);;//.sort(orderBy);
		
		Map mapObj;
		
		    
	    DBObject res;
	 
	    String userdata;
	    
		while (cursor.hasNext()) {
			res = cursor.next();
			
			//System.out.println(res.toString());
			mapObj = res.toMap();
			
			
			userdata = mapObj.get("user").toString();
			
			
			System.out.println(userdata);
			
			
			String arr[] = userdata.split(":");
			
		/*	// Debugging Bharat
			for(String data : arr)
				System.out.println(data);
			*/
		
			
			String username ="";
			String arr1[] = arr[1].split((","));
			username = arr1[0].replace("\"", "");
			System.out.println(username);
			pw.print(username);
			pw.print(",");
			
			
			String lang;
			String arr3[] = arr[3].split(",");
			lang = arr3[0].replace("\"", "");
			System.out.println(lang);
			pw.print(lang);
			pw.print(",");
			
			
			
			String followercount;
			String arr2[] = arr[2].split(",");
			followercount = arr2[0];
			System.out.println(followercount);
			pw.print(followercount);
			pw.print(",");
			
			
			String imgurl;
			imgurl = arr[4].replace("\"", "") + ":" + arr[5].replace("\"", "").replace("}", "");
			System.out.println(imgurl);
			pw.print(imgurl);
			pw.print(",");
			
			System.out.println(mapObj.get("text"));
			pw.print(mapObj.get("text"));
			pw.println("");	
		}
		}
		pw.flush();
		pw.close();
		fw.close();
	}
	catch(Exception e){
		e.getMessage();
	}
	}
}

