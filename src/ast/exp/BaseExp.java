package ast.exp;

import ast.Node;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class BaseExp extends Exp {

	private final Exp child;

    public BaseExp(Exp child) {
        this.child = child;
    }

    public Exp getChild() {
		return this.child;
	}
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Base\n" + child.toPrint(indent + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return child.checkSemantics(env);
    }

    @Override
    public Node typeCheck() {
        return child.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return child.codeGeneration();
    }
	
}
