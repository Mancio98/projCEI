package util;

import ast.type.Type;

//Used for the insertion of an ID in the HashMap
public class STentry {
	
	private int nl;
	private Type type;
	private int offset;
	private String label;
	
	public STentry(int nl)
	{
		this.nl = nl;
	} 
	   
	public STentry(int nl, Type type, int offset, int labelcount)
	{
		this.nl = nl;
		this.type = type;
		this.setOffset(offset);
		this.label = "function"+labelcount;
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
		return this.nl;
	}
	  
	public String toPrint(String s) { 
		return s + "STentry: nestlev " + Integer.toString(this.nl) + "\n" +
				s + "STentry: offset " + Integer.toString(this.offset) + "\n" +
				s + "STentry: type\n" + this.type.toPrint(s + "  ") + "\n";
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getLabel() {
		return label;
	}
	

}  