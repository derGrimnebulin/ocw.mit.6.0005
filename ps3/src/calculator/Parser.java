package calculator;

import calculator.Lexer;
import calculator.Lexer.Token;

/*Rules of production
 * EXP ::= NUM[UNIT]? OP NUM[UNIT]?
 * NUM ::= digits(.digits)?
 * OP ::= '+' | '-' | '*' | '\'
 * UNIT ::= pts | in
 * digits ::= [0-9]+
 */

/**
 * Calculator parser. All values are measured in pt.
 */
class Parser {
	
	
	@SuppressWarnings("serial")
	static class ParserException extends RuntimeException {
	}

	/**
	 * Type of values. Ordered according to precedence
	 */
	public enum ValueType {
		POINTS, INCHES, SCALAR
	};

	/**
	 * Internal value is always in points.
	 */
	public class Value {
		final double value;
		final ValueType type;

		Value(double value, ValueType type) {
			this.value = value;
			this.type = type;
		}
		
		public double getValue(){
			return value;
		}
		public ValueType getType(){
			return type;
		}

		@Override
		public String toString() {
			switch (type) {
			case INCHES:
				return value / PT_PER_IN + " in";
			case POINTS:
				return value + " pt";
			default:
				return "" + value;
			}
		}
	}

	private static final double PT_PER_IN = 72;
	private final Lexer lexer;
	private ValueType type = ValueType.SCALAR;

	/**
	 * Constructs a Parser object from a given lexer object. Contains methods to 
	 * interpret lexical tokens.
	 * @param lexer
	 */
	Parser(Lexer lexer) {
		this.lexer = lexer;
		// TODO implement for Problem 3
	}

	/**
	 * Evaluates the input expression. Only called once on a given parser object
	 * @Modifies lexer by consuming all tokens
	 * @return Value of the given expression
	 */
	public Value evaluate() {
		return new Value(0, ValueType.SCALAR);
		// TODO implement for Problem 3
	}
	
	/**
	 * Performs unit analysis of a given infix operation.
	 * @param u1 left-hand unit
	 * @param u2 right-hand unit
	 * @param op operator
	 * @return Token containing resultant unit.
	 */
	public static Token evaluateUnits(Token u1, Token u2, Token op) throws DivideByZeroException{
		return new Token(null,null);
	}
	/**
	 * Evaluates newly encountered infix expression.
	 * @param op1 operand 1
	 * @param op2 operand 2
	 * @param op operator
	 * @return result
	 */
	public static Token evaluateOperands(Token op1, Token op2, Token op) {
		return new Token(null,null);
	}
	
	public String getValueTypeState() {
		return type.toString();
	}
}
