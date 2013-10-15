package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class PiGeneratorTest {
    @Test
    public void basicPowerModTest() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
        //m>0 or modulo is undefined.
        assertEquals(-1,PiGenerator.powerMod(0, 0, 0));
        //m>0 lets us cover the remaining partitions with the following test cases
        assertEquals(-1,PiGenerator.powerMod(-1, 0, 1));
        assertEquals(-1,PiGenerator.powerMod(-1, -1, 1));
        assertEquals(-1, PiGenerator.powerMod(0, -1, 1));

    }

    // TODO: Write more tests (Problem 1.a, 1.c)
}
