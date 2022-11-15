package ast.exp.binExp;

import ast.type.Type;
import ast.type.AssetType;
import ast.type.BoolType;
import ast.type.IntType;
import ast.exp.Exp;
import util.TypeError;

//Used for expression of type "exp != exp" 
public class NotEqualExp extends BinExp {

    public NotEqualExp(int row, int column, Exp left, Exp right) {
        super(row, column, left, right);
    }

    public int calculateExp() {
    	return 0;
    }
    
    @Override
    public String toPrint(String indent) {
        return indent + "Exp: NotEq\n" + indent + "\tLeft:\n" + super.left.toPrint(indent + "\t\t") + "\n" + indent + "\tRight:\n" + super.right.toPrint(indent + "\t\t");
    }

	@Override
	public Type typeCheck() {
		if (super.left.typeCheck() instanceof BoolType && (super.right.typeCheck() instanceof AssetType || super.right.typeCheck() instanceof IntType)){
			System.out.println(new TypeError(super.row, super.column, "comparison between this two type is not possible").toPrint());
            System.exit(0);
        }
		else if ((super.left.typeCheck() instanceof AssetType || super.left.typeCheck() instanceof IntType ) && super.right.typeCheck() instanceof BoolType) {
			System.out.println(new TypeError(super.row, super.column, "comparison between this two type is not possible").toPrint());
            System.exit(0);
		} 
        return new BoolType();
	}

	@Override
	public String codeGeneration() {
		String neCGen = "";
		
		String leftCGen = this.left.codeGeneration();
		
		String rightCGen = this.right.codeGeneration();
		
		neCGen += leftCGen +
				"push $a0\n"+
				rightCGen +
				"lw $t1 $sp 0\n" +
				"pop\n"+
				"ne $t1 $a0 $a0\n"; 
		
		return neCGen;
	}

}
