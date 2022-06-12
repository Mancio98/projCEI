package ast.statement;

import ast.Node;
import util.EEnvironment;

//Extension of class Node used for readability
public abstract class Statement extends Node {

	public Statement(int row, int column) {
		super(row, column);
	}
	
	public abstract void analyzeLiquidity(EEnvironment env);
	
}
