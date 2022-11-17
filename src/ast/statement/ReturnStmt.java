package ast.statement;

import java.util.ArrayList;

import ast.type.Type;
import ast.type.VoidType;
import ast.IdNode;
import ast.exp.CallExp;
import ast.exp.Exp;
import util.SemanticError;
import util.EEntryAsset;
import util.EEntryFun;
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
	
	public boolean isCallExp() {
		boolean call = false;
		if (this.exp instanceof CallExp) {
			call = true;
		}
		return call;
	}
	
	public Exp getExp() {
		return this.exp;
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
		
		if (this.exp != null) {
			return this.exp.codeGeneration();}
		else 
			return "";
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		if (exp != null) {
            exp.analyzeEffect(env);
        }
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		if (exp != null) {
			this.exp.analyzeLiquidity(env, f);
		}
		
		return ;
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		if (exp != null) {
			this.exp.analyzeEffectFixPoint(env, gEnv, f);
		}
		return ;
	}
}
