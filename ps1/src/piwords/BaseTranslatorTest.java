package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseTranslatorTest {
		// TODO: Add missing test case.
    @Test
    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        int[] input1 = {0, 1};
        int[] expectedOutput1 = {2, 5};
        assertArrayEquals(expectedOutput1, BaseTranslator.convertBase(input1, 2, 10, 2));
        
        int[] inputA = {0,0,1,0};
        int[] expectedOutput2 = {1,2,5};
        assertArrayEquals(expectedOutput2, BaseTranslator.convertBase(inputA, 2, 10, 3));
			        
        int[] inputB = {1,3};
        int[] expectedOutput3 = {0,0,1,0};
        assertArrayEquals(expectedOutput3, BaseTranslator.convertBase(inputB, 10, 2, 4));
        
        int[] inputC = {1,4,1,5,9,3};
        int[] expectedOutput4 = {2,4,3,15,7};
        assertArrayEquals(expectedOutput4,BaseTranslator.convertBase(inputC, 10, 16, 5));
        
        int[] inputD = {2,4,3,15,6};
        int[] expectedOutput5 = {1,4,1,5,9};
        assertArrayEquals(expectedOutput5, BaseTranslator.convertBase(inputD, 16, 10, 5));
        
        //convertBase() should do nothing if baseA=baseB
        int[] inputF = {2,4,3,15,5};
        assertArrayEquals(inputF, BaseTranslator.convertBase(inputF, 16, 16, 5));
        
        // Expect non-neg values in input[] or else output[] = null
        int[] input2 = {1,-1, 1};
        assertArrayEquals(null, BaseTranslator.convertBase(input2, 10, 16, 2));
        //int[] input3 = {1,-1, 10};
        
        //Expect values less that BaseA in input[] or else put output[] = null
        int[] input4 = {1,2,3};
        assertArrayEquals(null, BaseTranslator.convertBase(input4, 3, 2, 3));
        
        //Expect BaseA>=2 BaseB>=2 and precisionB>=1 or else output[] = null
        assertArrayEquals(null, BaseTranslator.convertBase(input1, -1, 2, 2));
        assertArrayEquals(null, BaseTranslator.convertBase(input1, 2, -1, 2));
        assertArrayEquals(null, BaseTranslator.convertBase(input1, 2, 2, -1));
        assertArrayEquals(null, BaseTranslator.convertBase(input1, -2, 2, -1));
        
    }
}
