package ast.exp.binaryExp;

import ast.BoolTypeNode;
import ast.exp.Exp;

public class NotEqualExp extends BinExp {

    public NotEqualExp(Exp left, Exp right) {
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
        return indent + "Exp: NotEq\n" + super.left.toPrint(indent + "\t") + super.right.toPrint(indent + "\t");
    }
}
