package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AlphabetGeneratorTest {
    @Test
    public void generateFrequencyAlphabetTest() {
     
        String[] trainingData = {"aa", "bbc"};
        // In the output for base 10, they should be in the same proportion.
        char[] expectedOutput = {'a', 'a', 'a', 'a',
                                 'b', 'b', 'b', 'b',
                                 'c', 'c'};
        assertArrayEquals(expectedOutput,
                AlphabetGenerator.generateFrequencyAlphabet(
                        10, trainingData));
    }
    
    @Test
    public void histogramTest() {
      	// Expect in the training data that Pr(a) = 2/5, Pr(b) = 2/5,
      	// Pr(c) = 1/5.
    		String[] trainingData1 = {"aa", "bbc"};
    		char[] alphabet = {'a','b','c'};
    		Map<Character, Integer>	expectedOutput1 = new HashMap<Character, Integer>();
    		//set values
    		expectedOutput1.put('a', 2);
    		expectedOutput1.put('b', 2);
    		expectedOutput1.put('c', 1);
    		
    		assertEquals(expectedOutput1, AlphabetGenerator.histogram(alphabet,trainingData1));
    		
    		
    		String[] trainingData2 = {"aa","bbc","zb"};
    		Map<Character, Integer>	expectedOutput2 = new HashMap<Character, Integer>();
    		//set values
    		expectedOutput2.put('a', 2);
    		expectedOutput2.put('b', 3);
    		expectedOutput2.put('c', 1);
    		
    		assertEquals(expectedOutput2, AlphabetGenerator.histogram(alphabet,trainingData2));
    		
    		
    }
    // TODO: Write more tests (Problem 5.a)
}
