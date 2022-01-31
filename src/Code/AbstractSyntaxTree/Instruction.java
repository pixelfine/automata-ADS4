package Code.AbstractSyntaxTree;

import Code.Grid;
import Code.InterfaceGraphique;
import Code.Main;
import Code.SaveActions;

abstract class Instruction {
	public abstract void exec(Grid grid) throws Exception ;
}
class Avancer extends Instruction{
	private Expression exp;
	public Avancer(Expression exp) {
		this.exp=exp;
	}
	@Override
	public void exec(Grid grid) throws Exception {
		int tmp=(int) exp.eval(grid);
		while(tmp!=0) {
			if(tmp>0) { tmp--; grid.avancer(1);}
			else {tmp++; grid.avancer(-1);}
			if(Main.onIG) {InterfaceGraphique.actionList.add(new SaveActions(grid, "Avancer("+tmp+");"));}
		}
	}
}
class Tourner extends Instruction{
	private Expression exp;
	public Tourner(Expression exp) {
		this.exp=exp;
	}
	@Override
	public void exec(Grid grid) throws Exception {
		grid.tourner(exp.eval(grid));
		if(Main.onIG) {InterfaceGraphique.actionList.add(new SaveActions(grid, "Tourner("+exp.eval(grid)+");"));}
	}
}
class Ecrire extends Instruction{
	private Expression exp;
	public Ecrire(Expression exp) {
		this.exp=exp;
	}
	@Override
	public void exec(Grid grid) throws Exception {
		grid.ecrire(exp.eval(grid));
		if(Main.onIG) {InterfaceGraphique.actionList.add(new SaveActions(grid, "Ecrire("+exp.eval(grid)+")"));}
	}
}
class Si extends Instruction {
	private Condition cond;
	private Program prog;
	public Si(Condition cond, Program prog) {
		this.cond=cond;
		this.prog=prog;
	}
	@Override
	public void exec(Grid grid) throws Exception {
		if(cond.value(grid)) {
			prog.run(grid); 			
		}
	}
}
class TantQue extends Instruction {
	private Condition cond;
	private Program prog;
	public TantQue(Condition cond, Program prog) {
		this.cond=cond;
		this.prog=prog;
	}
	@Override
	public void exec(Grid grid) throws Exception {
		while(cond.value(grid)) {
			prog.run(grid);
		}
	}
}
