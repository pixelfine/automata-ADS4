package Code;

public class InterpreterException extends Exception{
	public String msg;
	public InterpreterException() {
		super("Exception de l'Interpreter.");
		this.msg="Exception de l'Interpreter";
	}
	public InterpreterException(String s) {
		super("Exception de l'Interpreter.\n\t"+s);
		this.msg="Exception de l'Interpreter : \n\t"
				+ s;
	}
	@Override
	public String getMessage() {
		return this.msg;
	}
}
