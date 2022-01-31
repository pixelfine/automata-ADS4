package Code;
import Code.AbstractSyntaxTree.*;
public interface Interpreter
{
    public void run(Program prog, Grid initGrid);
}
