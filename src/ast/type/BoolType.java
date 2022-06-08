package ast.type;

import util.EnvironmentAsset;

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

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
