import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;



public class PropertyJsonReader {
     public ArrayList<Squares> squares = new ArrayList<>();

     public PropertyJsonReader(){
         JSONParser processor = new JSONParser();
         try (Reader file = new FileReader("property.json")){
             JSONObject jsonfile = (JSONObject) processor.parse(file);
             JSONArray Land = (JSONArray) jsonfile.get("1");
             for(Object i:Land){
				 
				 int id = Integer.parseInt((String)((JSONObject)i).get("id"));
				 String name = (String)((JSONObject)i).get("name");
				 int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                 squares.add(new Land(id, name, cost));

				 
                 
             }
             JSONArray RailRoad = (JSONArray) jsonfile.get("2");
             for(Object i:RailRoad){
                int id = Integer.parseInt((String)((JSONObject)i).get("id"));
				String name = (String)((JSONObject)i).get("name");
				int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                squares.add(new RailRoad(id, name, cost));

             }
			 
             JSONArray Company = (JSONArray) jsonfile.get("3");
             for(Object i:Company){
                 int id = Integer.parseInt((String)((JSONObject)i).get("id"));
				 String name = (String)((JSONObject)i).get("name");
				 int cost = Integer.parseInt((String)((JSONObject)i).get("cost"));
                 squares.add(new Company(id, name, cost));

             }
             
         } catch (IOException | ParseException e){
             e.printStackTrace();
         }
     }
}