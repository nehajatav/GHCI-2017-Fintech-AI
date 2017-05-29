package com.example.enrich;

import java.util.*;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author sriramana
 */
public class StanfordNER {
  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
    props.put("regexner.mapping", "locations.txt");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    List<EmbeddedToken> tokens = new ArrayList<EmbeddedToken>();
    for (String s : args) {
      Annotation document = new Annotation(s);
      pipeline.annotate(document);
      List<CoreMap> sentences = document.get(SentencesAnnotation.class);
      StringBuilder sb = new StringBuilder();
      for (CoreMap sentence : sentences) {
        String prevNeToken = "O";
        String currNeToken = "O";
        boolean newToken = true;
        for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
          currNeToken = token.get(NamedEntityTagAnnotation.class);
          String word = token.get(TextAnnotation.class);
          if (currNeToken.equals("O")) {
            if (!prevNeToken.equals("O") && (sb.length() > 0)) {
              handleEntity(prevNeToken, sb, tokens);
              newToken = true;
            }
            continue;
          }

          if (newToken) {
            prevNeToken = currNeToken;
            newToken = false;
            sb.append(word);
            continue;
          }

          if (currNeToken.equals(prevNeToken)) {
            sb.append(" " + word);
          } else {
            handleEntity(prevNeToken, sb, tokens);
            newToken = true;
          }
          prevNeToken = currNeToken;
        }
      }
      System.out.println("We extracted " + tokens.size() + " tokens of interest from the input text");
    }
  }

  private static void handleEntity(String inKey, StringBuilder inSb, List<EmbeddedToken> inTokens) {
    System.out.println(inSb + " is a " + inKey);
    inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
    inSb.setLength(0);
  }
}

class EmbeddedToken {

  private String name;
  private String value;

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public EmbeddedToken(String name, String value) {
    super();
    this.name = name;
    this.value = value;
  }
}

