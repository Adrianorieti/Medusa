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
        File destDir = new File("result"+ File.separator);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(ipa));
        ZipEntry zipEntry = zis.getNextEntry();
        HashMap<Number, ZipEntry> files = new HashMap<Number, ZipEntry>();
        ArrayList<Integer> lengths = new ArrayList<Integer>();
        while (zipEntry != null) {
            File newFile = new File(destDir, zipEntry.toString());
            if(zipEntry.getName().contains("Info.plist"))
            {
                lengths.add(zipEntry.getName().length());
                files.put(zipEntry.getName().length(), zipEntry);
            }
           
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                
                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                // cerco il primo Info.plist da cui estrarrò  i metadati
               
        }
        zipEntry = zis.getNextEntry();
        
    }
    Collections.sort(lengths);
    // get the first and most important file
    File plistFile = new File("result" + File.separator, files.get(lengths.get(0)).toString());
    // begin metadata extraction
    zis.closeEntry();
    zis.close();
    metaParser.parseFile(plistFile, ipa);
}
}