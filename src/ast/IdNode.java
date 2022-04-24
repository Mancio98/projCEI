package ast;

import java.util.ArrayList;

import ast.exp.Exp;
import util.SemanticError;
import util.Environment;
import util.Environment.UndeclaredIdException;

//Used to lookup if an ID is or not in the symbol table
public class IdNode extends Exp {

	private final String id;
    private STentry stEntry;

    public IdNode(int row,int column,String id) {
    	super(row,column);
    	this.id = id;
    }

    
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        try {
			stEntry = env.lookUp(id);
		} catch (UndeclaredIdException e) {
			errors.add(new SemanticError(super.row +":"+super.column +" " + id + " undeclared"));
		} 
            
        return errors;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "ID : " + id;
    }


	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
    
}