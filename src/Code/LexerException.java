package Code;
public class LexerException extends Exception {
	public String msg;
	public LexerException(int row, int col, String word) {
		super("Caractere non reconnu a la ligne " + row + " et colonne " + col+ " dont le premier caractere est : "+word+"\n");
		this.msg="Caractere non reconnu a la ligne " + row + " et colonne " + col+ " dont le premier caractere est : "+word+"\n";
	}
	@Override
	public String getMessage() {
		return this.msg;
	}
}
