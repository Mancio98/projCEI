package ast.exp;

import ast.type.BoolType;
import ast.type.IntType;
import ast.type.Type;
import java.util.ArrayList;
import util.Environment;
import util.EnvironmentAsset;
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
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Neg\n" + this.child.toPrint(indent + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
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
		return "li a0 -"+expcgen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
