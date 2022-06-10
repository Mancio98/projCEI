package ast.exp.binExp;

import ast.type.Type;
import ast.type.IntType;
import ast.exp.Exp;
import util.TypeError;

//Used for expression of type "exp / exp" 
public class DivExp extends BinExp {

    public DivExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }
    
    public int calculateExp() {
    	return (super.left.calculateExp() / super.right.calculateExp());
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Div\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
    }

	@Override
	public Type typeCheck() {
		if (!(super.left.typeCheck() instanceof IntType && super.right.typeCheck() instanceof IntType)) {
			System.out.println(new TypeError(super.row, super.column, "expecting an integer value").toPrint());
            System.exit(0);
        }
        return new IntType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
}
