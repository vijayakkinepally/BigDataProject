



import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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

public class SamsungIphone {
	public static void main(String[] args )
	{
		try {
			 
			Mongo mongoConn = new Mongo("localhost", 27017); //connecting to mongodb
			
			String db1[] = {"iphone","samsung"};
			String collection[] = {"iphone_tweet_data","samsung_tweet_data"};
			
			int k=0;
			String[][] iphoneCount = new String[12][2];
			String[][] samsungCount = new String[12][2];
			
			FileWriter fw = new FileWriter("Iphone.csv");
		     PrintWriter pw = new PrintWriter(fw);
		     pw.print("country");
		     pw.print(",");
		     pw.print("count");
		     pw.println("");
		     
		     
		     
		     FileWriter fw1 = new FileWriter("Samsung.csv");
		     PrintWriter pw1 = new PrintWriter(fw1);
		     pw1.print("country");
		     pw1.print(",");
		     pw1.print("count");
		     pw1.println("");
			for (String a : db1)  {
			DB db = mongoConn.getDB(db1[k]); // creating or getting database from mongodb
			DBCollection ebolaCollection = db.getCollection(collection[k]); // get collection
			
			//k++;
			// Matching to get only tweets from December month
			DBObject matchDec = new BasicDBObject("$match", new BasicDBObject("created_at", new BasicDBObject("$regex", ".*.*")
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
			
			AggregationOutput output = ebolaCollection.aggregate(matchDec,project,group,sort);
			
			int i=0;	
			
			
		     Map res;
		     String country;
		     String counter;
		     int count;
			
			int j=0;
			for (DBObject result : output.results()) {
				if(i==0 && k==0) {
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
				int temp = Integer.parseInt(counter) * 10;
				if(k==0){
				iphoneCount[j][0] =country;
				iphoneCount[j][1] = Integer.toString(temp); //extrapolating, as got less tweets
				}
				else {
				samsungCount[j][0] =country;
			 	samsungCount[j][1] = Integer.toString(temp); 
				}
			    i++;
				j++;
			    if(i==10)break;
			}
			k++;
			
			}
			
			for(int l= 0;l<10;l++) {
			pw.print(iphoneCount[l][0]);
			pw.print(",");
			pw.print(iphoneCount[l][1]);
			pw.println("");
			pw1.print(samsungCount[l][0]);
			pw1.print(",");
			pw1.print(samsungCount[l][1]);
			pw1.println("");
			}
			pw.flush();
			pw.close();
			fw.close();
			
			pw1.flush();
			fw1.close();
			pw1.close();
		}
		
		catch(Exception e){
			e.getMessage();
		}
	}
}
