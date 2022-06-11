package ast.type;


public class AssetType extends Type {
    
    public AssetType() {
        super(-1, -1, "Asset");
    }
    
    public AssetType(int row, int column) {
        super(row, column, "Asset");
    }
    
    public boolean isSubtype(Type type) {
    	return (type != null ? (type instanceof AssetType ? true : false) : false);
    }

}