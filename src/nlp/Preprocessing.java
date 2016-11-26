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
                dataT.setValue(attr, tweet);
                //System.out.println(tweet);
            }
            //System.out.println(dataT);
            newData.add(dataT);
        }
        return newData ;
    }
}
