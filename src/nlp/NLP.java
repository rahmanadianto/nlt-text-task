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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        DataSource source = new DataSource("src/dataset/dataNLP.arff");
        Instances data = source.getDataSet();
        //System.out.println(data);
        Procedure P = new Procedure() ;
        Preprocessing pr = new Preprocessing() ;
        Instances newData = pr.deletehttpsorhttp(data);
        String filePath = "src/dataset/NLP-deletedhttp.arff";
       P.writeInstances(newData,filePath);
    }
    
}
