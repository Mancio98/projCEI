package ast.type;


public class BoolType extends Type {
    
    public BoolType() {
        super(-1, -1, "Bool");
    }
    
    public BoolType(int row, int column) {
        super(row, column, "Bool");
    }

    public boolean isSubtype(Type type) {
    	return (type != null ? (type instanceof BoolType ? true : false) : false);
    }
    
}
