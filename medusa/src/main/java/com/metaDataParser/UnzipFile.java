package com.metaDataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import com.dd.plist.PropertyListFormatException;

import org.xml.sax.SAXException;

public class UnzipFile {

    public static void unzip(File ipa) throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException, SAXException, URISyntaxException {
        
        byte[] buffer = new byte[1024];

        // Extract all files in the result folder
        File destDir = new File("result"+ File.separator);
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(ipa))) {
            ZipEntry zipEntry = zis.getNextEntry();
            HashMap<Number, ZipEntry> files = new HashMap<Number, ZipEntry>();
            ArrayList<Integer> lengths = new ArrayList<Integer>();

            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.toString());
                if(zipEntry.getName().contains("Info.plist")) // Save paths lengths for plist files
                {
                    lengths.add(zipEntry.getName().length());
                    files.put(zipEntry.getName().length(), zipEntry);
                }
               
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // Fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    
                    // Write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
            }
            zipEntry = zis.getNextEntry();
            
   }
   Collections.sort(lengths);
   // Get the first plist file containing meta infos
   File plistFile = new File("result" + File.separator, files.get(lengths.get(0)).toString());
   // Begin metadata extraction
   zis.closeEntry();
   zis.close();
   metaParser.parseFile(plistFile, ipa);
        }
}
}