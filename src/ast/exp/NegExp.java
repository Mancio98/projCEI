package ast.exp;

import ast.Node;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class NegExp extends Exp {

	private final Exp child;

    public NegExp(Exp child) {
        this.child = child;
    }

    public Exp getChild() {
		return this.child;
	}
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Neg\n" + child.toPrint(indent + "\t");
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
