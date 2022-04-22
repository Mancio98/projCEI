package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class DecNode implements Node {
	
	private ArrayList<VarNode> dec;

	public DecNode(ArrayList<VarNode> dec2) {
		dec=dec2;
	}

	public ArrayList<VarNode> getListDec(){
		return dec;
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
		
		return null;
	}

}
