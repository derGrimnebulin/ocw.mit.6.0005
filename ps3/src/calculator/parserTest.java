package calculator;

import static org.junit.Assert.*;

import org.junit.Test;
import calculator.Lexer.Token;
import calculator.Parser.Value;
import calculator.Parser.ValueType;
public class parserTest {

	@Test
	public void evaluateOperandsTest1() {
		//addition
		Token op1 = new Token(Type.NUM, "3");
		Token op2 = new Token(Type.NUM, "4.15");
		Token add = new Token(Type.OP, "+");
		Token expected1 = new Token(Type.NUM, "7.15");
		Token actual1 = Parser.evaluateOperands(op1,op2,add,3);
		assertEquals(expected1.toString(), actual1.toString());
		
		//subtracting
		Token sub = new Token(Type.OP, "-");
		Token expected2 = new Token(Type.NUM, "-1.15");
		Token actual2 = Parser.evaluateOperands(op1,op2,sub,3);
		assertEquals(expected2.toString(), actual2.toString());
		
		//multiplication
		Token mul = new Token(Type.OP, "*");
		Token expected3 = new Token(Type.NUM, "12.45");
		Token actual3 = Parser.evaluateOperands(op1,op2,mul,3);
		assertEquals(expected3.toString(), actual3.toString());
		
		//division
		Token div = new Token(Type.OP, "/");
		Token expected4 = new Token(Type.NUM,"0.723");
		Token actual4 = Parser.evaluateOperands(op1, op2, div,3);
		assertEquals(expected4.toString(), actual4.toString());
	}
	
