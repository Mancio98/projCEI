package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

//Basic class used to inherit some of the principal methods for the project
public abstract class Node {
	
	protected final int row;
	protected final int column;
	
	public Node(int row,int column) {
		this.row=row;
		this.column=column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
   
  public abstract String toPrint(String indent);

  public abstract Node typeCheck();
  
  public abstract String codeGeneration();
  
  public abstract ArrayList<SemanticError> checkSemantics(Environment env);
  
  
}  