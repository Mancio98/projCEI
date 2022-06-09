package ast.exp;

import ast.type.Type;
import java.util.ArrayList;
import util.Environment;
import util.EnvironmentAsset;
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
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Base\n" + this.child.toPrint(indent + "\t");
    }
    
    /*
    @Override
    public String toPrintInFun(String indent) {
        return indent + "Exp: Base " + this.child.toPrint(indent);
    }
    */

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return this.child.checkSemantics(env);
    }

	@Override
	public Type typeCheck() {
		return this.child.typeCheck();
	}

	@Override
	public String codeGeneration() {
		
		return this.child.codeGeneration();
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
