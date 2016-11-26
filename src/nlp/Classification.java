/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author tama
 */
public class Classification {
    ArrayList<String> listPositifWord ;
    ArrayList<String> listNegatifWord ;
    HashMap<String,Double> wordWeight ;
    Classification() {}
    
    public void initListPositifWord() {
        listPositifWord = new ArrayList<String>() ;
        try (BufferedReader br = new BufferedReader(new FileReader("src/list_word/positif.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
               listPositifWord.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Classification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Classification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void initListNegatifWord() {
        listPositifWord = new ArrayList<String>() ;
        try (BufferedReader br = new BufferedReader(new FileReader("src/list_word/negatif.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
               listPositifWord.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Classification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Classification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public HashMap<String,Double> getWordWeight() {
         HashMap<String,Double> wordWeight = new HashMap<String,Double>() ;
          try (BufferedReader br = new BufferedReader(new FileReader("src/list_word/wordweight.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    String[] word = line.split("\\s");
                    wordWeight.put(word[0],Double.valueOf(word[1]));
                }
            }           catch (FileNotFoundException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         return wordWeight ;
     }
     
     public Instances doClassification(Instances data,HashMap<String,Double> wordweight){
         Instances newData = new Instances(data,0);
         wordWeight = new HashMap<String,Double>() ;
         this.wordWeight = wordweight ;
         Attribute attr = data.attribute("Tweet");   
         Attribute attr2 = data.attribute("Sentiment");        
         for (int i=0;i<data.size();i++) {
             Instance dataT = data.get(i);
             String tweet = dataT.stringValue(attr);
             int tempint = countProbability(tweet) ;
             if (tempint > 0) {
                 dataT.setValue(attr2, "POSITIVE");
             } else {
                 dataT.setValue(attr2, "NEGATIVE");
             }
             newData.add(dataT);
         }
         return newData ;
     }
     
     public int[] countMerk(String merk,Instances data) {
         int[] result = new int[3] ;
         for (int i=0;i<3;i++) result[i] = 0;
         Attribute amerk = data.attribute("Category");
         Attribute asentiment = data.attribute("Sentiment");
         for (int i=0 ; i<data.size();i++) {
             Instance dataT = data.get(i);
             if (dataT.stringValue(amerk).equalsIgnoreCase(merk)) {
                 if (dataT.stringValue(asentiment).equalsIgnoreCase("POSITIVE")) result[1]++;
                 else if (dataT.stringValue(asentiment).equalsIgnoreCase("NEGATIVE")) result[2]++;
             }
         }
         result[0] = result[1]+result[2] ;
         return result ;
     }
     
     public int countProbability(String sentence){
        String[] words = sentence.split("\\s");
        double sum = 0 ;
        for (String word : words) {
            if (wordWeight.get(word)!=null) {
                sum+=wordWeight.get(word);
            }
        }
        if (sum>0) return 1 ;
        else return -1;
     }
}
