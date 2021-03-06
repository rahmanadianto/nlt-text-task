
package nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class NLP {

    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource("src/dataset/dataNLP.arff");
        Instances data = source.getDataSet();
        
        Procedure P = new Procedure() ;
        Preprocessing pr = new Preprocessing() ;
        Classification C = new Classification() ;
        
        Instances newData = pr.deleteMentionTag(data);
        newData = pr.deletehttpsorhttp(newData);
        newData = pr.convertToLowerCase(newData);
        newData = pr.removeNewLine(newData);
        newData = pr.convertEmoticon(newData);
        newData = pr.normalizeInstances(newData);
        newData = pr.deleteAllNonAlphabetCharaceter(newData);
        newData = pr.stemming(newData);
        newData = pr.removeNouns(newData);
        P.writeInstances(newData, "src/dataset/NLP-removeNoun.arff");
        
        ArrayList<String> listWord = pr.getListWord(newData);
        P.writeArrayList(listWord, "src/list_word/wordlistfix.txt");
        
        //Sebelum ke tahap ini, kata kata pada wordlistfix.txt diterjemahkan terlebih dahulu ke bahasa inggris
        //lalu kata tersebut disimpan ke dalam file di "src/list_word/wordlist_english.txt"
        //Lalu jalankan NLP2.java
        
        String pathToSWN = "src/list_word/sentiword.txt";
        SentiWordNetDemoCode sentiwordnet = new SentiWordNetDemoCode(pathToSWN);
       sentiwordnet.getMapWord();
       
        
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
