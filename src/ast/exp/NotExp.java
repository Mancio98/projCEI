package ast.exp;

import ast.type.BoolType;
import ast.type.Type;
import java.util.ArrayList;

import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;
import util.TypeError;

//Used for expression of type "! exp" 
public class NotExp extends Exp {

	private final Exp child;

    public NotExp(int row, int column, Exp child) {
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
        return indent + "Exp: Not\n" + this.child.toPrint(indent + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        return this.child.checkSemantics(env);
    }
    
	@Override
	public Type typeCheck() {
		if (!(this.child.typeCheck() instanceof BoolType)) {
			System.out.println(new TypeError(super.row, super.column, "expecting a bool value").toPrint());
            System.exit(0);
        }
        return new BoolType();
	}

	@Override
	public String codeGeneration() {
		
		
		return this.child.codeGeneration()+"not $a0 $a0\n";
	}

	@Override
	public void analyzeEffect(EEnvironment env) {
		this.child.analyzeEffect(env);
		return ;
	}




 
	
}
