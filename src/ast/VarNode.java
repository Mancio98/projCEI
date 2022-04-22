package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class VarNode implements Node {
	
	String id;
	Node type;

	
	public VarNode(String id, Node type) {
		super();
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
		// TODO Auto-generated method stub
		return null;
	}

	public Node getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
