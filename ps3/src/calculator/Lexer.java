package calculator;

import java.util.regex.*;
import calculator.Type;

/**
 * Calculator lexical analyzer.
 */
public class Lexer {	
	private String s = "";
	private int i = 0;
	private final Matcher matcher;
	
	/**
	 * Types expressed in regular grammar
	 */
	private static final Pattern number = Pattern.compile( "^([0-9]*[.]?[0-9]+)");
	private static final Pattern unit = Pattern.compile( "(in|pts)");
	private static final Pattern operator = Pattern.compile("(\\Q+\\E)");
	//private static final Pattern delimiter = Pattern.compile("[(]" + "|" + "[)]");
	//private static final Pattern REGEX_TOKEN = Pattern.compile( "^(" + number.toString() + ")" + "|" + 
	  // "(" + unit.toString() + ")" + "|" + "(" + operator.toString() + ")"	+ "(" + delimiter.toString() + ")" );
	private static final Pattern REGEX_TOKEN = Pattern.compile("^" +number.toString() +"|"+unit.toString()+"|"+operator.toString());
	/**
	 * Token in the stream.
	 */
	public static class Token {
		final Type type;
		final String text;

		Token(Type type, String text) {
			this.type = type;
			this.text = text;
		}

		Token(Type type) {
			this(type, null);
		}
		
		public String toString() {
			
			return "(" + type.name() + ", " + text + ")";
		}
	}
	
	private static Type[] TOKENS_TYPES = {
		Type.NUM,
		Type.SCAL,
		Type.OP
	};

	@SuppressWarnings("serial")
	static class TokenMismatchException extends Exception {
	}

	/**
	 * Constructs a lexer object based on an input string
	 * @param input String to be converted into tokens
	 */
	public Lexer(String s) {
		this.s = s;
		this.i = 0;
		this.matcher = REGEX_TOKEN.matcher(s);
	}
	
	/**
	 * Modifies this object by consuming a token from the input stream.
	 * @ returns next token in the stream, or EOF if the end has been reached
	 * @ throws Syntax Error if invalid input is found
	 */
	public Token next() throws SyntaxErrorException{
		//return EOF token if the end of the string has been reached
		if (i >= s.length())
			return new Token(Type.EOF, "");
		
    // Look for the next token
    if (!matcher.find(i)) {
        // No token found
    		System.out.println("hi");
        throw new SyntaxErrorException("syntax error at " + s.substring(i));
    }
		
		
    // Get the part of the string that the regex matched,
    // and advance our state
    String value = matcher.group(0);
    i = matcher.end();
    
    // Each set of parentheses in REGEX_TOKEN is a "capturing group", which
    // means that the regex matcher remembers where it matched and returns it
    // with the method group(i), where i=1 is the first set of parens.
    // Only one of the groups can match, so look for a non-null group.
    System.out.println("Current # capturing groups = " + matcher.groupCount());
    for (int i = 1; i <= matcher.groupCount(); i++) {
    		//System.out.println(i);
    		System.out.println(i);
    		System.out.println(matcher.group(i));
        if (matcher.group(i) != null) {
            // since i is 1-based, use i-1 to find the token type for this pattern
        		//System.out.println(i);
            return new Token(TOKENS_TYPES[i-1], value);
        }
    }
    throw new AssertionError("shouldn't get here");
	}
}
