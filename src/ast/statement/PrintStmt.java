package ast.statement;

import java.util.ArrayList;

import ast.type.BoolType;
import ast.type.Type;
import ast.IdNode;
import ast.exp.CallExp;
import ast.exp.Exp;
import util.SemanticError;
import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;

//Used for rule like "print exp"
public class PrintStmt extends Statement {

	private final Exp exp;

	public PrintStmt(int row,int column,Exp exp) {
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
		return indent + "Print:\n" + this.exp.toPrint(indent + "\t");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		errors.addAll(this.exp.checkSemantics(env));
		return errors;
	}

	@Override
	public Type typeCheck() {
		Type typeExp = this.exp.typeCheck();
		return null;
	}

	@Override
	public String codeGeneration() {
		if(this.exp.typeCheck() instanceof BoolType) {
			return this.exp.codeGeneration()+"print $a0 0\n";
		}
		else return this.exp.codeGeneration()+"print $a0 1\n";
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		this.exp.analyzeEffect(env);
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		this.exp.analyzeLiquidity(env, f);
		return ;
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		this.exp.analyzeEffectFixPoint(env, gEnv, f);
		return ;
	}
}
