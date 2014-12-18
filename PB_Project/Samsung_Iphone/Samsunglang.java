


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


public class Samsunglang {
 public static void main(String args[])
 {
 try {
   
	 FileWriter fw = new FileWriter("Samsunglang.csv");
     PrintWriter pw = new PrintWriter(fw);
     pw.print("Language");
     pw.print(",");
     pw.print("count");
     pw.println("");
    
  Mongo mongo = new Mongo("localhost",
    27017); //connecting to mongodb
  DB db = mongo.getDB("samsunglang"); // creating or getting database from mongodb
 
  DBCollection collection = db.getCollection("samsunglang_tweet_data"); // get collection
  
  DBObject match = new BasicDBObject("$match", new BasicDBObject("created_at", new BasicDBObject("$regex", ".*.*")
  .append("$options", "i")));
  
  
  DBObject fields1 = new BasicDBObject("user.lang", 1);
  fields1.put("user.id_str", 1);
  fields1.put("user.lang", 2);
 
  DBObject project = new BasicDBObject("$project", fields1 );
  
  
  DBObject groupFields = new BasicDBObject( "_id", "$user.lang");
  groupFields.put("count", new BasicDBObject( "$sum", 1));
  DBObject group = new BasicDBObject("$group", groupFields);


  DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1));
  
  AggregationOutput output = collection.aggregate(match,project,group,sort);
  
  int i=0;
  
  Map res;
  String lang;
  String counter;
 
  
  int[] count = new int[12];
  String[] language = new String[12];
  int k=0;
  for (DBObject result : output.results()) {
	  
		res = result.toMap();
		lang = res.get("_id").toString();
		
		System.out.print(lang + ",");
		
		counter = res.get("count").toString();
		System.out.print(counter);
		System.out.println("");
		count[k] = Integer.parseInt(counter);
		language[k] = lang;
		k++;
 //  System.out.println(result.toString());
   i++;
   if(i==10)break;
  }
  
  int total = 0;
  for(i=0;i<10;i++){
	  total += count[i];
  }
  
  for(i=0;i<10;i++){
	  pw.print(language[i]);
	  pw.print(",");
	  float temp = ((float)count[i]/total)*100;
	  int temp1 = (int) temp;
	  if(temp1 == 0)
		  pw.print("1");
	  else
	  pw.print(Integer.toString(temp1));
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