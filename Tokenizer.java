package com.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains everything related to the lexicon before it is written to a file
 */
public class Tokenizer {
    private ArrayList<String> tokens;
    private HashMap<String, Integer> termCounts;

    public Tokenizer() {
        tokens = new ArrayList <>();
        termCounts = new HashMap<>();
    }

    // This gets the string/int mapping from the lexicon
    public ArrayList<String> getTokens(){
        return this.tokens;
    }

    public HashMap<String, Integer> getTermCounts(){ return this.termCounts; }

    /*This method breaks the document into tokens for processing*/

    public void Tokenize(String text, Lexicon lexicon) {
        text = text.toLowerCase();
        int start = 0;
        int i;
        for (i = 0; i < text.length(); ++i){
            char c = text.charAt(i);
            if(!Character.isLetterOrDigit(c)){
                if(start != i) {
                    // uncomment this line for stemming
                    //String token = Stemmer.stem(text.substring(start, i));
                    String token = text.substring(start,i);
                    if(token.length() > 0) {
                        this.tokens.add(token);
                        if(termCounts.containsKey(token)){
                            termCounts.put(token, termCounts.get(token)+1);
                        } else {
                            termCounts.put(token, 1);
                        }
                    }
                }
                start = i + 1;
            }
        }

        if(start != i) {
            // uncomment this line for stemming
            //String tmp = Stemmer.stem(text.substring(start, i));
            String tmp = text.substring(start,i);
            if(tmp.length() > 0) {
                tokens.add(tmp);
                if (termCounts.containsKey(tmp)) {
                    termCounts.put(tmp, termCounts.get(tmp) + 1);
                } else {
                    termCounts.put(tmp, 1);
                }
            }
        }

    }




}
