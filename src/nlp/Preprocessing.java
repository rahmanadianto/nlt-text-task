/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import IndonesianNLP.* ;
/**
 *
 * @author tama
 */
public class Preprocessing {
    
    Preprocessing() {
     
    }
    
    public Instances deletehttpsorhttp(Instances data) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
      
        Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            if (tweet.contains("http")) {
                //System.out.println("before | "+tweet);
                 Matcher m = p.matcher(tweet);
                int j = 0;
                while (m.find()) {
                    tweet = tweet.replaceAll(m.group(j),"").trim();
                    j++;
                }
                //System.out.println("after | "+cleartext);
                dataT.setValue(attr, tweet);
                //System.out.println(tweet);
            }
            //System.out.println(dataT);
            newData.add(dataT);
        }
        return newData ;
    }
    
    public Instances deleteMentionTag(Instances data) {
        Attribute attr = data.attribute("Tweet");
       Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            if (tweet.contains("@")) {
                //System.out.println("before | "+tweet);
                tweet = tweet.replaceAll("@[A-Za-z]+","");
                //System.out.println("after | "+cleartext);
               
                //System.out.println(tweet);
            }
            //System.out.println(dataT);
            newData.add(dataT);
        }
        return newData ;
    }
    
    public Instances normalizeInstances(Instances data) {
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
//        String sentence = "kata2nya 4ku donk loecoe bangedh gt .";
//        sentence = formalizer.formalizeSentence(sentence);
//
          formalizer.initStopword();
//        System.out.println(formalizer.deleteStopword(sentence));

        Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
           tweet = formalizer.formalizeSentence(tweet);
           tweet = formalizer.deleteStopword(tweet);
            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
    }
    
    public void testNormalize() {
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        String sentence = "kata2nya 4ku donk loecoe bangedh gt .";
        sentence = formalizer.formalizeSentence(sentence);

        formalizer.initStopword();
        System.out.println(formalizer.deleteStopword(sentence));

    }
    
    public Instances deleteAllNonAlphabetCharaceter(Instances data) {
        Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            tweet = tweet.replaceAll("[^A-Za-z\\s]", "");
            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
    }
    
    public Instances convertToLowerCase(Instances data) {
            Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            tweet = tweet.toLowerCase();
            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
    }
    
      public Instances removeNewLine(Instances data) {
            Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            tweet = tweet.replace("\n", " ");
            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
    }
      
      public Instances convertEmoticon(Instances data) {
              Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            tweet = tweet.replace(":)", "positif");
            tweet = tweet.replace(":v", "positif");
            tweet = tweet.replace(":*", "positif");
            tweet = tweet.replace(";)", "positif");
            tweet = tweet.replace(":(", "negatif");
            tweet = tweet.replace(":d", "positif");
            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
      }
      
      public Instances stemming(Instances data) {
               Attribute attr = data.attribute("Tweet");
        Instances newData = new Instances(data,0);
        IndonesianStemmer stemmer = new IndonesianStemmer();
        for (int i=0;i<data.numInstances();i++) {
            Instance dataT = data.get(i);
            String tweet = dataT.stringValue(attr);
            tweet = stemmer.stemSentence(tweet);

            dataT.setValue(attr, tweet);
            newData.add(dataT);
        }
        return newData ;
      }
}
