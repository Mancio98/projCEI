package util;

import ast.type.Type;
import util.EEnvironment.EffectState;

//Used for the insertion of an ID in the HashMap
public class EEntry extends Entry {
	
	/*
	private EffectState state;
	*/
	public EEntry(int nl)
	{
		super(nl);
	} 
	/*
	public EEntry(int nl, EffectState state)
	{
		super(nl);
		this.state = state;
	}
	  
	public void updateEffectState(EffectState state)
	{
		this.state = state;
	}
	  
	public EffectState getEffectState()
	{
		return this.state;
	}*/
	  
	public int getNestinglevel()
	{
		return super.getNestinglevel();
	}
	  
	public String toPrint(String s) { 
		return s + "Eentry: nestlev " + Integer.toString(this.nl) + "\n";
	}
	
}  