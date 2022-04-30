package ast.type;

public class IntType extends Type {
    
    public IntType() {
        super(-1, -1, "Int");
    }
    
    public IntType(int row, int column) {
        super(row, column, "Int");
    }

}
