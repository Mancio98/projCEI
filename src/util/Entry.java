package util;


//Used for the insertion of an ID in the HashMap
public abstract class Entry {
	
	protected int nl;
	
	public Entry(int nl)
	{
		this.nl = nl;
	}
		  
	public int getNestinglevel()
	{
		return this.nl;
	}

}