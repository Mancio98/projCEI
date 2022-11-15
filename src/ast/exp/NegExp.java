package ast.exp;

import ast.IdNode;
import ast.type.IntType;
import ast.type.Type;
import java.util.ArrayList;

import util.EEntryAsset;
import util.EEntryFun;
import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;
import util.TypeError;

//Used for expression of type "- exp " 
public class NegExp extends Exp {

	private final Exp child;

    public NegExp(int row, int column, Exp child) {
    	super(row, column);
        this.child = child;
    }

    public Exp getChild() {
		return this.child;
	}
    
    public int calculateExp() {
    	return 0;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Neg\n" + this.child.toPrint(indent + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return this.child.checkSemantics(env);
    }

	@Override
	public Type typeCheck() {
		if (!(this.child.typeCheck() instanceof IntType)) {
			System.out.println(new TypeError(super.row, super.column, "expecting an integer value").toPrint());
            System.exit(0);
        }
        return new IntType();
	}

	@Override
	public String codeGeneration() {
		
		String expcgen = this.child.codeGeneration();
		return "li $a0 -"+expcgen;
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		System.out.println("NEG EXP");
		this.child.analyzeEffect(env);
		return ;
	}

	@Override
	public void analyzeLiquidity(EEnvironment env, String f) {
		System.out.println("NEG EXP");
		this.child.analyzeLiquidity(env, f);

		return ;
	}

	@Override
	public void analyzeEffectFixPoint(EEnvironment env, EEnvironment gEnv, String f) {
		System.out.println("NEG EXP FIX POINT");
		this.child.analyzeEffectFixPoint(env, gEnv, f);

		return ;
	}
	
}
