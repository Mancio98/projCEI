package ast;

import java.util.ArrayList;

import util.Environment;
import util.EnvironmentAsset;
import util.SemanticError;
import ast.type.Type;

//Basic class used to inherit some of the principal methods for the project
public abstract class Node {
	
	protected final int row;
	protected final int column;
	
	public Node(int row,int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
   
	public abstract String toPrint(String indent);
	
	public abstract ArrayList<SemanticError> checkSemantics(Environment env);
	
	public abstract Type typeCheck();
	
	public abstract String analyzeEffect(EnvironmentAsset env);
	
	// VEDERE SE SISTEMARE
	//public abstract void analizeEffect();
	  
	public abstract String codeGeneration();
}  