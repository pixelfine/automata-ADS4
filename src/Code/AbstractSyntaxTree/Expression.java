package Code.AbstractSyntaxTree;

import Code.Grid;
import Code.InterpreterException;
import Code.Sym;

public abstract class Expression {
	abstract int eval(Grid grid) throws Exception;
}

class Lire extends Expression{
	@Override
	public int eval(Grid grid) {
		return grid.lire();
	}
}

class Nombre extends Expression{
	private int value;
	public Nombre(int value) {
		this.value=value;
	}
	@Override
	public int eval(Grid grid) {
		return value;
	}
}

class Expe3 extends Expression{
	private Expression e1;
	private Operation op;
	private Expression e2;
	
	public Expe3(Expression e1, Operation o, Expression e2) {
		this.e1 = e1;
		this.op = o;
		this.e2 = e2;
	}
	
	@Override
	public int eval(Grid grid) throws Exception{
		switch(op.getValue()) {
		case PLUS : return (int)e1.eval(grid) + (int)e2.eval(grid);
		case MOINS : return (int)e1.eval(grid) - (int)e2.eval(grid);
		default: throw new InterpreterException("Valeur d'Expression inattendu\n");
		}
	}
}

abstract class Operation {
	Sym value;
	Sym getValue() {return this.value;}
}

class Plus extends Operation {
	public Plus() {super.value = Sym.PLUS;}
}

class Moins extends Operation {
	public Moins() {super.value = Sym.MOINS;}
}
