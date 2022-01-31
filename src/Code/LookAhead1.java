package Code;
import Code.*;
import java.io.*;

public class LookAhead1  {
    /* Simulating a reader class for a stream of Token */
    
    private Token current;
    private Lexer lexer;

    public LookAhead1(Lexer l) throws Exception, IOException, LexerException {
		lexer=l;
		current=lexer.yylex();
    }

    public boolean check(Sym s) {
	/* check whether the first character is of type s*/
        return (current.symbol() == s); 
    }

    public void eat(Sym s)	throws Exception {
		/* consumes a token of type s from the stream,
		   exception when the contents does not start on s.   */
    	print();
		if (!check(s)) {
		    throw new Exception("Erreur");
		}
	    current=lexer.yylex();
    }

    public int getIntValue () throws Exception {
	/* returns the value of an integer token */
		if(current.symbol()==Sym.NOMBRE)
		    return ((IntToken)current).value();
		else 
		    throw new Exception("LookAhead1 Erreur");
    }

    public String getStringValue () throws Exception {
	/* returns the string value of a token */
		if (current.symbol()==Sym.NOMBRE)
		    return ((StringToken)current).value();
		else 
		    throw new Exception("LookAhead1 Erreur");
    }
    public void print() {
    	System.out.print(current.symbol.name());
    	System.out.print("\t"+lexer.yytext() + "\n");
    }
    public boolean char0Moins() {
    	System.out.println(lexer.yycharat(0));
    	if(lexer.yycharat(0)=='-') {
    		return true;
    	}
    	return false;
    }
    @Override
    public String toString() {
    	return lexer.yytext()+" "+"("+this.current.symbol.name()+")";
    }

}