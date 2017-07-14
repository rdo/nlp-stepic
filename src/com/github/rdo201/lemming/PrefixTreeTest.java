package com.github.rdo201.lemming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PrefixTreeTest {

    public static void main(String[] args) throws Exception {
        PrefixTree tree = fillPrefixTree();
        List<String> set = importTestSet();
        Map<String, String> types = getTypes();

        double total = 0;
        double miss = 0;
        File dest = new File("data/result.txt");
        File errors = new File("data/errors.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
        BufferedWriter er = new BufferedWriter(new FileWriter(errors));
        for (String word : set) {
            if (word.equals("\n")) {
                bw.newLine();
                continue;
            }
            total++;
            String type = null;
            String originalForm;
            if(types.containsKey(word.toLowerCase())) {
                originalForm = word.toLowerCase();
            }
            else {
                originalForm = tree.get(word);
            }
            if (originalForm == null) {
                originalForm = tree.get(word.toLowerCase());
            }
            if (originalForm == null) {
                miss++;
                er.write(word);
                er.newLine();
                originalForm = word;
            }
            String computedType = types.get(originalForm.toLowerCase());
            String line = word + "{" + originalForm +"="+ (computedType != null ? computedType : "NI") +"}";
            bw.write(line);
            bw.write(" ");
        }
        System.out.println("Error rate:" + miss/(total+1));
        bw.flush();
        er.flush();
    }

    private static List<String> importTestSet() {
        List<String> result = new ArrayList<>();
        try {
            File data = new File("data/dataset.txt");
            BufferedReader br = new BufferedReader(new FileReader(data));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("[ .,!?]");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim();
                    if(!word.isEmpty()) result.add(word);
                }
                result.add("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static PrefixTree fillPrefixTree() {
        PrefixTree tree = new PrefixTree();
        AtomicLong lines = new AtomicLong(0);
        AtomicLong words = new AtomicLong(0);
        String line = null;
        try {
            File data = new File("data/dictionary-forms.csv");

            BufferedReader br = new BufferedReader(new FileReader(data));

            while ((line = br.readLine()) != null) {
                if (line.length() == 1) {
                    continue;
                }
                lines.incrementAndGet();
                String[] forms = line.split(",");
                tree.put(forms[0], forms[0]);
                for (int i = 2; i < forms.length; i++) {
                    tree.put(forms[i], forms[0]);
                    words.incrementAndGet();
                }
            }
        } catch (Exception e) {
            System.out.println("lines:" + lines.longValue());
            System.out.println("words: " + words.longValue());
            System.out.println("currentLine:" + line);
            e.printStackTrace();
        }


        System.out.println("lines:" + lines.longValue());
        System.out.println("words: " + words.longValue());

        return tree;
    }

    private static Map<String, String> getTypes() {
        Map<String, String> types = new HashMap<>();
        String line = null;
        try {

            File data = new File("data/types.txt");

            BufferedReader br = new BufferedReader(new FileReader(data));

            while ((line = br.readLine()) != null) {
                if (line.length() == 1) {
                    continue;
                }
                String[] wordAndType = line.split(" ");
                types.put(wordAndType[0].toLowerCase(), wordAndType[1]);
            }

        } catch (Exception e) {
            System.out.println(line);
            e.printStackTrace();
        }
        return types;
    }
}
