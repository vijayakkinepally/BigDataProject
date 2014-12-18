

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TotalAggregate {
	public static void main(String args[])
	{
	try {
		 
		Mongo mongo = new Mongo("localhost",
				27017); //connecting to mongodb
		DB db = mongo.getDB("ebola_db"); // creating or getting database from mongodb
		DBCollection collection = db.getCollection("ebola_tweet_data"); // get collection
		
		/*
		DBObject match = new BasicDBObject("$match", new BasicDBObject("user.location", new BasicDBObject("$regex", ".*.*")
		.append("$options", "i")));
		 */
		
		DBObject match = new BasicDBObject("$match", new BasicDBObject("created_at", new BasicDBObject("$regex", ".*.*")
		.append("$options", "i")));
		
		
		DBObject fields1 = new BasicDBObject("user.location", 1);
		fields1.put("user.location", 1);
		fields1.put("_id", 0);
		DBObject project = new BasicDBObject("$project", fields1 );
		
		
		DBObject groupFields = new BasicDBObject( "_id", "$user.location");
		groupFields.put("count", new BasicDBObject( "$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
		
		AggregationOutput output = collection.aggregate(match,project,group,sort);
		
		int i=-1;	
		
		
		 FileWriter fw = new FileWriter("Total_aggregate.csv");
	     PrintWriter pw = new PrintWriter(fw);
	     pw.print("Country");
	     pw.print(",");
	     pw.print("count");
	     pw.println("");
	        
	     Map res;
	     String country;
	     String counter;
	     int count;
	     
	     String[][] outputdata = new String[22][2];
		
		for (DBObject result : output.results()) {
			if(i==-1) {
				i++;
				continue;
			} // skipping first one beacuse it is with empty location
			 
			 
			res = result.toMap();
			country = res.get("_id").toString();
			country = country.replace(",", " ");
			//if(country.contains("USA"))
				//System.out.print(country + ",");
			
			counter = res.get("count").toString();
			count = Integer.parseInt(counter);
			//System.out.print(count);
			//System.out.println("");
			/*
			pw.print(country);
			pw.print(",");
			pw.print(count);	
			pw.println("");
			*/
			outputdata[i][0] = country;
			outputdata[i][1] = counter;
			
		    i++;
		    if(i==21)break;
		}
		
		// for debugging
		
		/*
		for(i=0;i<20;i++){
			System.out.println(outputdata[i][0] + "    " + outputdata[i][1]);
		}
		*/
		
		String[][] finalOutput = new String[22][2];
		String temp;
		int uscount = 0;
		int tempcount = 0;
		int j=1;
		//summing all states of us count in to one country us
		for( i=0;i<21;i++) {
			temp = outputdata[i][0];
			if(temp.contains("USA") | temp.contains("United States")) {
				tempcount = Integer.parseInt(outputdata[i][1]);
				uscount += tempcount;
			}
			else {
				finalOutput[j][0] = outputdata[i][0];
				finalOutput[j][1] = outputdata[i][1];
				j++;
			}
		}
		
		finalOutput[0][0] = "USA";
		finalOutput[0][1] = Integer.toString(uscount);
		
		for(i=0;i<10;i++) {
			System.out.println(finalOutput[i][0] + "    "+ finalOutput[i][1]);
			pw.print(finalOutput[i][0]);
			pw.print(",");
			pw.print(finalOutput[i][1]);
			pw.println("");
			
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

