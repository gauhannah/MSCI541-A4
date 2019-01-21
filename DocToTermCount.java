package com.app;

import java.io.*;
import java.util.HashMap;

/**
 * This class is an inverted postings list that maps docIds to terms
 * this is used for cosine similarity and language modeling
 */
public class DocToTermCount {
    private SimpleListOfInt[] list;
    private int length;

    public DocToTermCount(String path) {
        this.list = new SimpleListOfInt[2];
        this.length = 0;
        LoadDocToTermCount(path);
    }

    public SimpleListOfInt GetList(int idx) {
        if(idx < 0 || idx > this.length){
            return null;
        }
        else { return list[idx]; }
    }

    public int getLength(){
        return this.length;
    }


    /*
    * Used to copy and double the length of the postings list
    * */
    private void CopyAndDouble() {
        SimpleListOfInt[] tmp = new SimpleListOfInt[this.list.length * 2];
        for (int i = 0; i < this.length; ++i) {
            tmp[i] = this.list[i];
        }
        this.list = tmp;
    }

    public boolean Contains(int term){
        for(int i = 0; i < this.length; i +=2){
            if(i == term){
                return true;
            }
        }
        return false;
    }

    // This loads the postings list from a file
    private void LoadDocToTermCount(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                int docID = Integer.parseInt(tmp[0]);
                if (this.list.length == docID) {
                    CopyAndDouble();
                }
                if (this.list[docID] == null) {
                    this.list[docID] = new SimpleListOfInt();
                    this.list[docID].Add(Integer.parseInt(tmp[1]));
                    this.list[docID].Add(Integer.parseInt(tmp[2]));
                    this.length += 1;
                } else {
                    this.list[docID].Add(Integer.parseInt(tmp[1]));
                    this.list[docID].Add(Integer.parseInt(tmp[2]));
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }

    }
}