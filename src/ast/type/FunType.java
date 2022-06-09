package ast.type;

import java.util.ArrayList;

import util.EnvironmentAsset;

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

	public Type getReturnType() {
		return this.type;
	}
	
	public ArrayList<Type> getParTypes() {
		return this.parTypes;
	}
	
	public ArrayList<Type> getParATypes() {
		return this.parATypes;
	}
	/*
	public int getArgumentsNumber() {
		return this.parTypes.size() + this.parATypes.size();
	}
	*/
	public boolean isSubtype(Type type) {
    	return true;
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

	@Override
	public String analyzeEffect(EnvironmentAsset env) {
		// TODO Auto-generated method stub
		return null;
	}

}
