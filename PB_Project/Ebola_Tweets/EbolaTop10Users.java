


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

public class EbolaTop10Users {
	public static void main(String args[])
	{
	try {
		 
		Mongo mongo = new Mongo("localhost",
				27017); //connecting to mongodb
		DB db = mongo.getDB("ebola_db"); // creating or getting database from mongodb
	
		DBCollection collection = db.getCollection("ebola_tweet_data"); // get collection
		
		DBObject match = new BasicDBObject("$match", new BasicDBObject("created_at", new BasicDBObject("$regex", ".*.*")
		.append("$options", "i")));
		
		
		DBObject fields1 = new BasicDBObject("user.name", 1);
		fields1.put("user.id_str", 1);
		fields1.put("user.name", 2);
	
		DBObject project = new BasicDBObject("$project", fields1 );
		
		
		DBObject groupFields = new BasicDBObject( "_id", "$user.name");
		groupFields.put("count", new BasicDBObject( "$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
		
		AggregationOutput output = collection.aggregate(match,project,group,sort);
		
		
		FileWriter fw = new FileWriter("ebola_top10users.csv");
	     PrintWriter pw = new PrintWriter(fw);
	     pw.print("UserName");
	     pw.print(",");
	     pw.print("TweetCount");
	     pw.println("");
	     
	     Map res;
		
		int i=0;
		// Limiting results to 10
		for (DBObject result : output.results()) {
			res = result.toMap();
			pw.print(res.get("_id"));
			pw.print(",");
			pw.print(res.get("count"));
			pw.println("");
			System.out.println(result.toString());
			i++;
			if(i==10)break;
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