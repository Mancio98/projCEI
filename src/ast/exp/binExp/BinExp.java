package ast.exp.binExp;

import ast.IdNode;
import ast.exp.CallExp;
import ast.exp.Exp;
import ast.statement.CallStmt;
import ast.statement.IteStmt;

import java.util.ArrayList;

import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
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
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        ArrayList<SemanticError> leftErrors = this.left.checkSemantics(env);
        ArrayList<SemanticError> rightErrors = this.right.checkSemantics(env);
        leftErrors.addAll(rightErrors);
        return leftErrors;
    }
    
    @Override
	public void analyzeEffect(EEnvironment env) {
    	this.left.analyzeEffect(env);
    	this.right.analyzeEffect(env);
		return ;
	}
    
    @Override
    public void analyzeLiquidity(EEnvironment env, String f) {
    	this.left.analyzeLiquidity(env, f);
    	this.right.analyzeLiquidity(env, f);

		return ;
    	
    }
    
    @Override
    public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
    	this.left.analyzeEffectFixPoint(env, gEnv, f);
    	this.right.analyzeEffectFixPoint(env, gEnv, f);
		
		return ;
	}
	
}
