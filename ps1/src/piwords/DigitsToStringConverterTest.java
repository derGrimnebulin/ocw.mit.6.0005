  package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class DigitsToStringConverterTest {
    @Test
    public void basicNumberSerializerTest() {
        // Input is a 4 digit number, 0.123 represented in base 4
        int[] input = {0, 1, 2, 3};

        // Want to map 0 -> "d", 1 -> "c", 2 -> "b", 3 -> "a"
        char[] alphabet = {'d', 'c', 'b', 'a'};

        String expectedOutput = "dcba";
        assertEquals(expectedOutput, DigitsToStringConverter.convertDigitsToString(input, 4, alphabet));
        
        //remaining test cases for base-5 where 0 <= x_i[j] < 5
        int[] x_1 = {0,0,0}; char[] y_1 = {'d','d','d'};
        int[] x_2 = {0,0,1}; char[]	y_2	=	{'d','d','b'};
        int[] x_3 = {0,1,2}; char[] y_3 = {'d','b','p'};
        int[] x_4 = {1,2,3}; char[] y_4 = {'b','p','q'};
        int[] x_5 = {2,3,4}; char[] y_5 = {'p','q','e'};
        char[] xlphabet = {'b','d','p','q','e'};
        assertEquals(y_1 ,DigitsToStringConverter.convertDigitsToString(x_1, 5, xlphabet));
        assertEquals(y_2 ,DigitsToStringConverter.convertDigitsToString(x_2, 5, xlphabet));
        assertEquals(y_3 ,DigitsToStringConverter.convertDigitsToString(x_3, 5, xlphabet));
        assertEquals(y_4 ,DigitsToStringConverter.convertDigitsToString(x_4, 5, xlphabet));
        assertEquals(y_5 ,DigitsToStringConverter.convertDigitsToString(x_5, 5, xlphabet));
        
    }
    
    @Test
  	//expected output is null if digits[i] >= base or digits[i] < 0
    public void paramTest1() {

    	//input 'represents' a base-3 number
    	int[] input = {-1,0,1,2};
    	//0 -> 'b', 1 -> 'c', 2 -> 'a'
    	char[] alphabet = {'b', 'c', 'a'};
    	assertEquals("", DigitsToStringConverter.convertDigitsToString(input, 3, alphabet));
    }
    
    @Test
    //expected output is null if digits[i] >= base
    public void paramTest2() {
    	
    	//input 'represents' as base-2 number
    	int[] input = {1,0,1,2};
    	//0 -> 'c', 1 -> 'a'
    	char[] alphabet = {'c','a'};
    	assertEquals("", DigitsToStringConverter.convertDigitsToString(input, 2, alphabet));
    }
}
