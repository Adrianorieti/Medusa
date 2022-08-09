package com.securityChecker;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class json_reader {

    
    String path;
    
    public json_reader(String path) {
        this.path = path;
    }

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
            //array.forEach( seclist -> System.out.println(seclist));

            
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
  
     private static void parseArrayObject(JSONObject seclist) 
     {
         //Get employee object within list
         System.out.println(seclist.keySet());
         Set keys = seclist.keySet();
         Iterator<String> namesIterator = keys.iterator();
         while(namesIterator.hasNext()) {
            JSONObject current = (JSONObject) seclist.get(namesIterator.next());
            System.out.println("current: " + current.keySet());
            System.out.println("current: " + current.keySet());
         }
          
        //  //Get employee first name
        //  boolean canary = (boolean) flags.get("CANARY");    
        //  System.out.println(canary);
          
        //  //Get employee last name
        //  String lastName = (String) employeeObject.get("lastName");  
        //  System.out.println(lastName);
          
        //  //Get employee website name
        //  String website = (String) employeeObject.get("website");    
        //  System.out.println(website);
     }
 }