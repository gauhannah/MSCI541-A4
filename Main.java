package com.app;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String indexPath;
        String topicsPath;
        String resultsPath;
        String cosinePath;
        String LMpath;
        try {

            // get paths to relevant files from arguments
            indexPath = args[0];
            topicsPath = args[1];
            resultsPath = args[2];
            cosinePath = args[3];
            LMpath = args[4];
        } catch (Exception ex)  {
            System.out.println("incorrect number of arguments, please try again.");
            return;
        }

        Lexicon lexicon = new Lexicon(indexPath + "/lexicon.txt");
        Index index = new Index();
        index.buildIndex(indexPath + "/index.txt");
        WordCounts wordCounts = new WordCounts(indexPath);

        BufferedReader br=null;
        BufferedWriter bw = null;
        BufferedWriter writeCosine = null;
        BufferedWriter writeLM = null;

        PostingsList postingsList = new PostingsList(indexPath + "/postings.txt");
        DocToTermCount docToTermCount = new DocToTermCount(indexPath + "/docToTerm.txt");
        String line;
        Tokenizer tokens = new Tokenizer();
        try {
            br = new BufferedReader(new FileReader(topicsPath));
            bw = new BufferedWriter(new FileWriter(resultsPath));
            writeCosine = new BufferedWriter(new FileWriter(cosinePath));
            writeLM = new BufferedWriter(new FileWriter(LMpath));
           while((line = br.readLine()) != null){
               String[] tmp = line.split(":");
               // store the resultset for each of the three measures
               Results results = new Results(tmp[0]);
               Results cosineSimilarity = new Results(tmp[0]);
               Results lm = new Results(tmp[0]);

               tokens.Tokenize(tmp[1], lexicon);

               // calculate the results for each measure
               BM25Score.CalculateBM25Scores(results, tokens.getTermCounts(), wordCounts, postingsList, index, lexicon);
               CosineSimilarity.CalculateCosineSimilarity(cosineSimilarity, tokens.getTermCounts(), wordCounts,docToTermCount,index, lexicon,postingsList);
               LanguageModeling.CalculateLanguageModelling(lm, tokens.getTermCounts(),wordCounts,docToTermCount,index,lexicon,postingsList);

               results.Sort();
               cosineSimilarity.Sort();
               lm.Sort();

               //print all results to files
               PrintToFile(results, bw);
               PrintToFile(cosineSimilarity, writeCosine);
               PrintToFile(lm, writeLM);
               tokens = new Tokenizer();
           }
        } catch (Exception ex) {
                ex.printStackTrace();
        } finally{
            br.close();
            bw.close();
            writeCosine.close();
            writeLM.close();

        }


    }

    public static void PrintToFile(Results results, BufferedWriter bw) throws IOException{
        for(int i = 0; i < results.GetResultSet().size() && i < 1000; ++i){
            bw.write(results.getTopicID() + " q0 " + results.GetResultSet().get(i).getDocNo() + " " +
                    + (i+1) + " " + results.GetResultSet().get(i).getScore()
                    + " hvgautre" + System.lineSeparator());

        }
    }
}
