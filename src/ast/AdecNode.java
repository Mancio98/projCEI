package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class AdecNode implements Node {
	
	private ArrayList<AssetNode> adec;

	public AdecNode(ArrayList<AssetNode> a) {
		adec=a;
	}

	public ArrayList<AssetNode> getListAdec(){
		return adec;
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
