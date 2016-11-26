/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author tama
 */
public class NLP {
//    Urutan data (dari awal)
//    1. dataNLP.arff
//    2. NLP-deletedhttp.arff
//    3. NLP-deleted@.arff
//         NLP-lowerCase.arff
    //NLP-removeNewLine
//         NLP-emoticonConver.arff
//    4. NLP-normalize.arff
//    5. NLP-deletedNonAlfabetChar
    // 6. NLP-stemming

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource("src/dataset/NLP-final.arff");
        Instances data = source.getDataSet();
        //System.out.println(data);
        Procedure P = new Procedure() ;
        Preprocessing pr = new Preprocessing() ;
        Classification C = new Classification() ;
//        HashMap<String,Double> wordWeight = C.getWordWeight();
//        Instances newData = C.doClassification(data, wordWeight);
//        P.writeInstances(newData,"src/dataset/NLP-final");
//        
        int[] result ;
        Map<String,Double> rankMerk = new HashMap<String,Double>() ;
        result = C.countMerk("SAMSUNG", data);
        System.out.println("SAMSUNG : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
        rankMerk.put("SAMSUNG", (double)result[1]*100/result[0]);
        result = C.countMerk("VIVO", data);
        System.out.println("VIVO : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
          rankMerk.put("VIVO", (double)result[1]*100/result[0]);
        result = C.countMerk("XIAOMI", data);
        System.out.println("XIAOMI : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
          rankMerk.put("XIAOMI", (double)result[1]*100/result[0]);
        result = C.countMerk("IPHONE", data);
        System.out.println("IPHONE : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
        rankMerk.put("IPHONE", (double)result[1]*100/result[0]);
        result = C.countMerk("OPPO", data);
        System.out.println("OPPO : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
        rankMerk.put("OPPO", (double)result[1]*100/result[0]);
        result = C.countMerk("LENOVO", data);
        System.out.println("LENOVO : POSITIVE "+ result[1]+" ("+(double)result[1]*100/result[0]+"%)"
            +" NEGATIVE : "+result[2]+" ("+(double)result[2]*100/result[0]+"%)"+ "TOTAL DATA : "+result[0]);
        rankMerk.put("LENOVO", (double)result[1]*100/result[0]);
        
        Map<String,Double> sortedRank = MapUtil.sortByValue(rankMerk);
        System.out.println("\n======== Rank result ========\n");
        int numRank=1 ;
        for(Map.Entry<String, Double> entry : sortedRank.entrySet()) {
             System.out.println("Rank - "+numRank+" : "+entry.getKey()+"  =>  "+entry.getValue()+" %");
             numRank++;
        }
      
    }
    
}
