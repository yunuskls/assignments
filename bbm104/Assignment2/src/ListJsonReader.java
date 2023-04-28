import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class ListJsonReader{
    public ArrayList<String> chanceCards = new ArrayList<>();
    public ArrayList<String> communityCards = new ArrayList<>();

    public int x = 0;
    public int a = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public ListJsonReader(){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("list.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            for(Object i:chanceList){
                String item = ((String)((JSONObject)i).get("item"));
                chanceCards.add(item);
            }
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            for(Object i:communityChestList){
				String item = ((String)((JSONObject)i).get("item"));
                communityCards.add(item);
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }
}

