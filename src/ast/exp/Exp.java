package ast.exp;

import ast.Node;

public abstract class Exp implements Node {
	
	@Override
    public String toPrint(String indent) {
        return indent + "Exp: (Generic)\n";
    }
}
