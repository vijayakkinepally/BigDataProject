
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

public class EPlPlayer {
	public static void main(String args[])
	{
	try {
		
		int i=0;
		 FileWriter fw1 = new FileWriter("arsenal.csv");
	     PrintWriter pw1 = new PrintWriter(fw1);
	     pw1.print("PlayerName");
	     pw1.print(",");
	     pw1.print("Count");
	     pw1.println("");
	     
	     
	     FileWriter fw2 = new FileWriter("mu.csv");
	     PrintWriter pw2 = new PrintWriter(fw2);
	     pw2.print("PlayerName");
	     pw2.print(",");
	     pw2.print("Count");
	     pw2.println("");
	     
	     
	     FileWriter fw3 = new FileWriter("liverpool.csv");
	     PrintWriter pw3 = new PrintWriter(fw3);
	     pw3.print("PlayerName");
	     pw3.print(",");
	     pw3.print("Count");
	     pw3.println("");
	     
	 
	     
		String playerNames[] = {"Wayne Rooney","Angel Di Maria","Juan Mata","Robin van persie","John Terry","Fabregas","Eden Hazard","Diego Costa","Steven Gerrard","Luis suarez","Lucas Leiva","Pepe Reina"};
		String Count[] = new String[12];
		Mongo mongo = new Mongo("localhost",
				27017); //connecting to mongodb
		DB db = mongo.getDB("epldb"); // creating or getting database from mongodb
	
		DBCollection collection = db.getCollection("epl_arsenaltweets"); // get collection
		
		
		
		for(String playerName : playerNames) {
			String query =".*"+playerNames[i]+".*";
		BasicDBObject regexQuery = new BasicDBObject();
		
		regexQuery.put("text", 
				new BasicDBObject("$regex", query)
				.append("$options", "i"));
		
		
		DBObject fields1 = new BasicDBObject();
		
		fields1.put("text", 1);
		fields1.put("_id", 0);
		
		DBCursor cursor = collection.find(regexQuery,fields1);//.sort(orderBy);
		int count = cursor.count();
		Count[i] = Integer.toString(count);
		System.out.println(count);
		
		
		i++;
		/*
		while (cursor.hasNext()) {
			
			System.out.println(cursor.next());
			i++;
		}
		
		System.out.println(i);
*/
	}
		for(i=0;i<12;i++) {
			if(i<=3)
			{
				pw1.print(playerNames[i]);
				pw1.print(",");
				pw1.print(Count[i]);
				pw1.println("");
			}
			else if(i>=4 && i<=7)
			{
				pw2.print(playerNames[i]);
				pw2.print(",");
				pw2.print(Count[i]);
				pw2.println("");
			}
			else
			{
				pw3.print(playerNames[i]);
				pw3.print(",");
				pw3.print(Count[i]);
				pw3.println("");
			}
				
			}
		pw1.flush();
		pw1.close();
		fw1.close();
		
		pw2.flush();
		pw2.close();
		fw2.close();
		
		
		pw3.flush();
		pw3.close();
		fw3.close();
		
	}
	catch(Exception e){
		e.getMessage();
	}
	}
}
