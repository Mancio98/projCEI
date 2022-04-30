package ast.type;

import java.util.ArrayList;

public class FunType extends Type {
    
	private ArrayList<Type> parTypes;
	private ArrayList<Type> parATypes;
	private Type type;

	public FunType(ArrayList<Type> parTypes, ArrayList<Type> parATypes, Type type) {
		super(-1, -1, "Fun");
		this.parTypes = parTypes;
		this.parATypes = parATypes;
		this.type = type;
	}
	
	public FunType(int row, int column, ArrayList<Type> parTypes, ArrayList<Type> parATypes, Type type) {
		super(row, column, "Fun");
		this.parTypes = parTypes;
		this.parATypes = parATypes;
		this.type = type;
	}

	public String getType() {
		return this.type.getType();
	}
	
	@Override
	public String toPrint(String indent) {
		
		String s = " ( ";
	    for (Type par : this.parTypes) {
	    	s += par.toPrint("");
	    	s += ", ";
	    }
	    if (!this.parTypes.isEmpty()) {
	    	s = s.substring(0, s.length() - 2);
	    	s += " )";
	    }
	    else {
	    	s += ")";
	    }
	    s += " [ ";
	    for (Type asset: parATypes) {
	    	s += asset.toPrint("");
	    	s += ", ";
	    }
	    if (!this.parATypes.isEmpty()) {
	    	s = s.substring(0, s.length() - 2);
	    	s += " ]";
	    }
	    else {
	    	s += "]";
	    }
	    
		return indent + super.toPrint("") + s + type.toPrint(indent + " -> ") ; 
	}

}
