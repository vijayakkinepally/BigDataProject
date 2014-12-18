


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import java.net.UnknownHostException;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import java.util.*;


public class EbolaNovAggregate {
	public static void main(String[] args )
	{
		try {
			 
			Mongo mongoConn = new Mongo("localhost", 27017); //connecting to mongodb
			DB db = mongoConn.getDB("ebola_db"); // creating or getting database from mongodb
			DBCollection ebolaCollection = db.getCollection("ebola_tweet_data"); // get collection
			
	
			// Matching to get only tweets from November month
			DBObject matchNov = new BasicDBObject("$match", new BasicDBObject("created_at", new BasicDBObject("$regex", ".*Nov.*")
			.append("$options", "i")));

			
			//Projection fields
			DBObject userLocFields = new BasicDBObject("user.location", 1);
			userLocFields.put("user.location", 1);
			userLocFields.put("_id", 0);
			DBObject project = new BasicDBObject("$project", userLocFields );
			
			// Now the $group operation
			DBObject groupByFields = new BasicDBObject( "_id", "$user.location");
			groupByFields.put("count", new BasicDBObject( "$sum", 1));
			DBObject group = new BasicDBObject("$group", groupByFields);

			DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
			
			AggregationOutput output = ebolaCollection.aggregate(matchNov,project,group,sort);
			
			int i=0;	
			
			
			 FileWriter fw = new FileWriter("EbolaNov.csv");
		     PrintWriter pw = new PrintWriter(fw);
		     pw.print("Country");
		     pw.print(",");
		     pw.print("count");
		     pw.println("");
		        
		     Map res;
		     String country;
		     String counter;
		     int count;
			
			for (DBObject result : output.results()) {
				if(i==0) {
					i++;
					continue;
				}
				 
				res = result.toMap();
				country = res.get("_id").toString();
				country = country.replace(",", " ");
				System.out.print(country + ",");
				
				counter = res.get("count").toString();
				count = Integer.parseInt(counter);
				System.out.print(count);
				System.out.println("");
				pw.print(country);
				pw.print(",");
				pw.print(count);	
				pw.println("");
			    i++;
			    if(i==20)break;
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

