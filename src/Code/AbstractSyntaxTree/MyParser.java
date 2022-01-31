package Code.AbstractSyntaxTree;
import java.io.Reader;
import Code.*;

public class MyParser implements Parser {
	protected LookAhead1 look;
	
	/*public MyParser(LookAhead1 look) {
		this.look = look;
	}*/

	@Override
	public Program parseProgram(String exeName, Reader reader) {
		Lexer lexer = new Lexer(reader);
		Program programme=null;
		try {
			this.look = new LookAhead1(lexer);
			if(!look.check(Sym.EOF)) {
			programme = progNonTerm();
			look.eat(Sym.EOF);
			}else {look.eat(Sym.EOF);}
		}
		catch (ParserException e) { System.err.print(e.getMessage());System.exit(2);}
		catch (LexerException e) {System.err.print(e.getMessage());System.exit(2);}
		catch (Exception e) {e.printStackTrace(); System.exit(2);}
		return programme;
	}
	
	public Program progNonTerm() throws Exception {
		Instruction inst = null;
		Program prog = null;
		
		if((look.check(Sym.EOF)) || (look.check(Sym.FIN))) {
			if(look.check(Sym.EOF)) look.eat(Sym.EOF);
			return new Program(inst, prog);
		}else {
			inst = instNonTerm();
			if(look.check(Sym.PVIRG)) {
				look.eat(Sym.PVIRG);
					prog = progNonTerm();
					return new Program(inst, prog);
				
			} else throw new ParserException(look.toString(), "Le caractère ';' est attendu dans Programme\n"); 
		}
		//return new Program(inst, prog);
	}
	
	public Instruction instNonTerm() throws Exception {
		Expression exp = null;
		Condition cond = null;
		Program prog = null;
		
		//Avancer(exp)
		if(look.check(Sym.AVANCER)) {
			look.eat(Sym.AVANCER);
			if(look.check(Sym.LPAR)) {
				look.eat(Sym.LPAR);
				exp = expNonTerm();
				if(look.check(Sym.RPAR)) {
					look.eat(Sym.RPAR);
					return new Avancer(exp);
				} else throw new ParserException(look.toString(), "Le caractère ')' est attendu dans l'Instruction Avancer\n"); 
			} else throw new ParserException(look.toString(), "Le caractère '(' est attendu dans l'Instruction Avancer\n"); 
		}
		//Tourner(exp)
		else if(look.check(Sym.TOURNER)) {
			look.eat(Sym.TOURNER);
			if(look.check(Sym.LPAR)) {
				look.eat(Sym.LPAR);
				exp = expNonTerm();
				if(look.check(Sym.RPAR)) {
					look.eat(Sym.RPAR);
					return new Tourner(exp);
				}else throw new ParserException(look.toString(), "Le caractère ')' est attendu dans l'Instruction Tourner\n"); 
			}else throw new ParserException(look.toString(), "Le caractère '(' est attendu dans l'Instruction Tourner\n"); 
		}
		//Ecrire(exp)
		else if(look.check(Sym.ECRIRE)) {
			look.eat(Sym.ECRIRE);
			if(look.check(Sym.LPAR)) {
				look.eat(Sym.LPAR);
				exp = expNonTerm();
				if(look.check(Sym.RPAR)) {
					look.eat(Sym.RPAR);
					return new Ecrire(exp);
				}else throw new ParserException(look.toString(), "Le caractère ')' est attendu dans l'Instruction Ecrire\n"); 
			}else throw new ParserException(look.toString(), "Le caractère '(' est attendu dans l'Instruction Ecrire\n");
		}
		//si Condition alors Program fin
		else if(look.check(Sym.SI)) {
			look.eat(Sym.SI);
			cond = condNonTerm();
			if(look.check(Sym.ALORS)) {
				look.eat(Sym.ALORS);
				prog = progNonTerm();
				if(look.check(Sym.FIN)) {
					look.eat(Sym.FIN);
					return new Si(cond, prog);
				}else throw new ParserException(look.toString(), "Le caractère 'FIN' est attendu dans l'Instruction Si-Alors\n");
			}else throw new ParserException(look.toString(), "Le caractère 'Alors' est attendu dans l'Instruction Si-Alors\n");
		}
		//tantque Condition faire Program fin
		else if(look.check(Sym.TANTQUE)) {
			look.eat(Sym.TANTQUE);
			cond = condNonTerm();
			if(look.check(Sym.FAIRE)) {
				look.eat(Sym.FAIRE);
				prog = progNonTerm();
				if(look.check(Sym.FIN)) {
					look.eat(Sym.FIN);
					return new TantQue(cond, prog);
				} else throw new ParserException(look.toString(), "Le caractère 'FIN' est attendu dans l'Instruction TantQue\n");
			} else throw new ParserException(look.toString(), "Le caractère 'Faire' est attendu dans l'Instruction TantQue\n");
		}
		//Ne peut pas etre null donc
		throw new ParserException(look.toString(), "Le Programme est non reconnu\n");
	}
	
