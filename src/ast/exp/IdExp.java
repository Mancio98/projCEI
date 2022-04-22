package ast.exp;

import java.util.ArrayList;
import ast.STentry;
import util.SemanticError;
import util.Environment;
import util.Environment.UndeclaredIdException;
import ast.Node;

public class IdExp extends Exp {

	private final String id;
    private STentry stEntry;
    private int nestingLevel;

    public IdExp(String id) {
    	this.id = id;
    }
    
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        try {
			stEntry = env.lookUp(id);
			nestingLevel = env.getNestingLevel();
		} catch (UndeclaredIdException e) {
			errors.add(new SemanticError("var [" + id + "] does not exist"));

			e.printStackTrace();
		} 
            
        return errors;
    }
    
    @Override
    public Node typeCheck() {
    	return null;
    }
    
    @Override
    public String codeGeneration() {
    	return "";
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Var\n" + indent + "\t Id: " + id + "\n";
    }
    
}