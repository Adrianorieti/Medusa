package com.securityChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class securityChecker{

    static String binaryName;

    public static String getBinaryName() {
        return binaryName;
    }

    private static String OS = System.getProperty("os.name").toLowerCase();

    public securityChecker(String binaryName) {
        securityChecker.binaryName = binaryName;
    }
    
    /**
     * Check if the MACH-O binary has compilation flags
     * @throws IOException
     */
    public void performSecurityChecks() throws IOException{

        ProcessBuilder processBuilder = new ProcessBuilder();

        if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
        {

            // -- Linux --
            System.out.println("sono qui");
            String currentDir = System.getProperty("user.dir");
            String abs_bin_path = currentDir + "/" + getBinaryName();
            String abs_py_path = currentDir + "/dist/checksec/checksec"; 
            String abs_rules_path = currentDir + "/rules";
            String abs_payload_path = currentDir + "/result/Payload";

            // Run a shell command
            processBuilder.command("bash", "-c", abs_py_path + " " + abs_bin_path + " " + abs_rules_path + " " + abs_payload_path );
            
        }else if(OS.contains("win"))
        {

            // -- Windows --
            String currentDir = System.getProperty("user.dir");
            String abs_bin_path = currentDir + "\\" + getBinaryName();
            String abs_py_path = currentDir + "\\dist\\checksec\\checksec"; 
            System.out.println(abs_bin_path);
            System.out.println(abs_py_path);
            // Run a command
            //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\);
            
            // Run a bat file
            //processBuilder.command("C:\\Users\\test.bat");
        }
            
        try {
    
            Process process = processBuilder.start();
    
            StringBuilder output = new StringBuilder();
    
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
    
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line + "\n");
            }
    
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.exit(0);
            } else {
                System.out.println("An error occurred");
            }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
   

}