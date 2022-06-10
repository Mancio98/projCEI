package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.type.Type;
import ast.type.BoolType;
import util.EEnvironment;
import util.Environment;
import util.STEnvironment;


//Used for BOOL 
public class BoolExp extends Exp {

    private final boolean bool;

    public BoolExp(int row, int column, boolean bool) {
    	super(row, column);
        this.bool = bool;
    }

    public boolean getBool() {
        return this.bool;
    }

    public int calculateExp() {
    	return 0;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Bool(" + String.valueOf(this.bool) + ")";
    }
    
    /*
    @Override
    public String toPrintInFun(String indent) {
    	return indent + "Bool(" + String.valueOf(this.bool) + ")";
    }
    */

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return new ArrayList<SemanticError>();
    }

	@Override
	public Type typeCheck() {
		return new BoolType();
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		return ;
	}
	
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

    
}