//    Copyright 2013 Petter Törnberg
//
//    This demo code has been kindly provided by Petter Törnberg <pettert@chalmers.se>
//    for the SentiWordNet website.
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package nlp ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SentiWordNetDemoCode {

	private Map<String, Double> dictionary;

	public SentiWordNetDemoCode(String pathToSWN) throws IOException {
		// This is our main dictionary representation
		dictionary = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try {
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Example line:
					// POS ID PosS NegS SynsetTerm#sensenumber Desc
					// a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
					// ascetic#2 practicing great self-denial;...etc

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6) {
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line: "
										+ lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank,
								synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
					.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (csv != null) {
				csv.close();
			}
		}
	}

	public double extract(String word, String pos) {
            
            if (dictionary.get(word+"#"+pos)!=null) {
            return dictionary.get(word + "#" + pos);}
            else return 0.0;
	}
	
        public double extractTotal(String word) {
            double sum = 0;
            int i=0;
            if (extract(word,"a")!=0) {
                sum+=extract(word,"a");
                i++;
            }
            if (extract(word,"n")!=0) {
                sum+=extract(word,"n");
                i++;
            }
            if (extract(word,"r")!=0) {
                sum+=extract(word,"r");
                i++;
            }
            if (extract(word,"v")!=0) {
                sum+=extract(word,"v");
                i++;
            }
            double sum2=0.0;
            if (sum!=0)return (double)sum/i ;
            else return 1 ;
           
        }
        
        public void getMapWord() {
            HashMap<String,Double> result = new HashMap<String,Double>() ;
            ArrayList<String> wordList = new ArrayList<String>() ;
            ArrayList<Double> wordWeight = new ArrayList<Double>() ;
            try (BufferedReader br = new BufferedReader(new FileReader("src/list_word/wordlistfix.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                   wordList.add(line);
                }
            }           catch (FileNotFoundException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader("src/list_word/wordlist_english.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                   double weight = extractTotal(line);
                   wordWeight.add(weight);
                }
            }           catch (FileNotFoundException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SentiWordNetDemoCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             File file = new File("src/list_word/wordweight.txt");
             //System.out.println(wordList.size());
             //System.out.println(wordWeight.size());
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i=0;i<wordList.size();i++) {
//                double positif=0.0 ;
//                double negatif = 0.0 ;
//                if(wordWeight.get(i)<0) {
//                    negatif= wordWeight.get(i)*-1 ;
//                    positif = 1
//                }else if(wordWeight)
//                
                
                bw.write(wordList.get(i)+" "+wordWeight.get(i)+"\n");
            }
            bw.close();
            System.out.println("Done");

            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        
	public static void main(String [] args) throws IOException {
		
		String pathToSWN = "src/list_word/sentiword.txt";
		SentiWordNetDemoCode sentiwordnet = new SentiWordNetDemoCode(pathToSWN);
		sentiwordnet.getMapWord();
		
	}
        
}