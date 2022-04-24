package ast;

import java.util.ArrayList;

import util.Environment;
import util.Environment.DuplicateEntryException;
import util.SemanticError;

//Used to declaration of variable of type bool or int
public class VarNode extends Node {
	
	String id;
	Node type;
	
	public VarNode(int row,int column,String id, Node type) {
		super(row, column);
		this.id = id;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Var: " + type.toPrint(indent) + " " + id + "\n";
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
			errors.add(new SemanticError(super.row + ":" + super.column + " var "+ id +" already declared"));
		}
		
		return errors;
	}

	public Node getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
