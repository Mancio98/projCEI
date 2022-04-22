package ast;

import java.util.ArrayList;

import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;

public class VarNode implements Node {
	
	String id;
	Node type;
	
	public VarNode(String id, Node type) {
		this.id = id;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		try {
			env.addDeclaration(this.id, this.type);
		}
		catch (DuplicateEntryException e) {
			errors.add(new SemanticError("Var id "+ id +" already declared"));
			e.printStackTrace();
		}
		
		return errors;
	}

	public Node getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
