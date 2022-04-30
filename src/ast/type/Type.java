package ast.type;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;
import ast.Node;

//Used to specify the type of a node
public class Type extends Node {
	
	protected final String name;
	
	public Type(int row, int column, String name) {
		super(row, column);
		this.name = name;
	}

	public String getType() {
		return this.name;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "Type: " + this.name;
	}

	@Override
	public Type typeCheck() {
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
