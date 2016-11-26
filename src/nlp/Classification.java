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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tama
 */
public class Classification {
    ArrayList<String> listPositifWord ;
    ArrayList<String> listNegatifWord ;
    
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
}
