package com.app;

import javafx.geometry.Pos;

import java.util.HashMap;

/**
 * This class calculates the cosine similarity for each doc/query
 */
public class CosineSimilarity {

    public static void CalculateCosineSimilarity(Results resultSet, HashMap<String, Integer> terms, WordCounts wordCounts, DocToTermCount docToTerm, Index index, Lexicon lexicon, PostingsList postings){
        for(int i = 0; i < docToTerm.getLength(); ++i){
            double score = CalculateDocumentScore(lexicon, docToTerm, i, terms, index, postings);
            if(score > 0.0) {
                resultSet.Add(index.GetDocNo(i), score);
            }
        }
        System.out.println("done");
    }

    private static double CalculateDocumentScore(Lexicon lexicon, DocToTermCount docToTerm, Integer docId, HashMap<String, Integer> terms, Index idx, PostingsList postings){

        if(docToTerm.GetList(docId) != null) {
            double documentWeight = CalculateDocumentWeight(docToTerm.GetList(docId), idx.getIndex().size(), postings);
            double cosineSimilarity = 0;
            SimpleListOfInt document = docToTerm.GetList(docId);
            for (String term : terms.keySet()) {
                double ft = 0;
                for (int i = 0; i < document.Length(); i += 2) {
                    if (document.Get(i) == lexicon.GetLexicon().get(term)) {
                        ft = document.Get(i + 1);
                        break;
                    }
                }
                cosineSimilarity += ft * Math.log((double)idx.getIndex().size()/((double)docToTerm.getLength()/2.0));
            }
            cosineSimilarity = cosineSimilarity / documentWeight;
            return cosineSimilarity;
        }
        return 0;
    }


    // calculates the document weight
    private static double CalculateDocumentWeight(SimpleListOfInt docTerms, int docCount, PostingsList postings ){
        double weight = 0;
        for (int i = 0; i < docTerms.Length(); i+=2){
            weight += Math.pow(CalculateInsideWeight(docTerms.Get(i+1), postings.getPostingsList(i), docCount),2);
        }
        weight = Math.sqrt(weight);
        return weight;

    }

    // calculates the inside term within the document weight
    private static double CalculateInsideWeight(int wordCount, SimpleListOfInt postings, int docCount){
        return (double)wordCount*Math.log((double)docCount/(postings.Length()/2));
    }
}
