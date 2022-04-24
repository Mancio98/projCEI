package ast.exp;

import ast.Node;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

//Used for expression of type "( exp )" 
public class BaseExp extends Exp {

	private final Exp child;

    public BaseExp(int row,int column, Exp child) {
    	super(row,column);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
