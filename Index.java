package com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class contains the index, which maps docid to docno
 */
public class Index {
    private  HashMap<Integer, String> index;

    public Index(){
        this.index = new HashMap<>();
    }

    public void buildIndex(String indexPath) throws IOException {
        BufferedReader ir = null;
        try {
            ir = new BufferedReader(new FileReader(indexPath));
            String line = ir.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                this.index.put(Integer.parseInt(tmp[0]), tmp[1]);
                line = ir.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ir.close();
        }
    }

    public HashMap<Integer, String> getIndex() {
        return index;
    }

    public  String GetDocNo(int id) {
        return this.index.get(id);
    }

}
