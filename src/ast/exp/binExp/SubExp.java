package ast.exp.binExp;

import ast.type.Type;
import ast.type.IntType;
import ast.exp.Exp;
import util.EnvironmentAsset;
import util.TypeError;

//Used for expression of type "exp - exp" 
public class SubExp extends BinExp {

    public SubExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: Sub\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
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
		String subCGen = "";
		
		String leftCGen = this.left.codeGeneration();
		
		String rightCGen = this.right.codeGeneration();
		
		subCGen += leftCGen +
				"push $a0\n"+
				rightCGen +
				"lw $t1 $sp 0\n" +
				"pop\n"+
				"sub $t1 $a0 $a0 \n"; // sub LEFTVALUE RIGHTVALUE RETURNADDRESS
		
		return subCGen;
	}

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}
}
