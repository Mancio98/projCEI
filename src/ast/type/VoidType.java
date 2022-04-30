package ast.type;

public class VoidType extends Type {
    
    public VoidType() {
        super(-1, -1, "Void");
    }
    
    public VoidType(int row, int column) {
        super(row, column, "Void");
    }

}
