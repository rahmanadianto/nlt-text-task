/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

import java.sql.*;
 import weka.core.Attribute;
import weka.core.DenseInstance;
 import weka.core.FastVector;
 import weka.core.Instance;
 import weka.core.Instances;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author tama
 */
public class Procedure {
  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String DB_URL = "jdbc:mysql://localhost/twitter";

    //  Database credentials
    static final String USER = "root";
    static final String PASSWORD = "";
    
    Procedure() {}
    
    
    public Instances getInstanceFromSQL() { 
       FastVector atts ;        
        atts = new FastVector();
        FastVector attVals;
        FastVector attVals2;       
        atts.addElement(new Attribute("Tweet",(FastVector) null));
        attVals = new FastVector();
        attVals.addElement("SAMSUNG");
        attVals.addElement("IPHONE");
        attVals.addElement("VIVO");
        attVals.addElement("OPPO");
        attVals.addElement("XIAOMI");
        attVals.addElement("LENOVO");
        atts.addElement(new Attribute("Category",attVals));
        attVals2 = new FastVector();
        attVals2.addElement("POSITIVE");
        attVals2.addElement("NEUTRAL");
        attVals2.addElement("NEGATIVE");
        attVals2.addElement("UNCATEGORIZED");
        atts.addElement(new Attribute("Sentiment",attVals2));
        Instances data = new Instances("NLP",atts,0); ;
        
        System.out.println(data.toString());
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection  conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT * FROM tweets";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                double[] tempInst = new double[data.numAttributes()];
               
                String tweet = rs.getString("tweet");
                String category = rs.getString("category");
                if (category.equalsIgnoreCase("sony")) category = "oppo";
                 tempInst[0] = data.attribute(0).addStringValue(tweet);
                tempInst[1] = attVals.indexOf(category.toUpperCase());
                  tempInst[2] = attVals2.indexOf("UNCATEGORIZED");
                  data.add(new DenseInstance(1.0,tempInst));

            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found ex");
        } catch (SQLException ex) {
            System.out.println("SQL Ex");
        }
        return data;
               
    }
    
    public void writeInstances(Instances data,String filePath) {
        File file = new File(filePath);
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data.toString());
            bw.close();
            System.out.println("Done");

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
