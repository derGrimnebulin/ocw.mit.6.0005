package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WordFinderTest {
    @Test
    public void basicGetSubstringsTest() {
        String haystack1 = "abcde";
        String[] needles1 = {"ab", "abc", "de", "fg"};

        Map<String, Integer> expectedOutput1 = new HashMap<String, Integer>();
        expectedOutput1.put("ab", 0);
        expectedOutput1.put("abc", 0);
        expectedOutput1.put("de", 3);

        assertEquals(expectedOutput1, WordFinder.getSubstrings(haystack1, needles1));
        
        //Must test behavior in the event of repetition of needles and haystack 
        //so that the repeated needle appears the correct # times with the correct key.
        //also must check the endpoint of haystack so that an element of needles of len>1 beginning w/
        //haystack[len-1] is not pushed onto HashMap
        
        //Test to check for correct behavior at endpoint and the event of repeated strings
        String haystack2 = "ababcdcde";
        String[] needles2 = {"ab","e", "ef"};
        
        Map<String, Integer> expectedOutput2 = new HashMap<String, Integer>();
        expectedOutput2.put("ab", 0);
        expectedOutput2.put("ab", 2);
        expectedOutput2.put("e", 8);
        
        assertEquals(expectedOutput2, WordFinder.getSubstrings(haystack2, needles2));
        
        //Test to check for correct behavior in the event of overlapping repeated strings
        String haystack3 = "ababa";
        String[] needles3 = {"aba", "bab"};
        
        Map<String, Integer> expectedOutput3 = new HashMap<String ,Integer>();
        expectedOutput3.put("aba", 0);
        expectedOutput3.put("bab", 1);
        expectedOutput3.put("aba", 2);
        
        assertEquals(expectedOutput3, WordFinder.getSubstrings(haystack3, needles3));
        
    }

    // TODO: Write more tests (Problem 4.a)
}
