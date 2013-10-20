package piwords;

public class BaseTranslator {
    /**
     * Converts an array where the ith digit corresponds to (1 / baseA)^(i + 1)
     * digits[i], return an array output of size precisionB where the ith digit
     * corresponds to (1 / baseB)^(i + 1) * output[i].
     * 
     * Stated in another way, digits is the fractional part of a number
     * expressed in baseA with the most significant digit first. The output is
     * the same number expressed in baseB with the most significant digit first.
     *
     * @param digits The input array to translate. This array is not mutated.
     * @param baseA The base that the input array is expressed in.
     * @param baseB The base to translate into.
     * @param precisionB The number of digits of precision the output should
     *                   have.
     * @return An array of size precisionB expressing digits in baseB.
     */
    public static int[] convertBase(final int[] digits, final int baseA, final int baseB, final int precisionB) {
    	
				for (int item : digits) {
					if (item < 0 || item >= baseA) 
						return null;
				}
				if ( baseA < 2 || baseB < 2 || precisionB < 1 )
					return null;
    	
    		final int[] newDigits = new int[precisionB];
  			final int[] digitsCopy = new int[digits.length];
  			
  			//Cannot mutate digits, so a copy of it is needed.
  			System.arraycopy(digits, 0, digitsCopy, 0, digits.length); 
  			for (int i = 0; i < precisionB; i++){
  				int carry = 0;
  				for (int j = digitsCopy.length - 1; j >= 0; j--) {
  					int x = digitsCopy[j]*baseB + carry;
  					digitsCopy[j] = x%baseA;
  					carry = x / baseA;
  				}
  				
  				newDigits[i] = carry;
  				
  			}
        return newDigits;
     }
}
