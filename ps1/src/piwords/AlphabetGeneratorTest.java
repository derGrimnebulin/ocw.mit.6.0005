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
        // Expect null if base < 0.
        assertArrayEquals(null, AlphabetGenerator.generateFrequencyAlphabet(-1, trainingData));
        // Expect [] if base = 0;
        
        assertArrayEquals(new char[0], AlphabetGenerator.generateFrequencyAlphabet(0, trainingData));
        
        char[] expectedOutput1 = {'b'};
        assertArrayEquals( expectedOutput1, AlphabetGenerator.generateFrequencyAlphabet(1, trainingData));
        
        char[] expectedOutput2 = {'a','b'};
        assertArrayEquals( expectedOutput2, AlphabetGenerator.generateFrequencyAlphabet(2, trainingData));
    }
 
    @Test
    public void histogramTest() {
    		String[] trainingData1 = {"aa", "bbc"};
    		Map<Character, Integer>	expectedOutput1 = new HashMap<Character, Integer>();
    		//expect the entries below to be returned
    		expectedOutput1.put('a', 2);
    		expectedOutput1.put('b', 2);
    		expectedOutput1.put('c', 1);
    		
    		assertEquals(expectedOutput1, AlphabetGenerator.histogram(trainingData1));
    		
    		String[] trainingData2 = {"aa","bbc","\nb"};
    		Map<Character, Integer>	expectedOutput2 = new HashMap<Character, Integer>();
    		//Expect the entries below to be returned
    		expectedOutput2.put('a', 2);
    		expectedOutput2.put('b', 3);
    		expectedOutput2.put('c', 1);
    		
    		assertEquals(expectedOutput2, AlphabetGenerator.histogram(trainingData2));
    		
    }
    
		@Test
    public void PrTest() {
    		// Expect in trainingData = {"aa","bbc"} that Pr(a) = 2/5, Pr(b) = 2/5, Pr(c) = 1/5.
    		Map<Character, Integer>	histogram1 = new HashMap<Character, Integer>();
    		histogram1.put( 'a', 2 );
    		histogram1.put( 'b', 2 );
    		histogram1.put( 'c', 1 );
    		Map<Character, Double> PDF1 = new HashMap<Character, Double>();
    		PDF1.put( 'a', 0.4 );
    		PDF1.put( 'b', 0.4 );
    		PDF1.put( 'c', 0.2 );
    		assertEquals(PDF1, AlphabetGenerator.Pr( histogram1 ));

    		
    		// Expect in trainingData = {"aa","bbc","\nb"} that Pr(a) = 2/6, 
    		//Pr(b) = 3/6, Pr(c) = 1/6, Pr(\n) = 0/6.
    		Map<Character, Integer>	histogram2 = new HashMap<Character, Integer>();
    		histogram2.put( 'a', 2 );
    		histogram2.put( 'b', 3 );
    		histogram2.put( 'c', 1 );
    		Map<Character, Double> PDF2 = new HashMap<Character, Double>();
    		PDF2.put( 'a', 0.333 );
    		PDF2.put( 'b', 0.5  );
    		PDF2.put( 'c', 0.167 );
    		assertEquals(PDF2, AlphabetGenerator.Pr(histogram2));

    }
		
		@Test
		public void CDFTest() {
				// Expect in trainingData = {"aa","bbc","\nb"} that CDF(a) = 0.333..., 
				// CDF(b) = 0.833..., CDF(c) = 1.0;
				Map<Character, Double> PDF1 = new HashMap<Character, Double>();
				PDF1.put( 'a', 0.3333 );
				PDF1.put( 'b', 0.5 );
				PDF1.put( 'c', 0.1667  );
				
				Map<Character, Double> CDF1 = new HashMap<Character, Double>();
				CDF1.put( 'a', 0.333 );
				CDF1.put( 'b', 0.833 );
				CDF1.put( 'c', 1.0 );
				
				assertEquals(CDF1, AlphabetGenerator.CDF(PDF1));


		}
}
