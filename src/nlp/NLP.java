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
//    2. NLP-deleted@.arff
//    4. NLP-normalize.arff
//    5. NLP-deletedNonAlfabetChar

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource("src/dataset/NLP-normalize.arff");
        Instances data = source.getDataSet();
        //System.out.println(data);
        Procedure P = new Procedure() ;
        Preprocessing pr = new Preprocessing() ;
        Instances newData = pr.deleteAllNonAlphabetCharaceter(data);
        System.out.println(newData);
        String filePath = "src/dataset/NLP-deletedNonAlfabetChar.arff";
        P.writeInstances(newData,filePath);
     //   pr.testNormalize();
        
    }
    
}
