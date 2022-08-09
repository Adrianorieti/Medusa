package com.App;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;

import com.dd.plist.PropertyListFormatException;
import com.metaDataParser.UnzipFile;
import com.securityChecker.json_reader;
import com.securityChecker.securityChecker;
import com.securityChecker.json_reader;
import org.xml.sax.SAXException;


public class App 
{
    static String binaryName;
    public static void main( String[] args ) throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException, SAXException, URISyntaxException 
    {
        // Qui è come funzionerebbe se riuscissi a passare un argomento da cmdline
        // lo stò passando tramite il launch.json
        //System.out.print("ipa file url " + args[0] + "\n"); 
        // CAMBIARE E CONTROLLARE SE È ABSOLUTE PATH ALTRIMENTI TROVARLO
        File ipa = new File(args[0]);
               
        //System.out.print("main folder " + mainFolder + "\n");
        
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
                json_reader tfr = new json_reader("flags.json");
                try {
                    tfr.read_json();
                } catch (org.json.simple.parser.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }else{
                // File is not ipa so we close the program
                System.out.print("The file inserted is not a ipa packet \n");
                System.out.print("Please insert absolute path of an ipa packet\n");
            }
        }
    }
    // Check if file exists and is a ipa file
    private static boolean isFileIpa(String file) throws IOException {

            byte[] bytes;
            Path path = Paths.get(file);
            bytes = Files.readAllBytes(path);
            File f = new File(file);
            int[] magic_ipa = { (byte)0x50, (byte)0x4B, (byte)0x03, (byte)0x04 };
            int[] macho_magic = IntStream.range(0, 4).map(i -> bytes[i]).toArray();
            return f.exists() && Arrays.equals(magic_ipa, macho_magic);
            
    }

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
}
