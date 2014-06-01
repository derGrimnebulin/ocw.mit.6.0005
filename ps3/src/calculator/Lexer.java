package calculator;

import calculator.Type;

/**
 * Calculator lexical analyzer.
 */
public class Lexer {

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

	// TODO write method spec
	/**
	 * Lexer is a method that finds and returns the next token in the input string
	 * defined by the type enumeration. Lexer consumes one token of input at a time
	 * and returns a token of that type. It removes this token from the input string.
	 * @param input
	 * @return Token
	 */
	public Token Lexer(String input) {
		// TODO implement for Problem 2
		return new Lexer.Token(null, null);
	}
}
