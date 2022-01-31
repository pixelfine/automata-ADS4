package Code.AbstractSyntaxTree;
import Code.Grid;
import Code.InterpreterException;
import Code.Sym;

abstract class Condition {
	abstract boolean value(Grid grid) throws Exception;
}

class ConditionComparaison extends Condition{
	private Expression e1;
	private Comparaison c;
	private Expression e2;
	
	public ConditionComparaison(Expression e1, Comparaison c, Expression e2) {
		this.e1 = e1;
		this.c = c;
		this.e2 = e2;
	}

	@Override
	boolean value(Grid grid) throws Exception {
		switch(c.value) {
			case INF :
				return (int)e1.eval(grid) < (int)e2.eval(grid);
			case SUP : System.out.println(e1.eval(grid) +">"+ e2.eval(grid));
				return (int)e1.eval(grid) > (int)e2.eval(grid);
			case EGALE :
				return e1.eval(grid) == e2.eval(grid);
			default: throw new InterpreterException("Valeur de condition inattendu\n");
		}
	}
}
class ConditionConnecteur extends Condition{
	private Condition c1;
	private Connecteur c;
	private Condition c2;
	
	public ConditionConnecteur(Condition c1, Connecteur c, Condition c2) {
		this.c1 = c1;
		this.c = c;
		this.c2 = c2;
	}

	@Override
	boolean value(Grid grid) throws Exception{
		switch (c.value) {
		case ET : return c1.value(grid) && c2.value(grid);
		case Ou : return c1.value(grid) || c2.value(grid);
		default : throw new InterpreterException("Valeur de condition inattendu\n");
		}
	}
}

abstract class Comparaison {
	Sym value;
	Sym getValue() {return this.value;}
}

class Superieur extends Comparaison{
	public Superieur() {super.value = Sym.SUP;}
}

class Inferieur extends Comparaison{
	public Inferieur() {super.value = Sym.INF;}
}

class Egale extends Comparaison{
	public Egale() {super.value = Sym.EGALE;}
}

abstract class Connecteur {
	Sym value;
	Sym getValue() {return this.value;}
}
class Et extends Connecteur{
	public Et() {super.value = Sym.ET;}
}
class Ou extends Connecteur{
	public Ou() {super.value = Sym.Ou;}
}