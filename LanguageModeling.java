package com.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is probably (no, definitely) wrong but I tried :)
 */
public class LanguageModeling {


    public static void CalculateLanguageModelling(Results resultSet, HashMap<String, Integer> terms, WordCounts wordCounts, DocToTermCount docToTerm, Index index, Lexicon lexicon, PostingsList postings){
        for(int i = 0; i < docToTerm.getLength(); ++i){
            double score = CalculateDocumentScore(lexicon, docToTerm, i, terms, index, postings, wordCounts);
            if(score > 0.0) {
                resultSet.Add(index.GetDocNo(i), score);
            }
        }
        System.out.println("done");

    }

    private static double CalculateDocumentScore(Lexicon lexicon, DocToTermCount docToTerm, Integer docId, HashMap<String, Integer> terms, Index idx, PostingsList postings, WordCounts wordcounts) {
        double score = 0;
        ArrayList<Integer> termSet = GetIntersection(docToTerm, terms, lexicon);
        double wordcount = (double)wordcounts.getWordCounts().get(idx.GetDocNo(docId));
        double lambda = CalculateLambda(wordcount);
        double countInQuery;
        double mle;
        double PWinC;
        double occurancesInCollection;
        for(Integer term: termSet){
            countInQuery = (double)terms.get(lexicon.Get(term));
            mle = CalculateMLE((double)terms.get(lexicon.Get(term)), wordcount);
            occurancesInCollection = (double)postings.getPostingsList(term).Length()/2.0;
            PWinC = CalculatePWinC(occurancesInCollection,wordcount);
            score += countInQuery*Math.log(((1-lambda)*mle+lambda*PWinC)/(lambda*PWinC));
        }
        for(String term: terms.keySet()){
            score += terms.get(term)*Math.log(lambda);
        }
        for(String term: terms.keySet()){
            occurancesInCollection = (double)postings.getPostingsList(lexicon.GetLexicon().get(term)).Length()/2.0;
            score += terms.get(term)*CalculatePWinC(occurancesInCollection,wordcount);
        }

        return score;
    }

    private  static ArrayList<Integer> GetIntersection(DocToTermCount docToTerm, HashMap<String, Integer> queryTerms, Lexicon lexicon){
        ArrayList<Integer> intersection = new ArrayList<>();
        for(String term: queryTerms.keySet()){
            if(docToTerm.Contains(lexicon.GetLexicon().get(term))){
                intersection.add(lexicon.GetLexicon().get(term));
            }
        }
        return intersection;
    }

    private static double CalculateLambda(double docLength){
        return docLength/(docLength + 1000);
    }

    private static double CalculatePWinC(double occurances, double collectionSize){
        return occurances/collectionSize;
    }

    private static double CalculateMLE(double occurancesInDoc, double docLength){
        return occurancesInDoc/docLength;
    }
}
