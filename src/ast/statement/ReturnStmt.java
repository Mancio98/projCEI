package ast.statement;

import java.util.ArrayList;

import ast.type.Type;
import ast.type.VoidType;
import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.STEnvironment;

//Used for rule like "return (exp)?"
public class ReturnStmt extends Statement {

	private final Exp exp;

	public ReturnStmt(int row,int column) {
		super(row, column);
		this.exp = null;
	}
	
	public ReturnStmt(int row, int column, Exp exp) {
		super(row, column);
		this.exp = exp;
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Return" + (this.exp != null ? ":\n" + this.exp.toPrint(indent + "\t") : "");
    }

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		if (this.exp != null)
            errors.addAll(this.exp.checkSemantics(env));
		
		return errors;
	}
	
	@Override
	public Type typeCheck() {
        Type returnType;
        if (this.exp == null)
            returnType = new VoidType();
        else
            returnType = this.exp.typeCheck();
        
        return returnType;
	}
	
	@Override
	public String codeGeneration() {
		
		return this.exp.codeGeneration();
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		if (exp != null) {
            exp.analyzeEffect(env);
        }
		return ;
	}


}
