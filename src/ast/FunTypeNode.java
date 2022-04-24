package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

//Used to specify the type of a node
public class FunTypeNode extends Node {
	
	ArrayList<Node> parTypes;
	ArrayList<Node> parATypes;
	Node type;

	public FunTypeNode(int row,int column,ArrayList<Node> parTypes, ArrayList<Node> parATypes, Node type) {
		super(row, column);
		this.parTypes = parTypes;
		this.parATypes = parATypes;
		this.type = type;
	}

	@Override
	public String toPrint(String indent) {
		
		String s="";
	    for (Node par:parTypes)
	      s+=par.toPrint("");
	    for (Node asset: parATypes) {
	    	s+= asset.toPrint("");
	    }
		return indent+"FunType\n" + s + type.toPrint(indent+"  ->") ; 
	}
	
	
	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

}
