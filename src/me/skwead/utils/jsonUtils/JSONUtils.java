package me.skwead.utils.jsonUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

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

    public void addToFile(JSONObject object, String path) throws IOException {
        JSONParser p = new JSONParser();

        try {
            JSONArray all = (JSONArray) p.parse(new JSONUtils().getStringFromFile(path));
            all.add(object);
            try{
                FileWriter writer = new FileWriter(path);
                writer.write(all.toString());
                writer.flush();
                writer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void removeFromFile(JSONObject object, String path) throws IOException {
        JSONParser p = new JSONParser();

        try {
            JSONArray all = (JSONArray) p.parse(new JSONUtils().getStringFromFile(path));
            all.remove(object);
            try{
                FileWriter writer = new FileWriter(path);
                writer.write(all.toString());
                writer.flush();
                writer.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getJSONArrayfromFile(String path) throws ParseException, IOException {
        String fullJsonText = getStringFromFile(path);

        JSONParser parser = new JSONParser();

        return (JSONArray) parser.parse(fullJsonText);
    }

    public void setJsonArray(String path, JSONArray jsonArray) throws IOException, ParseException {
        File file = new File(path);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write(jsonArray.toJSONString());
    }
}
