package com.App;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;

import com.dd.plist.PropertyListFormatException;
import com.droolsObject.droolsCore;
import com.metaDataParser.UnzipFile;
import com.securityChecker.json_reader;
import com.securityChecker.securityChecker;
import com.securityChecker.json_reader;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;


public class App 
{
    static String binaryName;
    static HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
    static ArrayList<String> matches = new ArrayList<String>();
    
    public static void main( String[] args ) throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException, SAXException, URISyntaxException 
    {

        File ipa = new File(args[0]);
               
        BasicConfigurator.configure();

        if (args[0] != null)
        {
            
            if(isFileIpa(args[0]))
            {
                
                printTitle();
                // File is ipa so we can extract the files
                // and parse the metadata so finally create a metadata.json
                // UnzipFile.unzip(ipa);

                //Start security checks
                // securityChecker sc = new securityChecker(binaryName);
                // System.out.println("Performing security checks");
                // sc.performSecurityChecks();

                // read json TO-DO read every json
                //TO-DO check if file exists and pass it
                json_reader jsr = new json_reader("flags.json");

                try {
                    jsr.read_json();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                
                // create java object that is going to be passed to drools engine
                droolsCore dro = new droolsCore(flags, matches);
                dro.fireRules();
            }else{
                // File is not ipa so we close the program
                System.out.print("The file inserted is not a ipa packet \n");
                System.out.print("Please insert absolute path of an ipa packet\n");
            }
        }
    }

  /**
   * Check if file exists and is an ipa packet
   * @param file
   * @return true if exists and is an ipa packet
   * @throws IOException
   */
    private static boolean isFileIpa(String file) throws IOException {

            byte[] bytes;
            Path path = Paths.get(file);
            bytes = Files.readAllBytes(path);
            File f = new File(file);
            int[] magic_ipa = { (byte)0x50, (byte)0x4B, (byte)0x03, (byte)0x04 };
            int[] macho_magic = IntStream.range(0, 4).map(i -> bytes[i]).toArray();
            return f.exists() && Arrays.equals(magic_ipa, macho_magic);
            
    }

    /**
     * Print my amazing title <3
     */
    public static void printTitle()
    {
                System.out.println("");
                System.out.println("");
                System.out.println(",--.   ,--.          ,--.                        ");
                System.out.println("|   `.'   | ,---.  ,-|  |,--.,--. ,---.  ,--,--. ");
                System.out.println("|  |'.'|  || .-. :' .-. ||  ||  |(  .-' ' ,-.  |");
                System.out.println("|  |   |  |\\   --.\\ `-' |'  ''  '.-'  `)\\ '-'  |");
                System.out.println("`--'   `--' `----' `---'  `----' `----'  `--`--'");
                System.out.println("@Void Thesis project (if it doesn't work it's your fault)");
                System.out.println("");
                System.out.println("");
    }

    public String getBinaryName() {
        return binaryName;
    }
    public void setBinaryName(String binaryName) {
        App.binaryName = binaryName;
    }

    public static HashMap<String, Boolean> getFlags() {
        return flags;
    }
    public static void setFlags(HashMap<String, Boolean> flags) {
        App.flags = flags;
    }
    public static ArrayList<String> getMatches() {
        return matches;
    }

    public static void setMatches(ArrayList<String> matches) {
        App.matches = matches;
    }

    
}
