package calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
	public static class Value {
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
	 * Wrapper for the Value constructor method. Takes contiguous tokens from the token stream representing a 
	 * number and its associated unit type, and constructs a single Value object that carries both pieces of 
	 * information. If unit is null, the ValueType of the return Value object is SCALAR.
	 * @param number
	 * @param unit
	 * @return Value 
	 */
	public static Value tokenToValue(Token number, Token unit) {
		double x = Double.parseDouble(number.text);
		if (unit == null)
			return new Value(x, ValueType.SCALAR);
		switch(unit.text) {
		case "pts":
			return new Value(x,ValueType.POINTS);
		case "in":
			return new Value(x*PT_PER_IN, ValueType.INCHES);
		}
		throw new AssertionError("shouldn't be here");
	}
	
	
	
	
	/**
	 * performs basic arithmetic operations on Value objects
	 * @param left
	 * @param right
	 * @param op
	 * @return
	 */
	public static Value evaluateInfix(Value left, Value right, Token op){
		int precision = 10;
		double newValue = evaluateOperands(left.getValue(), right.getValue(), op, precision);
		ValueType newType = evaluateUnits(left.getType(), right.getType(), op);
		return new Value(newValue, newType);
	}
	
	
	
	
	/**
	 * Performs unit analysis of a given infix operation. The first unit (non-scalar token) encountered is returned,
	 *  except with division. In that case, scalar is returned if both units are the same and points is 
	 *  returned when dividing inches and points.
	 * 
	 * @param u1 left-hand unit
	 * @param u2 right-hand unit
	 * @param op operator
	 * @return Token containing resultant unit.
	 */
	public static ValueType evaluateUnits(ValueType u1, ValueType u2, Token op){
		String unit = "SCALAR"; //default ValueType
		final String left = u1.toString();
		final String right = u2.toString();
		switch(op.text) {
		case "/":
			//In the case of division, we need only to be concerned in situations where
			//the final unit is not scalar
			if (!u1.toString().equals(right)){
				if (left.equals("SCALAR")){
					unit = right;
					break;
				}
				if (right.equals("SCALAR")) {
					unit = left;
					break;
				}
			}
			break;
		default:
			if(left.equals("SCALAR")){
				unit = right;
				break;
			}
			if(right.equals("SCALAR")){
				unit = left;
				break;
			}
			unit = left;
			break;
		}
		return ValueType.valueOf(unit);
	}

	
	
	
	/**
	 * Performs unit analysis of a given infix operation. The first unit (non-scalar token) encountered is returned,
	 *  except with division. In that case, scalar is returned if both units are the same and points is 
	 *  returned when dividing inches and points.
	 * 
	 * @param u1 left-hand unit
	 * @param u2 right-hand unit
	 * @param op operator
	 * @return Token containing resultant unit.
	 */
	public static Token evaluateUnits(Token u1, Token u2, Token op){
		String unit = "scalar";
		switch(op.text) {
		case "/":
			//In the case of division, we need only to be concerned in situations where
			//the final unit is not scalar
			if (!u1.text.equals(u2.text)){
				if (u1.text.equals("scalar")){
					unit = u2.text;
					break;
				}
				if (u2.text.equals("scalar")) {
					unit = u1.text;
					break;
				}
			}
			break;
		default:
			if(u1.text.equals("scalar")){
				unit = u2.text;
				break;
			}
			if(u2.text.equals("scalar")){
				unit = u1.text;
				break;
			}
			unit = u1.text;
			break;
		}
		return new Token(Type.UNIT, unit);
	}
	
	
	
	
	/**
	 * Evaluates newly encountered infix expression. Internal arithmetic handled in points.
	 * @param op1 operand 1
	 * @param op2 operand 2
	 * @param op operator
	 * @return result
	 */
	public static double evaluateOperands(double op1, double op2, Token op, int precision) {
		//BigDecimal for greater arithmetic accuracy.
		final MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal value;
		switch(op.text){
			case "+":
				value = new BigDecimal(op1).add(new BigDecimal(op2));
				break;
			case "-":
				value =(new BigDecimal(op1).subtract(new BigDecimal(op2)));
				break;
			case "*":
				value = new BigDecimal(op1).multiply(new BigDecimal(op2));
				break;
			case "/":
				if (op2 == 0)
					throw new IllegalArgumentException("Cannot divide by zero.");
				value = new BigDecimal(op1).divide(new BigDecimal(op2), mc);
				break;
			default:
				throw new AssertionError("shouldn't get here");
		}
		return value.doubleValue();
	}
	
	
	
	
	/**
	 * Evaluates newly encountered infix expression. Internal arithmetic handled in points.
	 * @param op1 operand 1
	 * @param op2 operand 2
	 * @param op operator
	 * @return result
	 */
	public static Token evaluateOperands(Token op1, Token op2, Token op, int precision) {
		//BigDecimal for greater arithmetic accuracy.
		final MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal value;
		switch(op.text){
			case "+":
				value = new BigDecimal(op1.text).add(new BigDecimal(op2.text));
				break;
			case "-":
				value =(new BigDecimal(op1.text).subtract(new BigDecimal(op2.text)));
				break;
			case "*":
				value = new BigDecimal(op1.text).multiply(new BigDecimal(op2.text));
				break;
			case "/":
				if (op2.text == "0")
					throw new IllegalArgumentException("Cannot divide by zero.");
				value = new BigDecimal(op1.text).divide(new BigDecimal(op2.text), mc);
				break;
			default:
				throw new AssertionError("shouldn't get here");
		}
		return new Token(Type.NUM, value.toString() );
		
		
	}
	
}
