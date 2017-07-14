package com.github.rdo201.lemming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class WordTypesGenerator {
    public static void main(String[] args) throws Exception {
        File typesFile = new File("data/types.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(typesFile));
        /*{
            File data = new File("data/dictionary-forms.csv");
            BufferedReader br = new BufferedReader(new FileReader(data));
            //Map<String, String> result = new HashMap<>();
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("[ .,]");
                String type = words[1];
                String word = words[0];
                bw.write(word + " " + Type.getTypeByCode(type));
                bw.newLine();
            }
        }*/
        {
            File data = new File("data/dictionary-main.txt");
            BufferedReader br = new BufferedReader(new FileReader(data));
            //Map<String, String> result = new HashMap<>();
            String line = null;
            String origLine = null;
            while ((line = br.readLine()) != null) {
                try {
                    origLine = line;
                    if (line.trim().isEmpty()) continue;
                    line=line.replaceAll("\\(.+\\)", "");
                    String[] words = line.split("[ ]+");
                    String type = words[2].replaceAll("[.:,]","");
                    if (type.contains("//")) {
                        type=type.split("//")[0];
                    }
                    String word = words[0];
                    bw.write(word + " " +  Type.getTypeByCode(type));
                    bw.newLine();
                } catch (Exception e) {
                    System.out.println(origLine);
                }
            }
        }
    }
}
