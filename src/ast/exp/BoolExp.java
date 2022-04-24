package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.BoolTypeNode;
import ast.Node;
import util.Environment;

//Used for BOOL 
public class BoolExp extends Exp {

    private final boolean bool;

    public BoolExp(int row,int column,boolean bool) {
    	super(row,column);
        this.bool = bool;
    }

    public boolean getBool() {
        return this.bool;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Bool(" + String.valueOf(bool) + ")\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
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