package Code;
import Code.AbstractSyntaxTree.*;
import java.io.Reader;

public interface Parser {
    public Program parseProgram(String exeName, Reader reader);
}
