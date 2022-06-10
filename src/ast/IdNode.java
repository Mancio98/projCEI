package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.SemanticError;
import util.EEnvironment;
import util.EEnvironment.EffectState;
import util.STEnvironment.UndeclaredIdException;
import util.STEnvironment;
import ast.type.Type;
import util.STentry;

//Used to lookup if an ID is or not in the symbol table
public class IdNode extends Exp {

	private final String id;
    private STentry entry;

    public IdNode(int row, int column, String id) {
    	super(row,column);
    	this.id = id;
    }

    public String getId() {
    	return this.id;
    }
    
    public STentry getSTentry() {
    	return this.entry;
    }
    
    @Override
    public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        try {
			this.entry = env.lookUp(this.id);
		} catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row, super.column, " " + this.id + " undeclared"));
		} 
            
        return errors;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "ID: " + this.id;
    }

	@Override
	public Type typeCheck() {
		if (this.entry == null)
            System.exit(0);
        return this.entry.getType();
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizeEffect(EEnvironment env) {
		return ;
	}

	@Override
	public int calculateExp() {
		return 0;
	}
    
}