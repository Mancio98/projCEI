package ast.type;

import util.EnvironmentAsset;

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

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
