package ast.type;

public class VoidType extends Type {
    
    public VoidType() {
        super(-1, -1, "Void");
    }
    
    public VoidType(int row, int column) {
        super(row, column, "Void");
    }
    
    public boolean isSubtype(Type type) {
    	return (type != null ? (type instanceof VoidType ? true : false) : false);
    }

}
