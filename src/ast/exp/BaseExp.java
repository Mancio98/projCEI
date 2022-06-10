package ast.exp;

import ast.type.Type;
import java.util.ArrayList;
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
    
    /*
    @Override
    public String toPrintInFun(String indent) {
        return indent + "Exp: Base " + this.child.toPrint(indent);
    }
    */

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return this.child.checkSemantics(env);
    }

	@Override
	public Type typeCheck() {
		return this.child.typeCheck();
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		this.child.analizeEffect(env);
		return ;
	}
	
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
