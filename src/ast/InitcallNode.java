package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class InitcallNode implements Node {
	private String id;
	private ArrayList<Node> exp;

	public InitcallNode(String i, ArrayList<Node> e) {
		id=i;
		exp=e;
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
