package ast.exp;

import ast.type.BoolType;
import ast.type.IntType;
import ast.type.Type;
import java.util.ArrayList;

import util.EEnvironment;
import util.Environment;
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
