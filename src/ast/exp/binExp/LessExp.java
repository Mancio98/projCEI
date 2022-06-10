package ast.exp.binExp;

import ast.type.Type;
import ast.type.BoolType;
import ast.exp.Exp;
import util.TypeError;

//Used for expression of type "exp < exp" 
public class LessExp extends BinExp {

    public LessExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }
    
    public int calculateExp() {
    	return 0;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Less\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
    }

	@Override
	public Type typeCheck() {
		if (!(super.left.typeCheck() instanceof BoolType && super.right.typeCheck() instanceof BoolType)) {
			System.out.println(new TypeError(super.row, super.column, "expecting a bool value").toPrint());
            System.exit(0);
        }
        return new BoolType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}
}
