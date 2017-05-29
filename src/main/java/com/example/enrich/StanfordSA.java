package com.example.enrich;

import java.util.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.sentiment.SentimentCoreAnnotations$AnnotatedTree;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author sriramana
 */
public class StanfordSA {
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    String[] sentimentText = { "Very Negative","Negative", "Neutral", "Positive", "Very Positive"};
    Annotation annotation = pipeline.process(args[0]);
    for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
      Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
      System.out.println(RNNCoreAnnotations.getPredictedClass(tree));      int score = RNNCoreAnnotations.getPredictedClass(tree);
      System.out.println(sentimentText[score]);
    }
  }
}

 



