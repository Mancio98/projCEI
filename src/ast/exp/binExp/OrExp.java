package ast.exp.binExp;

import ast.type.Type;
import ast.type.BoolType;
import ast.exp.Exp;
import util.EnvironmentAsset;
import util.TypeError;

//Used for expression of type "exp || exp" 
public class OrExp extends BinExp {

    public OrExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Or\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
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
		String orCGen = "";
		
		String leftCGen = this.left.codeGeneration();
		
		String rightCGen = this.right.codeGeneration();
		
		orCGen += leftCGen +
				"push a0\n"+
				rightCGen +
				"lw t1 sp 0\n" +
				"pop \n"+
				"or t1 a0 a0\n"; // or LEFTVALUE RIGHTVALUE RETURNADDRESS
		
		return orCGen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}
}
