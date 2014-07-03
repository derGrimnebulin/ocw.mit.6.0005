package calculator;

import static org.junit.Assert.*;

import org.junit.Test;


public class lexerTest {

	@Test
	public void testNext() throws SyntaxErrorException {
		String s0 = "3.14234";
		Lexer lex0 = new Lexer(s0);
		assertEquals( new Lexer.Token(Type.NUM, "3.14234").toString(), lex0.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex0.next().toString() );
		
		String s1 = "in";
		Lexer lex1 = new Lexer(s1);
		assertEquals( new Lexer.Token(Type.UNIT, "in").toString(), lex1.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex1.next().toString() );
		
		String s2 = "4.5354 pts 3";
		Lexer lex2 = new Lexer(s2);
		assertEquals( new Lexer.Token(Type.NUM, "4.5354").toString(),lex2.next().toString() );
		assertEquals( new Lexer.Token(Type.UNIT, "pts").toString(),lex2.next().toString() );
		assertEquals( new Lexer.Token(Type.NUM, "3").toString(),lex2.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex2.next().toString() );
		
		String s3 = "5 / 3pts";
		Lexer lex3 = new Lexer(s3);
		assertEquals( new Lexer.Token(Type.NUM, "5").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.OP, "/").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.NUM, "3").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.UNIT, "pts").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex3.next().toString() );
		
		String s4 = "((5+3pts)/4in)";
		Lexer lex4 = new Lexer(s4);
		assertEquals( new Lexer.Token(Type.PAREN, "(").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.PAREN, "(").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.NUM, "5").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.OP, "+").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.NUM, "3").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.UNIT, "pts").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.PAREN, ")").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.OP, "/").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.NUM, "4").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.UNIT, "in").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.PAREN, ")").toString(), lex4.next().toString());
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex4.next().toString() );
		
		//should throw exception
	}
	@Test(expected = calculator.SyntaxErrorException.class)
	public void testThrow(){
		Lexer lex = new Lexer("m");
		try {
			lex.next();
		} catch (SyntaxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