	@Test
	public void evaluateOperandsTest2() {
		double epsilon = 0.0001;
		//addition
		double op1 = 3;
		double op2 = 4.15;
		Token add = new Token(Type.OP, "+");
		double expected1 = 7.15;
		double actual1 = Parser.evaluateOperands(op1,op2,add,3);
		assertEquals(expected1, actual1, epsilon);
		
		//subtracting
		Token sub = new Token(Type.OP, "-");
		double  expected2 = -1.15;
		double  actual2 = Parser.evaluateOperands(op1,op2,sub,3);
		assertEquals(expected2, actual2, epsilon);
		
		//multiplication
		Token mul = new Token(Type.OP, "*");
		double expected3 = 12.45;
		double actual3 = Parser.evaluateOperands(op1,op2,mul,3);
		assertEquals(expected3, actual3, epsilon);
		
		//division
		Token div = new Token(Type.OP, "/");
		double expected4 = 0.723;
		double actual4 = Parser.evaluateOperands(op1, op2, div,3);
		assertEquals(expected4, actual4, epsilon);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void divByZeroErrorTest() {
		Token zero = new Token(Type.NUM, "0");
		Token op1 = new Token(Type.NUM, "3");
		Token op = new Token(Type.OP, "/");
		Parser.evaluateOperands(op1, zero, op, 3);
	}
	
	@Test
	public void evaluateUnitsTest1() {
		
		Token inches = new Token(Type.UNIT, "in");
		Token points = new Token(Type.UNIT, "pts");
		Token scalar = new Token(Type.UNIT, "scalar");
		
		Token plus = new Token(Type.OP,"+");
		Token minus = new Token(Type.OP,"-");
		Token times = new Token(Type.OP,"*");
		Token divide = new Token(Type.OP,"/");
		
	// +,-,*
		String actual1 = Parser.evaluateUnits(inches, scalar, times).toString();
		assertEquals(inches.toString(), actual1);
		
		String actual2 = Parser.evaluateUnits(inches, inches, times).toString();
		assertEquals(inches.toString(), actual2);
		
		String actual3 = Parser.evaluateUnits(scalar, inches, plus).toString();
		assertEquals(inches.toString(), actual3);
		
		String actual4 = Parser.evaluateUnits(inches, points, plus).toString();
		assertEquals(inches.toString(), actual4);
		
	//division
		String actual5 = Parser.evaluateUnits(inches, scalar, divide).toString();
		assertEquals(inches.toString(), actual5);
		
		String actual6 = Parser.evaluateUnits(inches, inches, divide).toString();
		assertEquals(scalar.toString(), actual6);
		
		String actual7 = Parser.evaluateUnits(inches, points, divide).toString();
		assertEquals(scalar.toString(), actual7);
		
		String actual8 = Parser.evaluateUnits(scalar, inches, divide).toString();
		assertEquals(inches.toString(), actual8);
	}

	@Test
	public void evaluateUnitsTest2() {
		
		ValueType inches = ValueType.INCHES;
		ValueType points = ValueType.POINTS;
		ValueType scalar = ValueType.SCALAR;
		
		Token plus = new Token(Type.OP,"+");
		Token minus = new Token(Type.OP,"-");
		Token times = new Token(Type.OP,"*");
		Token divide = new Token(Type.OP,"/");
		
	// +,-,*
		String actual1 = Parser.evaluateUnits(inches, scalar, times).toString();
		assertEquals(inches.toString(), actual1.toString());
		
		String actual2 = Parser.evaluateUnits(inches, inches, times).toString();
		assertEquals(inches.toString(), actual2.toString());
		
		String actual3 = Parser.evaluateUnits(scalar, inches, plus).toString();
		assertEquals(inches.toString(), actual3.toString());
		
		String actual4 = Parser.evaluateUnits(inches, points, plus).toString();
		assertEquals(inches.toString(), actual4.toString());
		
	//division
		String actual5 = Parser.evaluateUnits(inches, scalar, divide).toString();
		assertEquals(inches.toString(), actual5.toString());
		
		String actual6 = Parser.evaluateUnits(inches, inches, divide).toString();
		assertEquals(scalar.toString(), actual6.toString());
		
		String actual7 = Parser.evaluateUnits(inches, points, divide).toString();
		assertEquals(scalar.toString(), actual7.toString());
		
		String actual8 = Parser.evaluateUnits(scalar, inches, divide).toString();
		assertEquals(inches.toString(), actual8.toString());
	}
	
	@Test
	public void tokenToValueTest() {
		double phi = 3.141562;
		Token number = new Token(Type.NUM, "3.141562");
		Token inches = new Token(Type.UNIT, "in");
		Token points = new Token(Type.UNIT, "pts");
		
		Value expectedFromNull = new Value(phi, ValueType.SCALAR);
		Value expectedFromInches = new Value(phi*72, ValueType.INCHES);
		Value expectedFromPoints = new Value(phi, ValueType.POINTS);
		
		Value actuallyFromNull = Parser.tokenToValue(number, null);
		Value actuallyFromInches = Parser.tokenToValue(number, inches);
		Value actuallyFromPoints = Parser.tokenToValue(number, points);
		
		assertEquals(expectedFromNull.toString(), actuallyFromNull.toString());
		assertEquals(expectedFromInches.toString(), actuallyFromInches.toString());
		assertEquals(expectedFromPoints.toString(), actuallyFromPoints.toString());
	}
	
	@Test
	public void evaluateInfixTest() {
		Value v1 = new Value(226.224,ValueType.INCHES);
		Value v2 = new Value(3, ValueType.SCALAR);
		Value v3 = new Value(4, ValueType.POINTS);
		
		Token plus = new Token(Type.OP,"+");
		Token minus = new Token(Type.OP,"-");
		Token times = new Token(Type.OP,"*");
		Token divide = new Token(Type.OP,"/");
		
		//v1+v2 = 6.141562in
		Value expected1 = new Value(229.224,ValueType.INCHES);
		Value actual1 = Parser.evaluateInfix(v1,v2,plus);
		assertEquals(expected1.toString(), actual1.toString());
		//v1-v1 = 0in
		Value expected2 = new Value(0, ValueType.INCHES);
		Value actual2 = Parser.evaluateInfix(v1,v1,minus);
		assertEquals(expected2.toString(), actual2.toString());
		
		//v3*v1 = 3.198pts
		Value expected3 = new Value(904.896, ValueType.POINTS);
		Value actual3 = Parser.evaluateInfix(v3, v1, times);
		assertEquals(expected3.toString(), actual3.toString());
		
		//v3/v3 = 1
		Value expected4 = new Value(1, ValueType.SCALAR);
		Value actual4 = Parser.evaluateInfix(v3,v3,divide);
		assertEquals(expected4.toString(), actual4.toString());
		
		//v1/v3 = 56.556
		Value expected5 = new Value(56.556, ValueType.SCALAR);
		Value actual5 = Parser.evaluateInfix(v1, v3, divide);
		assertEquals(expected5.toString(), actual5.toString());
		
		//v1*v3
		Value expected6 = new Value(904.896, ValueType.INCHES);
		Value actual6 = Parser.evaluateInfix(v1, v3, times);
		System.out.println(actual6.toString());
		assertEquals(expected6.toString(), actual6.toString());
		
		
	}
	@Test
	public void evaluateTest() {
		String exp1 = "(1/9pts)*9pts";
		String exp2 = "(1+3in)*(3.14pts-1)/2.14pts";
		String exp3 = "((1-3)*(3+2))/2.25pts";
		
		Lexer lex1 = new Lexer(exp1);
		Lexer lex2 = new Lexer(exp2);
		Lexer lex3 = new Lexer(exp3);
		
		Parser parse1 = new Parser(lex1);
		Parser parse2 = new Parser(lex2);
		Parser parse3 = new Parser(lex3);
		
		Value actuallyFromExp1 = parse1.evaluate();
		Value actuallyFromExp2 = parse2.evaluate();
		Value actuallyFromExp3 = parse3.evaluate();
		
		Value expectedFromExp1 = new Value(1, ValueType.SCALAR);
		Value expectedFromExp2 = new Value(4, ValueType.INCHES);
		Value expectedFromExp3 = new Value(-4, ValueType.POINTS);
		
		assertEquals(expectedFromExp1.toString(),actuallyFromExp1.toString());
		assertEquals(expectedFromExp2.toString(),actuallyFromExp2.toString());
		assertEquals(expectedFromExp3.toString(),actuallyFromExp3.toString());
	}
}

