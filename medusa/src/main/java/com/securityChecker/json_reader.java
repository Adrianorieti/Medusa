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

public class json_reader {

    
    String path;
    
    public json_reader(String path) {
        //TO-DO check if file exists
        this.path = path;
    }

    /**
     * Read jsons created from previous functions
     * @throws ParseException
     */
	public void read_json() throws ParseException {

		 //JSON parser object to parse read file
         JSONParser jsonParser = new JSONParser();
         
         try (FileReader reader = new FileReader(path))
         {
             //Read JSON file
            Object obj  = jsonParser.parse(reader);
            
            JSONArray array = new JSONArray();
            array.add(obj);
            System.out.println();
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
         // Extract info from json
         Set keys = seclist.keySet();
         Iterator<String> namesIterator = keys.iterator();
         JSONArray arr;
         JSONObject obj;
         // Check whether the info is a json Object or a Json Array
         while(namesIterator.hasNext()) {
            Object info_from_json = seclist.get(namesIterator.next());
            if (info_from_json.getClass().toString().contains("JSONArray"))
            {
                arr = (JSONArray) info_from_json;  
                Object[] array = arr.toArray();
                ArrayList<String> al = new ArrayList<String>();
                for(int i=0;i < arr.size();i++)
                {
                    al.add((String) array[i]);
                }
                
                App.setMatches(al);

            }else
            {
                obj = (JSONObject) info_from_json;
                HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
                Object[] obj_arr = obj.keySet().toArray();
                for(int i=0;i < obj.entrySet().size();i++)
                {
                    String flag = (String) obj_arr[i];
                    Boolean verify = (Boolean) obj.get(obj_arr[i]);
                    flags.put(flag, verify);
                }

                App.setFlags(flags);
            }

         }
     }
 }