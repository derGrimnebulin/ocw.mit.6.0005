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
		assertEquals( new Lexer.Token(Type.SCAL, "in").toString(), lex1.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex1.next().toString() );
		
		String s2 = "4.5354pts";
		Lexer lex2 = new Lexer(s2);
		assertEquals( new Lexer.Token(Type.NUM, "4.5354").toString(),lex2.next().toString() );
		assertEquals( new Lexer.Token(Type.SCAL, "pts").toString(),lex2.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex2.next().toString() );
		
		String s3 = "5 + 3";
		Lexer lex3 = new Lexer(s3);
		assertEquals( new Lexer.Token(Type.NUM, "5").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.OP, "+").toString(), lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.NUM, "3").toString(), lex3.next().toString() );
		//assertEquals( new Lexer.Token(Type.SCAL, "pts").toString(),lex3.next().toString() );
		assertEquals( new Lexer.Token(Type.EOF, "" ).toString(), lex3.next().toString() );
	}

}
