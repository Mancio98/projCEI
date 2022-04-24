package ast;

//Used for the insertion of an ID in the HashMap
public class STentry {
	
	private int nl;
	private Node type;
	
	  public STentry (int n)
	  { nl=n;
	  	} 
	   
	  public STentry (int n, Node t)
	  {nl=n;
	   type=t;
	  }
	  
	  public void addType (Node t)
	  {type=t;}
	  
	  public Node getType ()
	  {return type;}
	  
	  public int getNestinglevel ()
	  {return nl;}
	  
	  public String toPrint(String s) { 
		   return s+"STentry: nestlev " + Integer.toString(nl) +"\n"+
				  s+"STentry: type\n" + 
				  type.toPrint(s+"  ") + "\n";
	  }

}  