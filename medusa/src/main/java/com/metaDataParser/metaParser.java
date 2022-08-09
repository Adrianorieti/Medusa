package com.metaDataParser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.App.App;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class metaParser {
    

    public static void parseFile(File plistFile, File ipa_path) throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException, SAXException
    {
        
        NSDictionary dict = (NSDictionary) PropertyListParser.parse(plistFile);
        HashMap<String, NSObject> hm = dict.getHashMap();

        try {

            populateResult(hm, ipa_path);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void populateResult(HashMap<String,NSObject> hm, File ipa_path) throws IOException, NoSuchAlgorithmException
    {

        // Size in mb of ipa packet
        float size_in_bytes = Files.size(ipa_path.toPath());
        size_in_bytes = (size_in_bytes / 1000000);
        String size_in_mb = String.format("%.01f", size_in_bytes);

        //md5
        String checksumMD5 = DigestUtils.md5Hex(new FileInputStream(ipa_path));
        
        //sha
        String checksumSHA256 = DigestUtils.sha256Hex(new FileInputStream(ipa_path));

        // Create Json object for meta data results
        JSONObject jsonObject = new JSONObject();

        //Add all the info key: value

        jsonObject.put("Name",hm.get("CFBundleName").toString());
        jsonObject.put("Md5", checksumMD5);
        jsonObject.put("Sha256", checksumSHA256);
        jsonObject.put("Size", size_in_mb + "MB");
        
        jsonObject.put("App type", determineAppType("result" + File.separator + "Payload" + File.separator + hm.get("CFBundleName") + ".app" + File.separator + "_CodeSignature/CodeResources"));

        jsonObject.put("Bundle ID", hm.get("CFBundleIdentifier").toString());
        jsonObject.put("SDK name", hm.get("DTSDKName").toString());
        jsonObject.put("Bundle Version", hm.get("CFBundleVersion"));
        jsonObject.put("Build", hm.get("DTSDKBuild").toString());
        jsonObject.put("Platform Version ", hm.get("DTPlatformVersion"));
        jsonObject.put("Min OS Version", hm.get("MinimumOSVersion"));

        NSArray nsa = (NSArray) hm.get("CFBundleSupportedPlatforms");
        jsonObject.put("Supported Platforms",nsa.toASCIIPropertyList());

        jsonObject.put("Compiler", hm.get("DTCompiler").toString());

        // CFBundleName is the actual name of the executable
        String binaryName = "result" + File.separator + "Payload" + File.separator + hm.get("CFBundleName") + ".app" + File.separator + hm.get("CFBundleName");
        App app = new App();
        app.setBinaryName(binaryName);
        checkCpu cpu = new checkCpu(binaryName);
        String arch = cpu.parse();
        jsonObject.put("Cpu", arch);
        
        JSONObject permissions = new JSONObject();
        JSONObject urlschemes = new JSONObject();
        for (Object set : hm.entrySet()) 
        {

            // Printing all elements of a Map
            if(set.toString().matches("^NS.*"))
            {
                    if(set.toString().contains("NSDictionary"))
                    {
                        String[] splitted = set.toString().split("=");
                        NSDictionary ns = (NSDictionary) hm.get(splitted[0]);
                        permissions.put(splitted[0], ns);

                    }else if(set.toString().contains("NSArray"))
                    {
                        String[] splitted = set.toString().split("=");
                        NSArray ns = (NSArray) hm.get(splitted[0]);
                        permissions.put(splitted[0], ns);
                    }else {
                        String[] splitted = set.toString().split("=");
                        permissions.put(splitted[0], hm.get(splitted[0]));
                    }
            }else if(set.toString().contains("CFBundleURLTypes")){
                String[] splitted = set.toString().split("=");
                NSArray ns = (NSArray) hm.get(splitted[0]);
                urlschemes.put(splitted[0], ns);

            }

        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        jsonObject.put("URLSchemes", urlschemes);
        jsonObject.put("Permissions", permissions);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

        String metadata_path = "result" + File.separator + "metadata.json";
        FileWriter fileWriter = new FileWriter(metadata_path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(json);
        printWriter.close();
        

        System.out.println("Metadata extracted and json file created at" + System.getProperty("user.dir") + File.separator + metadata_path );
        // controllare poi se è possibile generare uno SBom per objective-c
    }

    private static String determineAppType(String codesign) throws IOException
    {
        BufferedReader codeRes = new BufferedReader(new FileReader(codesign));
        
        String line = codeRes.readLine();
        boolean isSwift = false;
        while (line != null){
            if(line.contains("libswift"))
            {
                isSwift = true;
            }
			line = codeRes.readLine();
        }
        codeRes.close();
        if(isSwift)
            return "Swift";
        else
            return "ObjectiveC";
    }
}



