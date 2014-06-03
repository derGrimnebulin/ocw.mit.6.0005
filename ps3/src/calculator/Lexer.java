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
	private static final Pattern digits = Pattern.compile( "[0-9]+" );
	private static final Pattern number = Pattern.compile( digits.toString() + "(." + digits.toString() + ")?");
	private static final Pattern unit = Pattern.compile( "pts" + "|" + "in");
	private static final Pattern operator = Pattern.compile( "+" + "|" + "-" + "|" + "*" + "|"+ "/" );
	private static final Pattern REGEX_TOKEN = Pattern.compile( number.toString() + "|" + unit.toString() + "|" + operator.toString() );
	
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
	}

	@SuppressWarnings("serial")
	static class TokenMismatchException extends Exception {
	}

	/**
	 *
	 * @param input String to be converted into tokens
	 */
	public Lexer(String s) {
		this.s = s;
		this.matcher = REGEX_TOKEN.matcher(s);
	}
	
	/**
	 * Modifies this object by consuming a token from the input stream.
	 * @ returns next token in the stream, or EOF if the end has been reached
	 * @ throws Syntax Error if invalid input is found
	 */
	public Token next() throws SyntaxErrorException {
		//return EOF token if the end of the string has been reached
		if (i >= s.length())
			return new Token(Type.EOF, "");
		
		return new Token(null, null);
	}
}
