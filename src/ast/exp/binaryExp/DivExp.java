package ast.exp.binaryExp;

import ast.BoolTypeNode;
import ast.Node;
import ast.exp.Exp;

//Used for expression of type "exp / exp" 
public class DivExp extends BinExp {

    public DivExp(int row,int column,Exp left, Exp right) {
        super(row,column,left, right);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Div\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
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
}
