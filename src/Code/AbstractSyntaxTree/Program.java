package Code.AbstractSyntaxTree;
import Code.Grid;

public class Program{
	private Instruction instruc;
	private Program program;
	public Program(Instruction instruc, Program program){
		this.instruc=instruc;
		this.program=program;
	}
	public void run(Grid grid) throws Exception {
		if(this.instruc!=null) {
			this.instruc.exec(grid);
		}
		if(this.program!=null) {
			this.program.run(grid);
		}
	}
}