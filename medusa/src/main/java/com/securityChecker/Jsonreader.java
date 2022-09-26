package com.securityChecker;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.App.App;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Jsonreader {

    
    String path;
    
    public Jsonreader(String path) {
        //TO-DO check if file exists
        this.path = path;
    }

    /**
     * Read jsons created from previous functions
     * @throws ParseException
     */
	public void readJson() throws ParseException {

		 //JSON parser object to parse read file
         JSONParser jsonParser = new JSONParser();
         
         try (FileReader reader = new FileReader(path))
         {
             //Read JSON file
            Object obj  = jsonParser.parse(reader);
            
            JSONArray array = new JSONArray();
            //System.out.println(obj);
            array.add(obj);
            array.forEach( seclist -> parseArrayObject( (JSONObject) seclist ) );
            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
  
     /**
      * Extract info from json file 
      * @param seclist
      */
     private static void parseArrayObject(JSONObject seclist) 
     {
        // Get flags array from json
        JSONObject flags = (JSONObject) seclist.get("flags");
        // Get matches array from json
        JSONObject matches = (JSONObject) seclist.get("matched");
       
        // Deserialize flags
        Set keys = flags.keySet();
        Iterator<String> namesIterator = keys.iterator();
        JSONObject obj;
        HashMap<String, Boolean> flag_values = new HashMap<String, Boolean>();
        while(namesIterator.hasNext()) {
            Object info_from_json = flags.get(namesIterator.next());
            obj = (JSONObject) info_from_json;
            Object[] obj_arr = obj.keySet().toArray();
            for(int i=0;i < obj.entrySet().size();i++)
            {
                String flag = (String) obj_arr[i];
                Boolean verify = (Boolean) obj.get(obj_arr[i]);
                flag_values.put(flag, verify);
            }
        }
        // Populate App variable
        App.setFlags(flag_values);

        // Deserialize matches 
        keys = matches.keySet();
        namesIterator = keys.iterator();
        ArrayList<String> al = new ArrayList<String>();
        while(namesIterator.hasNext()) {
            JSONArray info_from_json = (JSONArray) matches.get(namesIterator.next());
            for(int i=0;i < info_from_json.size();i++)
            {
            
                al.add(info_from_json.get(i).toString());
            }
        }
        System.out.println(al);
        App.setMatches(al);
        //     if (info_from_json.getClass().toString().contains("JSONArray"))
        //     {
        //         arr = (JSONArray) info_from_json;  
        //         Object[] array = arr.toArray();
        //         ArrayList<String> al = new ArrayList<String>();
        //         for(int i=0;i < arr.size();i++)
        //         {
        //             al.add((String) array[i]);
        //         }
        //         System.out.println(al);
        //         //App.setMatches(al);

        //     }else
        //     {
        //         obj = (JSONObject) info_from_json;
        //         HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
        //         Object[] obj_arr = obj.keySet().toArray();
        //         for(int i=0;i < obj.entrySet().size();i++)
        //         {
        //             String flag = (String) obj_arr[i];
        //             Boolean verify = (Boolean) obj.get(obj_arr[i]);
        //             flags.put(flag, verify);
        //         }
        //         System.out.println(flags);

        //         //App.setFlags(flags);
            //}

         //}
     }
 }