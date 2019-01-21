package com.app;

import java.util.*;

/**
 * This class builds the resultset, it is based off of the class
 * provided by Dr. Smucker for homework 3
 */
public class Results  {
    public class Result implements Comparable{
        private String docNo;
        private double score;

        public Result(String docNo, double score){
            this.docNo = docNo;
            this.score = score;
        }

        public void setDocNo(String docNo){
            this.docNo = docNo;
        }

        public String getDocNo(){
            return this.docNo;
        }

        public double getScore(){
            return this.score;
        }

        public void setScore(double score){
            this.score = score;
        }

        public int compareTo(Object r) {
            if(this.score > ((Result) r).getScore()) {
                return -1;
            } else if(this.score < ((Result) r).getScore()){
                return 1;
            } else{
                return 0;
            }
        }
    }

    private List<Result> resultSet;
    private String topicID;


    public Results(String topicID) {
        this.topicID = topicID;
        this.resultSet = new ArrayList<>();
    }

    public void Add(String docNo, double score){
        Result toAdd = new Result(docNo, score);
        resultSet.add(toAdd);
    }

    public void Sort(){
        Collections.sort(this.resultSet);
    }

    public String getTopicID(){
        return topicID;
    }

    public List<Result> GetResultSet(){
        return this.resultSet;
    }

}
