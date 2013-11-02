package piwords;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AlphabetGenerator {
   

		/**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
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
    public static final char[] generateFrequencyAlphabet(int base, String[] trainingData) {
    	
    		// Check the base
    		if ( base < 0 )
    			return null;
    		
    		// Initialize alphabet.
    		final char[] FrequencyAlphabet = new char[base];
    		// Generate histogram from trainingData.
    		final Map<Character, Integer> histogram = histogram(trainingData);
    		// Generate PDF from histogram.
    		final Map<Character, Double> PDF = Pr(histogram);
    		// Generate CDF from histogram.
    		final Map<Character, Double> CDF = CDF(PDF);
    		
    		// Sort key set values in sorted array.
    		Object[] keyArray = CDF.keySet().toArray();
    		Arrays.sort(keyArray);
    		
    		// Variable to store the left limit 
    		int sinister = 0;
    		for ( Object key : keyArray ) {
    			int dexter = (int) Math.round( CDF.get(key) * base );
    			
    			for (int i = sinister; i < dexter; i++) {
    				FrequencyAlphabet[i] = (Character) key; 
    			}
    			
    			sinister = dexter;
    		}
    		
        return FrequencyAlphabet;
    }
    
    
    
    /**
     * Given an Array of strings, return a Map<Character, Integer> representing the histogram of the frequency 
     * of distinct chars in each element of the Array. trainingData is not mutated.
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
     * histogram is not mutated.
     * 
     * @param histogram Map between char and number of occurrences in some data from which 
     * 									the probability that a given char will be encountered is derived.
     * 
     * @return PDF      Map between char and probability that a given char will be encountered
     * 									in given set of data.
     */
    public static final Map<Character, Double> Pr( Map<Character, Integer> histogram) {
    		// Round to 3 decimal places.
  			final DecimalFormat round = new DecimalFormat("#.###");
  			round.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
    	
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
    		
    		for ( Object key : keyArray ) {
    			// Place the probability of encountering each key into PDF
    			double value = new Double(round.format(histogram.get(key) / total));
    			PDF.put( (Character) key, value );
    			
    		}
    		return PDF;
    }
    
    /**
     * Given a probability distribution function represented by a Map<Character, Double>, 
     * returns a Cumulative Distribution Function also represented by a Map<Character, Double>.
     * PDF is not mutated.
     * 
     * @param  PDF the probability distribution function to be converted into a cumulative 
     *         distribution function.
     * @return A Map<Character, Double> representing a cumulative 
     *         distribution function.
     */
    public static final Map<Character, Double> CDF( Map<Character, Double> PDF) {
    		//Round to 3 decimal places.
    		final DecimalFormat round = new DecimalFormat("#.###");
    		round.setRoundingMode(java.math.RoundingMode.HALF_DOWN);
    		
    		// Initialize CDF Map.
    	  final	Map<Character, Double> CDF = new HashMap<Character, Double>();
    		
    		// Accumulate PDF values.
    		double accumulate = 0.0;
    		
    		// Get key set from PDF, convert to Array.
    		Object[] keyArray = PDF.keySet().toArray();
    		// Ensure lexigraphical order.
    		Arrays.sort(keyArray);
    		
    		for (Object key : keyArray ) {
    			//accumulate Pr(key).
    			accumulate += new Double( round.format( PDF.get(key) ) ) ;
    			
    			//Current value of accumulate is 
    			//CDF value of key.
    			CDF.put( (Character)key, accumulate );
    		}
    		
    		return CDF;
    }
    
    
    
}
