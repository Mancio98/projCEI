package ast.exp.binExp;

import ast.type.Type;
import ast.type.BoolType;
import ast.exp.Exp;
import util.EnvironmentAsset;
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
			System.out.println(new TypeError(super.row, super.column, "expecting a bool value").toPrint());
            System.exit(0);
        }
        return new BoolType();
	}


	@Override
	public String codeGeneration() {
		String greCGen = "";
		
		String leftCGen = this.left.codeGeneration();
		
		String rightCGen = this.right.codeGeneration();
		
		greCGen += leftCGen +
				"push a0\n"+
				rightCGen +
				"lw t1 sp 0\n" +
				"pop\n"+
				"gre t1 a0 a0\n"; // gre LEFTVALUE RIGHTVALUE RETURNADDRESS
		
		return greCGen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}
}
