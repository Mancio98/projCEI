package ast.type;

import util.EnvironmentAsset;

public class IntType extends Type {
    
    public IntType() {
        super(-1, -1, "Int");
    }
    
    public IntType(int row, int column) {
        super(row, column, "Int");
    }

    public boolean isSubtype(Type type) {
    	return (type != null ? (type instanceof IntType ? true : false) : false);
    }

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
