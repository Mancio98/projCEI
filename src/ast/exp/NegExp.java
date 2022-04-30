package ast.exp;

import ast.type.Type;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

//Used for expression of type "- exp " 
public class NegExp extends Exp {

	private final Exp child;

    public NegExp(int row, int column, Exp child) {
    	super(row, column);
        this.child = child;
    }

    public Exp getChild() {
		return this.child;
	}
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Neg\n" + this.child.toPrint(indent + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return this.child.checkSemantics(env);
    }

	@Override
	public Type typeCheck() {
		return this.child.typeCheck();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
