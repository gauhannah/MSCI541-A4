package com.app;

import sun.security.util.Length;

import java.io.*;

/**
 * This class contains everything related to handling the postings list
 *
 */
public class PostingsList {
    private SimpleListOfInt[] list;
    private int length;

    public PostingsList(String path) {
        this.list = new SimpleListOfInt[2];
        this.length = 0;
        LoadPostingsList(path);
    }

    /*
    * Used to copy and double the length of the postings list
    * */
    public SimpleListOfInt getPostingsList(int idx) {
        if(idx < 0 || idx > this.length){
            return null;
        }
        else { return list[idx]; }
    }

    private void CopyAndDouble() {
        SimpleListOfInt[] tmp = new SimpleListOfInt[this.list.length * 2 ];
        for (int i = 0; i < this.length; ++i){
            tmp[i] = this.list[i];
        }
        this.list = tmp;
    }

    // This loads the postings list from a file
    private void LoadPostingsList(String postingsPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(postingsPath))) {
            String line = br.readLine();
            while(line != null) {
                String[] tmp = line.split(",");
                int termID = Integer.parseInt(tmp[0]);
                if(this.list.length == termID) {
                    CopyAndDouble();
                }
                if (this.list[termID] == null) {
                    this.list[termID] = new SimpleListOfInt();
                    this.list[termID].Add(Integer.parseInt(tmp[1]));
                    this.list[termID].Add(Integer.parseInt(tmp[2]));
                    this.length += 1;
                } else {
                    this.list[termID].Add(Integer.parseInt(tmp[1]));
                    this.list[termID].Add(Integer.parseInt(tmp[2]));
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException x){
            x.printStackTrace();
        }

    }

}
