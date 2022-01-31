package Code;

public class ParserException extends Exception{
	public String msg;
	public ParserException(String s) {
		super("Exception du parser, le caractere "+s+" est inattendu");
		this.msg="Exception du parser, le caractere "+s+" est inattendu";
	}
	public ParserException(String s, String s2) {
		super("Exception du parser, le caractere "+s+" est inattendu :"+s2);
		this.msg="Exception du parser : \n\t"
				+ "Le caractere "+s+" est inattendu .\n\t"
				+s2;
	}
	@Override
	public String getMessage() {
		return this.msg;
	}
}