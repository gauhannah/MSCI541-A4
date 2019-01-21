package com.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *  This class contains everything related to the lexicon
 */
public class Lexicon {
    private HashMap<String, Integer> stringToId;
    private HashMap<Integer, String> idToString;

    public Lexicon(String path) throws IOException {
        stringToId = new HashMap<>();
        idToString = new HashMap<>();
        LoadLexicon(path);
    }

    // Load the lexicon from a file for processing
    public void LoadLexicon(String path) throws IOException {
        BufferedReader br;
        File test = new File(path);
        if(test.exists()) {
            br = new BufferedReader(new FileReader(test));
            String line = br.readLine();
            String[] tmp;
            while(line != null) {
                tmp = line.split(",");
                stringToId.put(tmp[1], Integer.parseInt(tmp[0]));
                idToString.put(Integer.parseInt(tmp[0]),tmp[1]);
                line = br.readLine();
            }
            br.close();
        }
    }

    // get the lexicon
    public HashMap<String, Integer> GetLexicon() {
        return this.stringToId;
    }

    public String Get(Integer termId){
        return this.idToString.get(termId);
    }

}
