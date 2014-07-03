package calculator;

import static org.junit.Assert.*;

import org.junit.Test;
import calculator.Lexer.Token;
public class parserTest {

	@Test
	public void evaluateOperandsTest1() {
		//addition
		Token op1 = new Token(Type.NUM, "3");
		Token op2 = new Token(Type.NUM, "4.15");
		Token add = new Token(Type.OP, "+");
		Token expected1 = new Token(Type.NUM, "7.15");
		Token actual1 = Parser.evaluateOperands(op1,op2,add);
		assertEquals(expected1, actual1);
		
		//subtracting
		Token sub = new Token(Type.OP, "-");
		Token expected2 = new Token(Type.NUM, "1.15");
		Token actual2 = Parser.evaluateOperands(op2,op1,sub);
		assertEquals(expected2, actual2);
		
		//multiplication
		Token mul = new Token(Type.OP, "*");
		Token expected3 = new Token(Type.NUM, "12.45");
		Token actual3 = Parser.evaluateOperands(op2,op1,mul);
		assertEquals(expected3, actual3);
		
		//division
		Token div = new Token(Type.OP, "/");
		Token expected4 = new Token(Type.NUM,"0.72289156626");
		Token actual4 = Parser.evaluateOperands(op1, op2, div);
		assertEquals(expected3, actual3);
		
		//TODO test expected div by zero error
		
	}
	
	public void evaluateUnitsTest() {
		
	}
	
	@Test
	public void evaluateTestOrderOfOperations(){
		double epislon = 0.001;
		Lexer lex0 = new Lexer("(3+4)*(4-1)/(3.14+8)");
	}


}
