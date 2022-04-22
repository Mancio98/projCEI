package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class FieldNode implements Node{
	
	private Node type;
	private Node exp;
	private String id;

	public FieldNode(String s, Node t, Node e) {
		type=t;
		exp=e;
		id=s;
	}
	
	public FieldNode(String s, Node t) {
		type=t;
		id=s;
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

}
