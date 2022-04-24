package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

//Used to the management of declaration of multiple ID of type asset
public class AdecNode extends Node {
	
	private ArrayList<AssetNode> adec;

	public AdecNode(int row,int column,ArrayList<AssetNode> a) {
		super(row, column);
		adec=a;
	}

	public ArrayList<AssetNode> getListAdec(){
		return adec;
	}
	@Override
	public String toPrint(String indent) {
		String s="";
		for(AssetNode v: adec) {
			s += v.toPrint(indent);
		}
		return s;
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
		ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
		
		for (AssetNode node : adec) {
			errors.addAll(node.checkSemantics(env));
		}
		
		return errors;
	}

}
