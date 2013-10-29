package piwords;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 193
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (28 As, indexes 0-27),
     *                "b", "b", ... (47 Bs, indexes 28-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */
    public static char[] generateFrequencyAlphabet(int base, String[] trainingData) {
        // TODO: Implement (Problem 5.b)
        return null;
    }
    
    
    
    /**
     * Given an Array of strings, return a Map<Character, Integer> representing the histogram of the frequency 
     * of distinct chars in each element of the Array.
     * 
     * @param  trainingData The occurrence of each alphabetic character present here is to be counted 
     * @return histogram    A map between each character present in trainingData and it's number 
     * 											of appearances.
     */
    public static final Map<Character, Integer> histogram(String[] trainingData) {
    		//initialize histogram 
    		final Map<Character, Integer> histogram = new HashMap<Character, Integer>();
    		
    		for (int i = 0; i < trainingData.length; i++) {
    				//look at each string in trainingData array
    				String item = trainingData[i];
    				
    				for ( int j = 0; j < item.length(); j++ ) {
    						char key = item.charAt(j);
    					
    						if ( Character.isLetter(key) )
    								if (histogram.containsKey(key))
    										//increase the count in histogram if  
    										//encountered char is a valid alphabetic character
    										histogram.put(key, histogram.get(key) + 1);
    								else
    										histogram.put(key, 1);
    								
									
    				}
    		}
    		return histogram;
    }
    
    /**
     * Given a histogram containing the occurrences of alphabetic characters in data,
     * return a Map<Character, Integers> representing the Probability Distribution Function 
     * which maps each character to it's probability that it will be encountered in the data.
     * 
     * @param histogram Map between char and number of occurrences in some data from which 
     * 									the probability that a given char will be encountered is derived.
     * 
     * @return PDF      Map between char and probability that a given char will be encountered
     * 									in given set of data.
     */
    public static final Map<Character, Double> Pr( Map<Character, Integer> histogram) {
    		// Initialize PDF
    		final Map<Character, Double> PDF = new HashMap<Character, Double>();

    		// Count the gross char occurrences
    		Object[] values = histogram.values().toArray();
    		double total = 0.0;
    		for (Object item : values) {
    				total += (Integer) item;
    		}
    		
    		// Place keys in an Array
    		Object[] keyArray = histogram.keySet().toArray();
    		//Iterate over Array.
    		for ( Object key : keyArray ) {
    			// Place the probability of encountering each key into PDF
    			PDF.put( (Character) key, histogram.get(key) / total );
    			
    		}
    		return PDF;
    }
    
    
    private static final int CDF(int a) {
    		// TODO: Implement CDF calculator
    		return 0;
    }
    
    
    
}
