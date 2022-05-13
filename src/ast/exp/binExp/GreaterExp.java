package ast.exp.binExp;

import ast.type.Type;
import ast.type.BoolType;
import ast.exp.Exp;
import util.TypeError;

//Used for expression of type "exp > exp" 
public class GreaterExp extends BinExp {

    public GreaterExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Greater\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
    }

	@Override
	public Type typeCheck() {
		if (!(super.left.typeCheck() instanceof BoolType && super.right.typeCheck() instanceof BoolType)) {
            new TypeError(super.row, super.column, "expecting an integer value");
            return null;
        }
        return new BoolType();
	}


	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
}