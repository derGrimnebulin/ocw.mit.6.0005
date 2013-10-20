package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class PiGeneratorTest {
    @Test
    public void basicPowerModTest() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
        assertEquals(-1, PiGenerator.powerMod(-1, 0, 1));
        assertEquals(-1, PiGenerator.powerMod(-1, -1, 1));
        assertEquals(-1, PiGenerator.powerMod(0, -1, 1));
        assertEquals(-1, PiGenerator.powerMod(0, 0, -1));
    }
    
    @Test
    public void computePiInHexTest() {
    		//test case arrays
    		int[] a = new int[0x0]; //precision <= 0x0
    		int[] b = new int[] {0x2,0x4}; //precision 0x2
    		int[] c = new int[] {0x2,0x4,0x3,0xf,0x6,0xa,0x8,0x8,0x8,0x5,0xa,0x3}; //precision 0xc
    		assertArrayEquals(a, PiGenerator.computePiInHex(0x0));
    		assertArrayEquals(b, PiGenerator.computePiInHex(0x2));
    		assertArrayEquals(c, PiGenerator.computePiInHex(0xc));
    
    }
    
    @Test
    public void piDigitTest() {
    	assertEquals(-1, PiGenerator.piDigit(-1));
    	
    }
     		
  }
