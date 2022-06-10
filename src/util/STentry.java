package util;

import ast.type.Type;

//Used for the insertion of an ID in the HashMap
public class STentry extends Entry {
	
	private Type type;
	
	public STentry(int nl)
	{
		super(nl);
	} 
	   
	public STentry(int nl, Type type)
	{
		super(nl);
		this.type = type;
	}
	  
	public void addType(Type type)
	{
		this.type = type;
	}
	  
	public Type getType()
	{
		return this.type;
	}
	  
	public int getNestinglevel()
	{
		return super.getNestinglevel();
	}
	  
	public String toPrint(String s) { 
		return s + "STentry: nestlev " + Integer.toString(this.nl) + "\n" +
				s + "STentry: type\n" + this.type.toPrint(s + "  ") + "\n";
	}

}  