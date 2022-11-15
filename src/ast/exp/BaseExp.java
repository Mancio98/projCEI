package ast.exp;

import ast.IdNode;
import ast.type.Type;
import java.util.ArrayList;

import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;

//Used for expression of type "( exp )" 
public class BaseExp extends Exp {

	private final Exp child;

    public BaseExp(int row, int column, Exp child) {
    	super(row, column);
        this.child = child;
    }

    public Exp getChild() {
		return this.child;
	}
    
    public int calculateExp() {
    	return this.child.calculateExp();
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Base\n" + this.child.toPrint(indent + "\t");
    }

	@Override
	public String codeGeneration() {
		
		return this.child.codeGeneration();
	}

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return this.child.checkSemantics(env);
    }

	@Override
	public Type typeCheck() {
		return this.child.typeCheck();
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		System.out.println("BASE EXP");
		this.child.analyzeEffect(env);
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		System.out.println("BASE EXP");
		this.child.analyzeLiquidity(env, f);
		
		return ;
	}
	
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		System.out.println("BASE EXP FIX POINT");
		this.child.analyzeEffectFixPoint(env, gEnv, f);

		return ;
	}
}
