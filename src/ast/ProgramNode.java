package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class ProgramNode implements Node {
	
	private ArrayList<Node> field;
	private ArrayList<Node> asset;
	private ArrayList<Node> function;
	private Node initcall;
	

	public ProgramNode(ArrayList<Node> fi, ArrayList<Node> a, ArrayList<Node> fu, Node i){
		field=fi;
		asset=a;
		function=fu;
		initcall=i;
	}


	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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
