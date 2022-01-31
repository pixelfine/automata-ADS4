package Code;
class Token {
    protected Sym symbol;
    public Token(Sym s) {
	symbol=s;
    }
    public Sym symbol() {
	return symbol;
    }
    public String toString(){
	return "Symbol : "+this.symbol;
    }
}

class IntToken extends Token {
    private int value;
    public IntToken(Sym c, int n) {
		super(c);
		value=n;
    }
    public int value() {
    	return value;
    }
    public String toString(){
    	return "Symbol : "+this.symbol+" | Value : "+this.value();
    }
}

class StringToken extends Token {
    private String value;
    public StringToken(Sym c, String v) {
		super(c);
		value=v;
    }
    public String value() {
    	return value;
    }
    public String toString(){
    	return "Symbol : "+this.symbol+" | Value : "+this.value();
    }
}