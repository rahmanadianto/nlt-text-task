/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

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
        DataSource source = new DataSource("src/dataset/NLP-removeDuplication.arff");
        Instances data = source.getDataSet();
        //System.out.println(data);
        Procedure P = new Procedure() ;
        Preprocessing pr = new Preprocessing() ;
        Instances newData = pr.removeNouns(data);
        //System.out.println(newData);
        String filePath = "src/dataset/NLP-removeNoun.arff";
        P.writeInstances(newData,filePath);
     //   pr.testNormalize();
        
    }
    
}
