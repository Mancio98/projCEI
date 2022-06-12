package ast.exp;

import java.util.ArrayList;

import util.SemanticError;
import ast.type.Type;
import ast.type.IntType;
import util.EEnvironment;
import util.STEnvironment;

//Used for NUMBER
public class ValExp extends Exp {

    private final int value;

    public ValExp(int row, int column, int value) {
    	super(row, column);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    
    public int calculateExp() {
    	return this.value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Val(" + String.valueOf(this.value) + ")";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return new ArrayList<SemanticError>();
    }
    
    @Override
    public Type typeCheck() {
    	return new IntType();
    }

	@Override
	public String codeGeneration() {
		
		return "li $a0 "+this.value+"\n";
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		return ;
	}
    
}