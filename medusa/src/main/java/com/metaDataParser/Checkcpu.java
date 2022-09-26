package com.metaDataParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import java.util.Arrays;
import java.util.stream.IntStream;

class Checkcpu {
    
    Path path;
    String cpu = "";
    byte[] data2 = new byte[32];
    String full_cpu = " ";


  Checkcpu(String path) {
    this.path = Paths.get(path);
  }

  String parse_cafebabe(byte[] data) {

    if (data[31] == (byte) 0x0c) {
        cpu = "arm";
      if (data2[28] == (byte)0x01){
         full_cpu = cpu.concat("64");
      }
      else
         full_cpu = cpu.concat("32");
    }
    return full_cpu;
  }

  String parse_feedfacf(byte[] data) {
    if (data2[4] == (byte)0x0c) {
        cpu = "arm";
      if (data2[7] == (byte)0x01)
        full_cpu = cpu.concat("64");
      else
        full_cpu = cpu.concat("32");
    }
    return full_cpu;

  }

  /**
   * Check the binary cpu reading header
   * @return
   */
  String parse() {
    byte[] data;

    try {
      data = Files.readAllBytes(path);
      InputStream is = new FileInputStream(path.toString());
      is.read(data2);
      int[] magic_cafebabe = { (byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe };
      int[] magic_feedfacf = { (byte)0xcf, (byte)0xfa, (byte)0xed, (byte)0xfe };
      int[] macho_magic = IntStream.range(0, 4).map(i -> data[i]).toArray();
 
         if (Arrays.equals(magic_cafebabe, macho_magic)) {
        return parse_cafebabe(data);
      } else if (Arrays.equals(magic_feedfacf, macho_magic)) {
        return parse_feedfacf(data);
      } else {
        System.err.println("Unrecognized magic");
        return "";
      }
    } catch (Exception e) {
        System.err.println(e);
        return "";
    }
  }

}