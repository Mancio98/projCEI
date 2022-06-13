package ast.exp;

import ast.Node;
import util.EEnvironment;

//Extension of class Node used for readability
public abstract class Exp extends Node {
	
	public Exp(int row, int column) {
		super(row, column);
	}

	@Override
    public String toPrint(String indent) {
        return indent + "Exp: (Generic)\n";
    }
	
	public abstract int calculateExp();
	
	public abstract void analyzeLiquidity(EEnvironment env);
}
