package ast.type;

import java.util.ArrayList;

import util.EEnvironment;
import util.STEnvironment;
import util.SemanticError;
import ast.Node;

//Used to specify the type of a node
public abstract class Type extends Node {
	
	protected final String name;
	
	public Type(int row, int column, String name) {
		super(row, column);
		this.name = name;
	}

	public String getType() {
		return this.name;
	}
	
	public abstract boolean isSubtype(Type type) ;
	
	@Override
	public String toPrint(String indent) {
		return indent + "Type: " + this.name;
	}

	@Override
	public Type typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(STEnvironment env) {
		return null;
	}
	
	@Override
	public void analyzeEffect(EEnvironment env) {
		return ;
	}
}
