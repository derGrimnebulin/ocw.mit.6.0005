package piwords;

import java.util.HashMap;
import java.util.Map;

public class WordFinder {
    /**
     * Given a String (the haystack) and an array of Strings (the needles),
     * return a Map<String, Integer>, where keys in the map correspond to
     * elements of needles that were found as substrings of haystack, and the
     * value for each key is the lowest index of haystack at which that needle
     * was found. A needle that was not found in the haystack should not be
     * returned in the output map.
     * 
     * @param haystack The string to search into.
     * @param needles The array of strings to search for. This array is not
     *                mutated.
     * @return The list of needles that were found in the haystack.
     */
    public static Map<String, Integer> getSubstrings(String haystack, String[] needles) { 
        // TODO: Implement (Problem 4.b)
    		Map<String, Integer> needlesFound = new HashMap<String, Integer>();
    		for (String needle : needles) {
    			//get length of needle
    			int l = needle.length();
    			//check each l-tad of haystack for equality with needle 
    			//and record index in HashMap
    			for (int i = 0; i < haystack.length()-l+1 ; i++ ) {
    				if (haystack.substring(i,i+l).equals(needle)) 
    					//System.out.println("hi");
    					needlesFound.put(needle, i);
   
    			}
    			
    		}
        return needlesFound;
    }
}
