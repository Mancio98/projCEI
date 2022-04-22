package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class FunTypeNode implements Node {
	
	ArrayList<Node> parTypes;
	ArrayList<Node> parATypes;
	Node type;

	public FunTypeNode(ArrayList<Node> parTypes, ArrayList<Node> parATypes, Node type) {
		super();
		this.parTypes = parTypes;
		this.parATypes = parATypes;
		this.type = type;
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

}
