package ast.exp;

import ast.Node;

//Extension of class Node used for readability
public abstract class Exp extends Node {
	
	public Exp(int row, int column) {
		super(row, column);
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Exp: (Generic)\n";
    }
	
	// public abstract String toPrintInFun(String indent);
}
