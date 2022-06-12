package util;


//Used for the insertion of an ID in the HashMap
public class EEntry extends Entry {
	
	public EEntry(int nl)
	{
		super(nl);
	} 
	  
	public int getNestinglevel()
	{
		return super.getNestinglevel();
	}
	  
	public String toPrint(String s) { 
		return s + "Eentry: nestlev " + Integer.toString(this.nl) + "\n";
	}
	
}