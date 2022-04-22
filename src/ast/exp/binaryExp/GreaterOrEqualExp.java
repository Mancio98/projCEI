package ast.exp.binaryExp;

import ast.BoolTypeNode;
import ast.exp.Exp;

public class GreaterOrEqualExp extends BinExp {

    public GreaterOrEqualExp(Exp left, Exp right) {
        super(left, right);
    }
    
    @Override
    public BoolTypeNode typeCheck() {
        return new BoolTypeNode();
    }
    
    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Exp: GreaterOrEq\n" + super.left.toPrint(indent + "\t") + super.right.toPrint(indent + "\t");
    }
}
