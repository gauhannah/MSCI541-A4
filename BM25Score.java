package com.app;

import java.util.HashMap;

/**
This class calculates the BM25 scores for all documents for a topic */
public class BM25Score {

    // formula constants
    private static final double k1 = 1.2;
    private static final double k2 = 7;
    private static final double b = 0.75;

    public static void CalculateBM25Scores(Results resultSet, HashMap<String, Integer> terms, WordCounts wordCounts, PostingsList postingsList, Index index, Lexicon lexicon){
       for(Integer docID: index.getIndex().keySet()){
           double docLength = wordCounts.getWordCounts().get(index.GetDocNo(docID));
           double score = CalculateDocumentScore(lexicon, docLength, wordCounts.averageWordCount, postingsList, docID, terms, index);
           if(score > 0.0) {
               resultSet.Add(index.GetDocNo(docID), score);
           }
       }

    }

    // calculates the BM25 score for 1 document
    private static double CalculateDocumentScore(Lexicon lexicon, double docLength, double avgDocLength,
                    PostingsList postingsList, Integer docId, HashMap<String, Integer> termCounts, Index idx){
        double K = CalculateK(docLength, avgDocLength);
        double result = 0;
        for(String term: termCounts.keySet()){
            Integer termID = lexicon.GetLexicon().get(term);
            if(termID == null) {
                System.out.println(term);
            }
            double fi = getDocOccurances(termID,docId, postingsList);
            // calculate inside the summation for bm25 score
            double term1 = ((k1 + 1)*fi)/(K + fi);
            double term2 = ((k2 + 1)/(k2+termCounts.get(term))*termCounts.get(term));
            double term3 = Math.log((idx.getIndex().size()-getNumDocs(termID, postingsList)+0.5)/(getNumDocs(termID, postingsList)+0.5));
            result += term1*term2*term3;
        }
        return result;
    }

    private static double CalculateK(double docLength, double avgDocLength) {
        return k1*((1-b)+b*(docLength/avgDocLength));
    }


    // get the number of docs containing a term
    private static double getNumDocs(Integer termID, PostingsList postingsList) {
        return postingsList.getPostingsList(termID).Length()/2.0;
    }

    // get the number of occurences inside a doc
    public static double getDocOccurances(Integer termID, int docId, PostingsList postingsList){
        SimpleListOfInt postings = null;
        try {
            postings = postingsList.getPostingsList(termID);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(postings != null){
            for(int i = 0; i < postings.Length(); i+=2){
                if(postings.Get(i) == docId){
                    return postings.Get(i+1);
                }
            }
        }
        return 0;
    }




}
