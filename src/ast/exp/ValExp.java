package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.IntTypeNode;
import util.Environment;

//Used for NUMBER
public class ValExp extends Exp {

    private final int value;

    public ValExp(int row, int column,int value) {
    	super(row,column);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Val(" + String.valueOf(value) + ")";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }
    
    @Override
    public IntTypeNode typeCheck() {
    	// TODO Auto-generated method stub
    	return null;
    }

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
    
}