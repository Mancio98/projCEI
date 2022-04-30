package ast.exp.binExp;

import ast.exp.Exp;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

//Extension of class Exp used for readability and binary expressions
public abstract class BinExp extends Exp {

	protected final Exp left;
	protected final Exp right;
	
    public BinExp(int row, int column, Exp left, Exp right) {
    	super(row,column);
        this.right = right;
        this.left = left;
    }

    public Exp getLeft() {
		return this.left;
	}
    
    public Exp getRight() {
		return this.right;
	}
    
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> leftErrors = this.left.checkSemantics(env);
        ArrayList<SemanticError> rightErrors = this.right.checkSemantics(env);
        leftErrors.addAll(rightErrors);
        return leftErrors;
    }
	
}