	public Expression expNonTerm() throws Exception {
		if(look.check(Sym.LIRE)) {
			look.eat(Sym.LIRE);
			return new Lire();
		}
		else if(look.check(Sym.NOMBRE)) {
			Expression nb = new Nombre(look.getIntValue());
			look.eat(Sym.NOMBRE);
			return nb;
		}
		else if(look.check(Sym.LPAR)) {
			look.eat(Sym.LPAR);
			Expression e1 = expNonTerm();
			Operation op = opNonTerm();
			Expression e2 = expNonTerm();
			if(look.check(Sym.RPAR)) {
				look.eat(Sym.RPAR);
				return new Expe3(e1, op, e2);
			}else throw new ParserException(look.toString(), "Le caractère '(' est attendu dans l'Expression\n");
		}
		throw new ParserException(look.toString(), "Le caractère entré est Non reconnu dans Expression\n");
	}
	
	public Condition condNonTerm() throws Exception {
		if(look.check(Sym.LACC)) {
			look.eat(Sym.LACC);
			Condition c1 = condNonTerm();
			Connecteur c = connectNonTerm();
			Condition c2 = condNonTerm();
			if(look.check(Sym.RACC)) {
				look.eat(Sym.RACC);
				return new ConditionConnecteur(c1, c, c2);
			}else throw new ParserException(look.toString(), "Le caractère '}' est attendu dans Condition\n");
		}else {
			Expression e1 = expNonTerm();
			Comparaison c = compNonTerm();
			Expression e2 = expNonTerm();
			return new ConditionComparaison(e1, c, e2);
		}
	}
	
	public Operation opNonTerm() throws Exception {
		if(look.check(Sym.PLUS)) {
			look.eat(Sym.PLUS);
			return new Plus();
		}
		else if(look.check(Sym.MOINS)) {
			look.eat(Sym.MOINS);
			return new Moins();
		}else if(look.check(Sym.NOMBRE)) {
			if(look.char0Moins()) {
				return new Plus();
			}
		}throw new ParserException(look.toString(), "Le caractère '+' ou '-' est attendu dans Operation\n");
	}
	
	public Connecteur connectNonTerm() throws Exception {
		if(look.check(Sym.ET)) {
			look.eat(Sym.ET);
			return new Et();
		}
		else if(look.check(Sym.Ou)) {
			look.eat(Sym.Ou);
			return new Ou();
		}throw new ParserException(look.toString(), "Le caractère 'Et' ou 'Ou' est attendu dans Connecteur\n");
	}
	
	public Comparaison compNonTerm() throws Exception {
		if(look.check(Sym.INF)) {
			look.eat(Sym.INF);
			return new Inferieur();
		}
		else if(look.check(Sym.SUP)) {
			look.eat(Sym.SUP);
			return new Superieur();
		}
		else if(look.check(Sym.EGALE)) {
			look.eat(Sym.EGALE);
			return new Egale();
		}throw new ParserException(look.toString(), "Le caractère '>' ou '<' ou '=' est attendu dans Comparaison\n");
	}
}
