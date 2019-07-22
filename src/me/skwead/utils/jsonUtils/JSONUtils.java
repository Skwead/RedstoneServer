package me.skwead.utils.jsonUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {

    public JSONObject addJSONobj(JSONObject parent, JSONObject child, String key){
        parent.put(key, child);
        return parent;
    }

    public void addJSONArr(JSONArray array, JSONObject obj){
        array.add(obj);
    }

    public JSONObject getJSONfromFile(String path) throws ParseException, IOException {
        String fullJsonText = getStringFromFile(path);

        JSONParser parser = new JSONParser();

        return (JSONObject) parser.parse(fullJsonText);
    }

    public String getStringFromFile(String path) throws IOException{
        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String lines = "";
        String fullJsonText = "";

        while((lines = br.readLine()) != null) {
            fullJsonText += lines;
        }

        return fullJsonText;
    }

    public JSONArray getJSONArrayfromFile(String path) throws ParseException, IOException {
        String fullJsonText = getStringFromFile(path);

        JSONParser parser = new JSONParser();

        return (JSONArray) parser.parse(fullJsonText);
    }
}
